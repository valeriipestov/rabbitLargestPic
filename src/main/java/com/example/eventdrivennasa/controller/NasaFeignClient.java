package com.example.eventdrivennasa.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

@FeignClient(name = "nasa", url = "${nasa.base_url}")
public interface NasaFeignClient {

    @GetMapping(path = "/rovers/curiosity/photos")
    JsonNode getPictures(@RequestParam String api_key, @RequestParam Integer sol,
                         @RequestParam(required = false) String camera);

}
