package com.sainsbury.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.util.UriComponentsBuilder;
import com.sainsbury.main.MainApplicationController;

@SpringBootApplication
@ComponentScan("com.sainsbury.main")
public class SainsburyTechnicalTestApplication
{   
	// Default URL link for Sainsbury
	private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/"
			+ "www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

	// Message to display for User to input some URL into console
	private static final String CONSOLE_MESSAGE = "\nPlease provide a link to scrape from Webpage.\n"
			+ "You can also press enter to use the default link.";

	@Autowired
	private MainApplicationController mainApplicationController;

	public static void main(String[] args) throws IOException
	{
		ApplicationContext context = SpringApplication.run(SainsburyTechnicalTestApplication.class);

		SainsburyTechnicalTestApplication webScraperApp = context.getBean(SainsburyTechnicalTestApplication.class);

		webScraperApp.scrapeFromLink(args);

		SpringApplication.exit(context);
	}

	private void scrapeFromLink(final String[] args) throws IOException
	{
		System.out.printf("\nApplication started with %d arguments.\n", args.length);

		if (args.length > 0)
		{
			for (String arg : args)
			{
				if (isValidUri(arg))
				{
					// Method call to scrape the webpage
					mainApplicationController.scrapeWebpage(getUriFromInput(arg));
				}
			}
		}
		else
		{
			promptForConsoleInput();
		}
	}

	private void promptForConsoleInput() throws IOException
	{
		String userInput = ConsoleReader.requestUserInput(CONSOLE_MESSAGE);

		if (userInput.isEmpty())
		{
			// scrape the webpage from the default link
			mainApplicationController.scrapeWebpage(getUriFromInput(URL));
		}
		else if (isValidUri(userInput))
		{
			// scrape the webpage from the given link
			mainApplicationController.scrapeWebpage(getUriFromInput(userInput));
		}
		else
		{
			System.out.println("\nCould not parse the given URL: " + userInput + "\n");
		}
	}

	private boolean isValidUri(String input)
	{
		return new UrlValidator().isValid(input);
	}

	private URI getUriFromInput(String input)
	{
		return UriComponentsBuilder.fromHttpUrl(input).build().toUri();
	}

	public static class ConsoleReader 
	{
		public static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

		public static String requestUserInput(final String message) throws IOException {
			System.out.println(message);
			return READER.readLine();
		}
	}
}
