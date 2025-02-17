package com.example.device.restapi.service;

import com.example.device.restapi.dto.DeviceAddDTO;
import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.dto.DeviceUpdateDTO;
import com.example.device.restapi.entity.Device;
import com.example.device.restapi.mapper.DeviceMapper;
import com.example.device.restapi.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;

    // Optional as of Spring 4.3 if there is only one constructor
    // Constructor injection ensures that the dependency is available as soon as the object is created,
    // preventing null references, and supports better testing and code readability.
    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    @Override
    public void createDevice(DeviceAddDTO deviceAddDTO) {
        Device device = DeviceMapper.mapToDevice(deviceAddDTO);
        device.setCreatedAt(LocalDateTime.now());
        deviceRepository.save(device);
    }

    @Override
    public DeviceDTO getDeviceById(int id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with id: " + id));
        return DeviceMapper.mapToDeviceDTO(device);
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        // Fetch all devices from the repository and convert them to DTOs
        return deviceRepository.findAll().stream()
                .map(DeviceMapper::mapToDeviceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateDevice(DeviceUpdateDTO deviceUpdateDTO) {
        /*Optional<Device> optionalDevice = deviceRepository.findById(deviceUpdateDTO.getDeviceId());
        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            if (deviceUpdateDTO.getDeviceName() != null && !deviceUpdateDTO.getDeviceName().isEmpty()) {
                device.setDeviceName(deviceUpdateDTO.getDeviceName());
            }
            if (deviceUpdateDTO.getDeviceBrand() != null && !deviceUpdateDTO.getDeviceBrand().isEmpty()) {
                device.setDeviceBrand(deviceUpdateDTO.getDeviceBrand());
            }
            device.setUpdatedAt(LocalDateTime.now());
            deviceRepository.save(device);
            return true;
        } else {
            throw new EntityNotFoundException("Device not found with ID: " + deviceUpdateDTO.getDeviceId());
        }*/

        // Refactored code
        Device device = deviceRepository.findById(deviceUpdateDTO.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found with id: " + deviceUpdateDTO.getDeviceId()));

        if (deviceUpdateDTO.getDeviceName() != null && !deviceUpdateDTO.getDeviceName().isEmpty()) {
            device.setDeviceName(deviceUpdateDTO.getDeviceName());
        }
        if (deviceUpdateDTO.getDeviceBrand() != null && !deviceUpdateDTO.getDeviceBrand().isEmpty()) {
            device.setDeviceBrand(deviceUpdateDTO.getDeviceBrand());
        }

        device.setUpdatedAt(LocalDateTime.now());
        return true;
    }

    @Override
    public boolean deleteDevice(int id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("Device not found with ID: " + id);
        }
    }

    @Override
    public Page<DeviceDTO> getDeviceByBrand(String brand, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Device> devicesPage = deviceRepository.findByDeviceBrand(brand, pageable);
        if (devicesPage.isEmpty()) {
            throw new EntityNotFoundException("No devices found for the brand: " + brand);
        }
        return devicesPage.map(DeviceMapper::mapToDeviceDTO);
    }
}
