package com.jenis.scrapper.webscrapper.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenis.scrapper.webscrapper.models.AssetModels;
import com.jenis.scrapper.webscrapper.models.SiteMapElement;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ScrapperServiceImpl implements ScrapperService {

  Pattern NO_EXTENSION_PATTERN = Pattern.compile(".*(?![a-zA-Z]+$)[^.]+$");
  Pattern FILE_EXTENSION_PATTERN = Pattern.compile(".*\\.com.*\\.[a-zA-Z0-9]+$");

  private String domainUrl;

  private final HashMap<String, SiteMapElement> siteMap = new HashMap<>();

  @Override
  public String scrapeSite(String url) throws IOException {
    this.domainUrl = url;
    scrapeUrl(url);
    System.out.println(siteMap.size());
    return new ObjectMapper().writeValueAsString(siteMap);
  }

  private void scrapeUrl(String url) throws IOException {
    if (!siteMap.containsKey(url)) {
      Document document = Jsoup.connect(url).get();
      List<AssetModels> assets = retrieveStaticAssets(document);
      List<String> domainLinks = retrieveDomainLinkStrategy(document);

      // update hashmap with extracted data
      siteMap.put(url, new SiteMapElement(assets, domainLinks));

      // repeat for all domain links found on page
      domainLinks.forEach(x -> {
        try {
          scrapeUrl(x);
        } catch (IOException e) {
          // broken links
          e.printStackTrace();
        }
      });
    }
  }

  private List<AssetModels> retrieveStaticAssets(Document document) {
    List<AssetModels> allAssets = new ArrayList<>();
    allAssets.addAll(getImageStrategy(document));
    allAssets.addAll(getExternalFileStrategy(document));

    return allAssets;
  }

  private List<AssetModels> getImageStrategy(Document document) {
    return document.select("img").stream().map(x -> {
      if (!x.attr("src").isBlank()) {
        return new AssetModels(x.tagName(), x.attr("src"));
      } else {
        return new AssetModels(x.tagName(), x.attr("data-src"));
    }
    }).collect(
        Collectors.toList());
  }

  private List<AssetModels> getExternalFileStrategy(Document document) {
    return document
        .select("a[href]")
        .stream()
        .map(x -> x.attr("href"))
        .distinct()
        .filter(FILE_EXTENSION_PATTERN.asPredicate())
        .map(x -> new AssetModels("file", x))
        .collect(Collectors.toList());
  }

  private List<String> retrieveDomainLinkStrategy(Document document) {
    return document
        .select("a[href*=/]")
        .stream()
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
        .filter(NO_EXTENSION_PATTERN.asPredicate())
        .collect(Collectors.toList());
  }
}
