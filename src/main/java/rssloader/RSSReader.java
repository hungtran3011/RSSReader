package rssloader;

import javax.xml.parsers.*;
import java.net.URI;
import org.w3c.dom.*;
import rssloader.RSSSync;

public class RSSReader {
//    public String loadXML() {
//        URI rssFile = URI.create("https://crypto.news/feed/");
//        RSSSync rssSync = new RSSSync();
//        return rssFile.toString();
//    }
    public void parseXML(String URIString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URIString);
            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) item;
                    String title = elem.getElementsByTagName("title").item(0).getTextContent();
                    String link = elem.getElementsByTagName("link").item(0).getTextContent();
                    String description = elem.getElementsByTagName("description").item(0).getTextContent();
                    String[] categories = new String[elem.getElementsByTagName("category").getLength()];
                    for (int j = 0; j < elem.getElementsByTagName("category").getLength(); j++) {
                        String category = elem.getElementsByTagName("category").item(j).getTextContent();
                        categories[j] = category;
                    }
                    String author = elem.getElementsByTagName("dc:creator").item(0).getTextContent();
                    System.out.printf("""
                            Title: %s,
                            Link: %s,
                            Description: %s,
                            Categories: %s
                            Author: %s
                            
                            %n""", title, link, description, String.join(", ", categories), author);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
