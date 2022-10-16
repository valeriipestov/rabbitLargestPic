package com.example.eventdrivennasa.controller;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventdrivennasa.model.LargestPicRequestDTO;
import com.example.eventdrivennasa.service.NasaService;
import com.example.eventdrivennasa.service.PictureService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class NasaController {

    private NasaService nasaService;

    private PictureService pictureService;

    @PostMapping("/mars/pictures/largest")
    public ResponseEntity<String> getLargestLocation(@RequestBody LargestPicRequestDTO largestPicRequestDTO,
                                                     HttpServletRequest request) {
        var commandId = nasaService.acceptAndPublish(largestPicRequestDTO);
        return ResponseEntity
          .status(HttpStatus.ACCEPTED)
          .location(URI.create(request.getRequestURL() + "/" + commandId))
          .build();
    }

    @GetMapping(value = "/mars/pictures/largest/{commandId}", produces = "image/jpeg")
    public byte[] getPicture(@PathVariable String commandId) {
        return pictureService.findPicture(commandId);
    }

}
