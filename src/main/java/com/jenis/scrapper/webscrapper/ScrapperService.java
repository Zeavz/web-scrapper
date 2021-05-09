package com.jenis.scrapper.webscrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ScrapperService {

  private String domainUrl;

  private HashMap<String, String> siteMap = new HashMap<>();

  public String returnString() {
    return "Hello World";
  }

  public String scrapeSite(String url) throws IOException {
    this.domainUrl = url;
    scrapeUrl(url);
    System.out.println(siteMap.size());
    return new ObjectMapper().writeValueAsString(siteMap);
  }

  private void scrapeUrl(String url) throws IOException {
    if (!siteMap.containsKey(url)) {
      Document document = Jsoup.connect(url).get();
      Elements links = document.select("a[href*=/]");
      siteMap.put(url, "Done");
      links.stream()
          .map(x -> {
            String hrefVal = x.attr("href");
            if (hrefVal.startsWith("/")) {
              return URI.create(domainUrl + hrefVal).normalize().toString();
            } else {
              return hrefVal;
            }
          })
          .distinct()
          .filter(x -> x.contains(domainUrl))
          .forEach(x -> {
            try {
              scrapeUrl(x);
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
    }
  }
}
