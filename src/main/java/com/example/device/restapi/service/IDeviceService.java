package com.example.device.restapi.service;

import com.example.device.restapi.dto.DeviceDTO;

import java.util.List;

public interface IDeviceService {

    void createDevice(DeviceDTO deviceDTO);

    DeviceDTO getDeviceById(int id);

    List<DeviceDTO> getAllDevices();

    boolean updateDevice(DeviceDTO deviceDTO);


    boolean deleteDevice(int id);

    DeviceDTO getDeviceByBrand(String brand);
}
