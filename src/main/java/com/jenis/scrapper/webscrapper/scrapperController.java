package com.jenis.scrapper.webscrapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class scrapperController {

    @GetMapping("scrapping/start")
    public String startScrapping() {
        return "hello World";
    }
}
