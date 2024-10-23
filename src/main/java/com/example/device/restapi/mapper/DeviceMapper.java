package com.example.device.restapi.mapper;

import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.entity.Device;

public class DeviceMapper {

    public static Device mapToDevice(DeviceDTO deviceDTO, Device device) {
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceBrand(deviceDTO.getDeviceBrand());
        return device;
    }

    public static DeviceDTO mapToDeviceDTO(Device device, DeviceDTO deviceDTO) {
        deviceDTO.setDeviceId(device.getDeviceId());
        deviceDTO.setDeviceName(device.getDeviceName());
        deviceDTO.setDeviceBrand(device.getDeviceBrand());
        return deviceDTO;
    }
}
