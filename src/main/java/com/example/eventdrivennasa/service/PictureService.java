package com.example.eventdrivennasa.service;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.eventdrivennasa.controller.NasaFeignClient;
import com.example.eventdrivennasa.model.LargestPicRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureService {

    Map<String, byte[]> picStorage = new ConcurrentHashMap<>();
    @Value("${nasa.api_key}")
    private String nasaApiKey;

    private final NasaFeignClient feignClient;

    private final RestTemplate restTemplate;

    public byte[] findLargestPicture(LargestPicRequestDTO largestPicRequestDTO) {
        validateRequest(largestPicRequestDTO);
        var pictures =
          feignClient.getPictures(nasaApiKey, largestPicRequestDTO.getSol(), largestPicRequestDTO.getCamera());

        return pictures.findValues("img_src").parallelStream()
          .map(url -> restTemplate.headForHeaders(url.asText()).getLocation())
          .filter(Objects::nonNull)
          .map(redirectUrl -> Pair.of(redirectUrl, restTemplate.headForHeaders(redirectUrl).getContentLength()))
          .max(Comparator.comparing(Pair::getRight))
          .map(pair -> restTemplate.getForObject(pair.getLeft(), byte[].class))
          .orElseThrow(() -> new NoSuchElementException("Largest Picture was not found"));
    }

    public void addPicture(String id, byte[] pic) {
        picStorage.put(id, pic);
    }

    public byte[] findPicture(String commandId) {
        var pic = picStorage.get(commandId);
        if (Objects.nonNull(pic)) {
            return pic;
        }
        throw new NoSuchElementException("No picture found");
    }


    private void validateRequest(LargestPicRequestDTO largestPicRequestDTO) {
        Objects.requireNonNull(largestPicRequestDTO);
        Objects.requireNonNull(largestPicRequestDTO.getSol());
    }

}
