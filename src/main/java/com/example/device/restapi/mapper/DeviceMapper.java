package com.example.device.restapi.mapper;

import com.example.device.restapi.dto.DeviceAddDTO;
import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.entity.Device;

public class DeviceMapper {

    public static Device mapToDevice(DeviceAddDTO deviceAddDTO, Device device) {
        device.setDeviceName(deviceAddDTO.getDeviceName());
        device.setDeviceBrand(deviceAddDTO.getDeviceBrand());
        return device;
    }

    public static DeviceDTO mapToDeviceDTO(Device device) {
        return new DeviceDTO(device.getDeviceId(), device.getDeviceName(), device.getDeviceBrand());
    }
}
