package com.sainsbury.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataExtractorServiceHelper
{
    private static final String TITLE_SELECTOR = "div.productSummary div.productTitleDescriptionContainer h1";
    
    private static final String PRICE_SELECTOR = "div.productSummary div.priceTabContainer div.pricing p.pricePerUnit";
	private static final String PRICE_REGEX = "\\d\\.\\d+";
    
    private static final String DESCRIPTION_SECTION_SELECTOR = "h3.productDataItemHeader";
    private static final String DESCRIPTION_SELECTOR = "div.productText p";
	
	public static String getScrapedObjectTitle(final String link, final Document webpageFromLink)
	{
		Elements titleElements = webpageFromLink.select(TITLE_SELECTOR);
		
		if(!titleElements.isEmpty())
		{
			return titleElements.get(0).text();
		}
			
		return null;
	}
	
	public static String getScrapedObjectKcal(final String link, final Document webpageFromLink)
	{
		return null;
	}
	
	public static Double getScrapedObjectUnitPrice(final String link, final Document webpageFromLink)
	{
		Elements priceElements = webpageFromLink.select(PRICE_SELECTOR);
		
		if (!priceElements.isEmpty())
		{
			String price = priceElements.get(0).text();
			
			// Match the price string with a regex and set this to the model
	        final Pattern pattern = Pattern.compile(PRICE_REGEX);
	        final Matcher matcher = pattern.matcher(price);

	        if (matcher.find())
	        {
	        	return new Double(Double.parseDouble(matcher.group()));
	        }
		}
		
		return null;
	}
	
	public static String getScrapedObjectDescription(final String link, final Document webpageFromLink)
	{
		Elements descriptionElements = webpageFromLink.select(DESCRIPTION_SECTION_SELECTOR);
		List<Element> filteredDescriptionElements = descriptionElements.stream().filter(elem -> "Description".equals(elem.text()))
				.collect(Collectors.toList());
		
		if (!filteredDescriptionElements.isEmpty())
		{
			Elements descriptions = descriptionElements.get(0).nextElementSibling().select(DESCRIPTION_SELECTOR);
			
			if (!descriptions.isEmpty())
			{
				return descriptions.get(0).text();
			}
		}
		
		return null;
	}
}