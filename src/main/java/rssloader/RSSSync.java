package rssloader;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.net.*;
import java.net.http.*;
import java.util.Arrays;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class RSSSync {
    public void getNewUpdate(String urlString, String cacheURIString){
        try {
            URL url = URI.create(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Path file = Path.of(cacheURIString);
            if (file.toFile().exists()) {
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                ZonedDateTime lastModified = attr.lastModifiedTime().toInstant().atZone(ZoneOffset.UTC);
                connection.setRequestMethod("HEAD");
                connection.setRequestProperty("If-Modified-Since", lastModified.format(DateTimeFormatter.RFC_1123_DATE_TIME));
                connection.connect();
                int responseCode = connection.getResponseCode();
                System.out.println(responseCode);
                if (responseCode == 304) {
                    return;
                }
            }
            else {
                connection.setRequestMethod("GET");
                connection.connect();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = connection.getInputStream();
//                Document doc = builder.parse(inputStream);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(cacheURIString);
                    InputStream iStream = url.openStream();
                    byte buffer[] = new byte[1024];
                    int length;
                    while ((length = iStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, length);
                    }
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                finally {
//                    NodeList items = doc.getElementsByTagName("item");
//                    for (int i = 0; i < items.getLength(); i++) {
//                        Node item = items.item(i);
//                        if (item.getNodeType() == Node.ELEMENT_NODE) {
//                            Element elem = (Element) item;
//                            String title = elem.getElementsByTagName("title").item(0).getTextContent();
//                            String link = elem.getElementsByTagName("link").item(0).getTextContent();
//                            System.out.printf("""
//                                Title: %s,
//                                Link: %s
//                                %n""", title, link);
//                        }
//                    }
//                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
