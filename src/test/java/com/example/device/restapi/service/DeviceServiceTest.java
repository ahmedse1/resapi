package com.example.device.restapi.service;

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
    private DeviceDTO deviceDTO;

    @BeforeEach
    void setUp() {
        device = new Device();
        device.setDeviceId(1);
        device.setDeviceName("iPhone 14 pro");
        device.setDeviceBrand("Apple");
        device.setCreatedAt(LocalDateTime.now());

        deviceDTO = new DeviceDTO();
        deviceDTO.setDeviceName("iPhone 14 pro");
        deviceDTO.setDeviceBrand("Apple");
        deviceDTO.setDeviceId(1);
    }

    @Test
    void testCreateDevice() {
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        deviceService.createDevice(deviceDTO);

        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void testGetDeviceById() {
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));

        DeviceDTO foundDeviceDTO = deviceService.getDeviceById(1);

        assertNotNull(foundDeviceDTO);
        assertEquals(device.getDeviceName(), foundDeviceDTO.getDeviceName());
        assertEquals(device.getDeviceBrand(), foundDeviceDTO.getDeviceBrand());
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
        assertEquals(device.getDeviceName(), deviceDTOs.get(0).getDeviceName());
    }

    @Test
    void testUpdateDevice_Success() {
        when(deviceRepository.findById(1)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        deviceDTO.setDeviceName("Updated iPhone");
        deviceDTO.setDeviceBrand("Updated Apple");

        boolean updated = deviceService.updateDevice(deviceDTO);

        assertTrue(updated);
        assertEquals("Updated iPhone", device.getDeviceName());
        assertEquals("Updated Apple", device.getDeviceBrand());
        verify(deviceRepository, times(1)).save(device);
    }

    @Test
    void testUpdateDevice_NotFound() {
        when(deviceRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            deviceService.updateDevice(deviceDTO);
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
    void testGetDeviceByBrand_Success() {
        when(deviceRepository.findByDeviceBrand("Apple")).thenReturn(Optional.of(device));

        DeviceDTO foundDeviceDTO = deviceService.getDeviceByBrand("Apple");

        assertNotNull(foundDeviceDTO);
        assertEquals(device.getDeviceName(), foundDeviceDTO.getDeviceName());
        assertEquals(device.getDeviceBrand(), foundDeviceDTO.getDeviceBrand());
    }

    @Test
    void testGetDeviceByBrand_NotFound() {
        when(deviceRepository.findByDeviceBrand("Apple")).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            deviceService.getDeviceByBrand("Apple");
        });

        assertEquals("Device not found with brand: Apple", exception.getMessage());
    }
}