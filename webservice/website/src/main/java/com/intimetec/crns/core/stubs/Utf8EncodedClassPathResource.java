package com.intimetec.crns.core.stubs;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

public class Utf8EncodedClassPathResource extends EncodedResource {
	
	public Utf8EncodedClassPathResource(String resourceName)
	{
		super(new ClassPathResource(resourceName), "utf-8");
	}

}
