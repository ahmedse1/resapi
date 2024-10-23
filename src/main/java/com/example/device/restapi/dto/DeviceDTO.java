package com.example.device.restapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class DeviceDTO {

    private int deviceId;

    @NotEmpty(message = "Device name cannot be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the device name should be between 3 and 50")
    private String deviceName;

    @NotEmpty(message = "Device brand cannot be a null or empty")
    @Size(min = 3, max = 50, message = "The length of the device brand should be between 3 and 50")
    private String deviceBrand;
}
