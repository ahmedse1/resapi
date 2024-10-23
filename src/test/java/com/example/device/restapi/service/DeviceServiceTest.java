package com.example.device.restapi.service;

import com.example.device.restapi.dto.DeviceAddDTO;
import com.example.device.restapi.dto.DeviceUpdateDTO;
import com.example.device.restapi.entity.Device;
import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    private Device device;
    private DeviceAddDTO deviceAddDTO;
    private DeviceUpdateDTO deviceUpdateDTO;

    @BeforeEach
    void setUp() {
        device = new Device();
        device.setDeviceId(1);
        device.setDeviceName("iPhone 14 pro");
        device.setDeviceBrand("Apple");
        device.setCreatedAt(LocalDateTime.now());

        deviceAddDTO = new DeviceAddDTO();
        deviceAddDTO.setDeviceName("iPhone 14 pro");
        deviceAddDTO.setDeviceBrand("Apple");

        deviceUpdateDTO = new DeviceUpdateDTO();
        deviceUpdateDTO.setDeviceName("iPhone 14 pro");
        deviceUpdateDTO.setDeviceBrand("Apple");
        deviceUpdateDTO.setDeviceId(1);
    }

    @Test
    void testCreateDevice() {
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        deviceService.createDevice(deviceAddDTO);

        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void testGetDeviceById() {
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));

        DeviceDTO foundDeviceDTO = deviceService.getDeviceById(1);

        assertNotNull(foundDeviceDTO);
        assertEquals(device.getDeviceName(), foundDeviceDTO.deviceName());
        assertEquals(device.getDeviceBrand(), foundDeviceDTO.deviceBrand());
    }

    @Test
    void testGetDeviceById_NotFound() {
        when(deviceRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            deviceService.getDeviceById(1);
        });

        assertEquals("Device not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetAllDevices() {
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(device));

        List<DeviceDTO> deviceDTOs = deviceService.getAllDevices();

        assertNotNull(deviceDTOs);
        assertEquals(1, deviceDTOs.size());
        assertEquals(device.getDeviceName(), deviceDTOs.get(0).deviceName());
    }

    @Test
    void testUpdateDevice_Success() {
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        deviceUpdateDTO.setDeviceName("Updated iPhone");
        deviceUpdateDTO.setDeviceBrand("Updated Apple");

        boolean updated = deviceService.updateDevice(deviceUpdateDTO);

        assertTrue(updated);
        assertEquals("Updated iPhone", device.getDeviceName());
        assertEquals("Updated Apple", device.getDeviceBrand());
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void testUpdateDevice_NotFound() {
        when(deviceRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            deviceService.updateDevice(deviceUpdateDTO);
        });

        assertEquals("Device not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteDevice_Success() {
        when(deviceRepository.existsById(1)).thenReturn(true);

        boolean deleted = deviceService.deleteDevice(1);

        assertTrue(deleted);
        verify(deviceRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteDevice_NotFound() {
        when(deviceRepository.existsById(1)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            deviceService.deleteDevice(1);
        });

        assertEquals("Device not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetDevicesByBrand_Success() {

        List<Device> devices = Collections.singletonList(device);
        when(deviceRepository.findByDeviceBrandIgnoreCase("Apple")).thenReturn(devices);

        List<DeviceDTO> foundDeviceDTOs = deviceService.getDeviceByBrand("Apple");

        assertNotNull(foundDeviceDTOs);
        assertEquals(1, foundDeviceDTOs.size());
        assertEquals(device.getDeviceName(), foundDeviceDTOs.get(0).deviceName());
        assertEquals(device.getDeviceBrand(), foundDeviceDTOs.get(0).deviceBrand());
    }


    @Test
    void testGetDevicesByBrand_NotFound() {

        when(deviceRepository.findByDeviceBrandIgnoreCase("Apple")).thenReturn(Collections.emptyList());

        List<DeviceDTO> foundDeviceDTOs = deviceService.getDeviceByBrand("Apple");

        assertNotNull(foundDeviceDTOs);
        assertTrue(foundDeviceDTOs.isEmpty());
    }

}