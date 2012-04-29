package com.parousia.idlebrain.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.parousia.idlebrain.network.HTTPUtility;

public class DataHelper {

	public static ArrayList<String> heroineList = new ArrayList<String>();
	private static String siteHTML;

	public static ArrayList<String> fetchHeroineList() {
		try {
			siteHTML = HTTPUtility.fetchHTML(new URI(
					"http://idlebrain.com/movie/photogallery/heroines.html"));
			if (siteHTML == null) {
				return null;
			}
			Log.d("LOGDATA",siteHTML);
			Log.d("LOGDATA",Integer.toString(siteHTML.indexOf("&#149",0)));
			
			return parseWebsite(siteHTML);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

	private static ArrayList<String> parseWebsite(String siteHTML) {
		
		
		Document doc = Jsoup.parse(siteHTML);
		
		Elements links = doc.select("a[href]");
		Map<String, URI> mapOfLinks = new HashMap<String, URI>();  
		for (Element link : links) {
//			print(" * a: <%s>  (%s)", link.attr("abs:href"),
//					trim(link.text(), 35));
			
			Log.d("LOGDATA","html - " +link.html());
			Log.d("LOGDATA","link - "+link.attr("href"));
			
			
		}
		return null;
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}
