package org.example;

import java.net.URI;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

import rssloader.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        RSSSync rssSync = new RSSSync();
        try {
            File newFile = new File(".");
            System.out.println(newFile.getAbsolutePath());
            File newsList = new File("src/main/java/org/example/list.txt");
            Scanner newsListScanner = new Scanner(newsList);
            while (newsListScanner.hasNextLine()) {
                String urlString = newsListScanner.nextLine();
                String domainString = URI.create(urlString).getHost();
                rssSync.getNewUpdate(urlString, "src/main/tmp-cache/%s.xml".formatted(domainString));
//                RSSReader rssReader = new RSSReader();
//                rssReader.parseXML("src/main/tmp-cache/%s.xml".formatted(domainString));
            }
//            String urlString = "https://www.thecoinrepublic.com/feed/";
//            String domainString = URI.create(urlString).getHost();
//            rssSync.getNewUpdate(urlString, "src/main/tmp-cache/%s.xml".formatted(domainString));
//            RSSReader rssReader = new RSSReader();
//            rssReader.parseXML();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}