package com.example.device.restapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DeviceDTO {

    @NotEmpty(message = "Device name cannot be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the device name should be between 5 and 30")
    private String deviceName;

    @NotEmpty(message = "Device brand cannot be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the device brand should be between 5 and 30")
    private String deviceBrand;
}
