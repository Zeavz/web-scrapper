package com.jenis.scrapper.webscrapper.controllers;

import com.jenis.scrapper.webscrapper.services.ScrapperService;
import com.jenis.scrapper.webscrapper.services.ScrapperServiceImpl;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ScrapperController {

    private final ScrapperService scrapperService;

    public ScrapperController(ScrapperServiceImpl scrapperService) {
        this.scrapperService = scrapperService;
    }

    @GetMapping("scrapping/start")
    public String startScrapping(@RequestParam String url) throws IOException {
        return scrapperService.scrapeSite(url);
    }
}
