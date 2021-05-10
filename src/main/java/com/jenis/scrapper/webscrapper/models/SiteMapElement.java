package com.jenis.scrapper.webscrapper.models;

import java.util.List;

public class SiteMapElement {

  public List<AssetModels> assets;
  public List<String> outboundLinks;

  public SiteMapElement(List<AssetModels> assets, List<String> outboundLinks) {
    this.assets = assets;
    this. outboundLinks = outboundLinks;
  }

}
