package com.example.device.restapi.controller;


import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.dto.DeviceUpdateDTO;
import com.example.device.restapi.dto.ResponseDTO;
import com.example.device.restapi.service.IDeviceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/devices", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DeviceController {

    private final IDeviceService iDeviceService;


    //add device
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody DeviceDTO deviceDTO) {
        iDeviceService.createDevice(deviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO("201", "Device created successfully"));
    }

    //get device by id
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceByIdentifier(@PathVariable int id) {
        DeviceDTO deviceDTO = iDeviceService.getDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }

    //get all devices
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = iDeviceService.getAllDevices();
        if(devices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }


    //update device
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateDevice(@Valid @PathVariable int id, @Valid @RequestBody DeviceUpdateDTO deviceUpdateDTO) {
        deviceUpdateDTO.setDeviceId(id);
        boolean isUpdated = iDeviceService.updateDevice(deviceUpdateDTO);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Device updated successfully"));
        } else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417", "Update operation failed. Please try again or contact Dev team"));
        }
    }

    //delete device
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteDevice(@PathVariable int id) {
        boolean isDeleted = iDeviceService.deleteDevice(id);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Device deleted successfully"));
        } else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417", "Delete operation failed. Please try again or contact Dev team"));
        }
    }

    //search/get device by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<DeviceDTO>> getDeviceByBrand(@PathVariable String brand) {
        List<DeviceDTO> deviceDTO = iDeviceService.getDeviceByBrand(brand);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }
}
