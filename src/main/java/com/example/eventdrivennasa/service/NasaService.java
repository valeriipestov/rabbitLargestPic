package com.example.eventdrivennasa.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.eventdrivennasa.model.LargestPicRequestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NasaService {

    private RabbitTemplate rabbitTemplate;

    public String acceptAndPublish(LargestPicRequestDTO largestPicRequestDTO) {
        var commandId = generateCommandId();
        largestPicRequestDTO.setCommandId(commandId);
        rabbitTemplate.convertAndSend("pictures-direct-exchange", "", largestPicRequestDTO);
        return commandId;
    }


    private String generateCommandId() {
        return RandomStringUtils.randomAlphabetic(8);
    }

}
