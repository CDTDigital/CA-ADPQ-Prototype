package com.intimetec.crns.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/")
public class DefaultController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "Get Login Page", notes = "Default page for application that is Login Page")
	String index() {
		return "index";
	}
}