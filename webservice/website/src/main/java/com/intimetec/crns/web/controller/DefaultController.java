package com.intimetec.crns.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hello() {
		return "Hello World";
	}
	
}