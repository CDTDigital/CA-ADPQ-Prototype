package com.intimetec.crns.core.noaa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intimetec.crns.core.models.LastSync;
import com.intimetec.crns.core.repository.LastSyncTimeRepository;
import com.intimetec.crns.core.service.county2zip.County2ZipService;

@Service
public class NoaaWeatherAlertsFeeds {
	
	@Autowired
	County2ZipService county2ZipService;
	@Autowired
	LastSyncTimeRepository lastSyncTimeRepository;
	@Autowired
	String weatherAlertsUrl;
	
	
	Date lastSyncTime;

	public Feed parseFeeds() throws XMLStreamException {
		Optional<LastSync> lastSyncTime = lastSyncTimeRepository.findLatestNoaaSyncTime();
		if(lastSyncTime.isPresent()){
			this.lastSyncTime = lastSyncTime.get().getNoaaLastSyncTime();
		}
		ObjectFactory factory = new ObjectFactory();
		Feed feed = factory.createFeed();
		List<Feed.Entry> entryList = new ArrayList<Feed.Entry>();
		String tagContent = null;
		try {
			URL url = new URL(weatherAlertsUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");

			XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
			XMLStreamReader reader = xmlFactory.createXMLStreamReader(connection.getInputStream());

			while (reader.hasNext()) {
				int event = reader.next();
				boolean isEntryRead = false;
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					if ("entry".equals(reader.getLocalName())) {
						Feed.Entry entry = factory.createFeedEntry();

						while (reader.hasNext()) {
							int entryEvent = reader.next();
							switch (entryEvent) {
							case XMLStreamConstants.START_ELEMENT:
								if ("geocode".equals(reader.getLocalName())) {
									entry.geocode = factory.createFeedEntryGeocode();
									parseGeocodeInfo(reader, tagContent, entry);
								} else if ("author".equals(reader.getLocalName())) {
									entry.author = factory.createFeedEntryAuthor();
									parseAutherInfo(reader, tagContent, entry);
								}
								break;

							case XMLStreamConstants.CHARACTERS:
								tagContent = reader.getText().trim();
								break;

							case XMLStreamConstants.END_ELEMENT:
								switch (reader.getLocalName()) {
								case "id":
									entry.setId(tagContent);
									break;
								case "title":
									entry.setTitle(tagContent);
									break;
								case "summary":
									entry.setSummary(tagContent);
									break;
								case "published":
									entry.setPublished(formattedDate(tagContent, "yyyy-MM-dd'T'HH:mm:ssZ"));
									break;
								case "expires":
									entry.setExpires(formattedDate(tagContent, "yyyy-MM-dd'T'HH:mm:ssZ"));
									break;
								case "entry":
									isEntryRead = true;
									processEntry(entryList, entry);
									break;
								}
								break;
							}
							if(isEntryRead)
								break;
						}
					}
				}
			}
			feed.entry = entryList;
			return feed;
		} catch (MalformedURLException e) {
			throw new XMLStreamException("Provided URL is not correct, " + e.getMessage());
		} catch (IOException e) {
			throw new XMLStreamException("XML format is not supported, " + e.getMessage());
		} catch (ParseException e) {
			throw new XMLStreamException("XML Date format is not supported, " + e.getMessage());
		}
	}

	private void parseAutherInfo(XMLStreamReader reader, String tagContent, Feed.Entry entry)
			throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				break;

			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;

			case XMLStreamConstants.END_ELEMENT:
				switch (reader.getLocalName()) {
				case "name":
					entry.author.setName(tagContent);
					return;
				}
				break;
			}
		}
	}

	private void parseGeocodeInfo(XMLStreamReader reader, String tagContent, Feed.Entry entry)
			throws XMLStreamException {
		boolean isUGC = false;
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				break;

			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;

			case XMLStreamConstants.END_ELEMENT:
				switch (reader.getLocalName()) {
				case "valueName":
					if (("UGC").equalsIgnoreCase(tagContent))
						isUGC = true;
					break;
				case "value":
					if (isUGC) {
						entry.geocode.setValue(tagContent);
						return;
					}
				}
				break;
			}
		}
	}

	public Date formattedDate(String date, String format) throws ParseException {
		date = date.substring(0, date.length() - 3) + date.substring(date.length() - 2);
		DateFormat formatter = new SimpleDateFormat(format);
		return (Date) formatter.parse(date);
	}
	
	public void processEntry(List<Feed.Entry> entryList, Feed.Entry entry){
		String[] geocodes = entry.getGeocode().getValue().split(" ");
		List<String> validGeoCodes = new ArrayList<String>();
		if(entry.published.before(lastSyncTime)){
			return;
		}
		
		for(int i =0 ; i<geocodes.length; i++){
			if(geocodes[i].charAt(2) == 'C'){
				String fipsCode = geocodes[i].substring(3);
				validGeoCodes.add(fipsCode);
				entry.setCoutyMapping2Zip(county2ZipService.getByCountyCode(Integer.parseInt(fipsCode)));
			}
		}
		if(!validGeoCodes.isEmpty()) {
			entryList.add(entry);
			entry.geocode.setGeoCodes(validGeoCodes);
		}
	}
}