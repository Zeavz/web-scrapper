package com.jenis.scrapper.webscrapper;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ScrapperController {

    private final ScrapperService  scrapperService;

    public ScrapperController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @GetMapping("scrapping/start")
    public String startScrapping(@RequestParam String url) throws IOException {
        System.out.println(url);
        return scrapperService.scrapeSite(url);
    }
}
