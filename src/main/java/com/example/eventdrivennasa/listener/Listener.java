package com.example.eventdrivennasa.listener;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.eventdrivennasa.model.LargestPicRequestDTO;
import com.example.eventdrivennasa.service.PictureService;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class Listener {

    private PictureService pictureService;

    @RabbitListener(queues = "pictures-queue")
    public void processMessages(LargestPicRequestDTO largestPicRequestDTO) {
        var picBytes = pictureService.findLargestPicture(largestPicRequestDTO);
        pictureService.addPicture(largestPicRequestDTO.getCommandId(), picBytes);
    }


}
