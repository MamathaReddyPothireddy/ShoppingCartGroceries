package com.sainsbury.main;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sainsbury.service.DataExtractorService;

import com.sainsbury.model.JsonModel;

@Component
@ComponentScan("com.sainsbury.service")
public class MainApplicationController
{
	@Autowired
	private DataExtractorService dataExtractorService;
	
    public void scrapeWebpage(final URI inputUri)
    {
    	System.out.println("Scraping webpage from link: " + inputUri + "\n");
    	
    	try
    	{
    		JsonModel json = dataExtractorService.getJsonModel(inputUri);
    	
    		ObjectMapper objectMapper = new ObjectMapper();
    		
    		System.out.println(objectMapper.writeValueAsString(json));
    		
    	}
    	catch (MalformedURLException e)
    	{
    		e.printStackTrace();
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    }
}