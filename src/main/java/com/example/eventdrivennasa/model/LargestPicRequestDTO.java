package com.example.eventdrivennasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LargestPicRequestDTO {

    private Integer sol;

    private String camera;

    @JsonProperty("command_id")
    private String commandId;

}
