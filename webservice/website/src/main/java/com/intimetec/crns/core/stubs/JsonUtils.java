package com.intimetec.crns.core.stubs;

import java.io.IOException;
import java.io.Reader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser.Feature;
import org.springframework.core.io.support.EncodedResource;

public class JsonUtils {
		
	/**
	 * Convenience method to create a mapper, optionally allowing comments.
	 */
	public static ObjectMapper createMapper(boolean allowComments)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_COMMENTS, allowComments);
		
		return mapper;
	}
	
	/**
	 * Gets the root JsonNode from the resource and manages the opening/closing of the associated stream.
	 */
	public static JsonNode getRootNode(ObjectMapper mapper) throws IOException
	{	
		EncodedResource resource = new Utf8EncodedClassPathResource("stubs/data.json");
		Reader reader = resource.getReader();
		
		try
		{
			return mapper.readTree(reader);
		}
		finally
		{
			reader.close();
		}
	}
}