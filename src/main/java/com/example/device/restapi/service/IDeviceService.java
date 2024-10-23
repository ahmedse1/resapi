package com.example.device.restapi.service;

import com.example.device.restapi.dto.DeviceAddDTO;
import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.dto.DeviceUpdateDTO;

import java.util.List;

public interface IDeviceService {

    void createDevice(DeviceAddDTO deviceAddDTO);

    DeviceDTO getDeviceById(int id);

    List<DeviceDTO> getAllDevices();

    boolean updateDevice(DeviceUpdateDTO deviceUpdateDTO);


    boolean deleteDevice(int id);

    List<DeviceDTO> getDeviceByBrand(String brand);
}
