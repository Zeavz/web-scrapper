package com.jenis.scrapper.webscrapper.services;

import java.io.IOException;

public interface ScrapperService {

  public String scrapeSite(String url) throws IOException;

}
