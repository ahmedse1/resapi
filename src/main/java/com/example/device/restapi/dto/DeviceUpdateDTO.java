package com.example.device.restapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeviceUpdateDTO {

    private int deviceId;

    @Size(min = 3, max = 50, message = "The length of the device name should be between 3 and 50")
    private String deviceName;

    @Size(min = 3, max = 50, message = "The length of the device brand should be between 3 and 50")
    private String deviceBrand;
}

