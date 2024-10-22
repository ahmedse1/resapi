package com.example.device.restapi.controller;


import com.example.device.restapi.dto.DeviceDTO;
import com.example.device.restapi.dto.ResponseDTO;
import com.example.device.restapi.service.IDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class DeviceController {

    private IDeviceService iDeviceService;


    //add device

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody DeviceDTO deviceDTO) {
        iDeviceService.createDevice(deviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO("201", "Device created successfully"));
    }

    //get device by id
    @GetMapping("/get")
    public ResponseEntity<DeviceDTO> getDeviceByIdentifier(@RequestParam int id) {
        DeviceDTO deviceDTO = iDeviceService.getDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }

    //get all devices
    @GetMapping("/getAll")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = iDeviceService.getAllDevices();
        if(devices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }


    //update device
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateDevice(@RequestBody DeviceDTO deviceDTO) {
        boolean isUpdated = iDeviceService.updateDevice(deviceDTO);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("200", ""));
        } else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417", "Update operation failed. Please try again or contact Dev team"));
        }
    }

    //delete device
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteDevice(@RequestParam int id) {
        boolean isDeleted = iDeviceService.deleteDevice(id);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO("200", ""));
        } else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417", "Delete operation failed. Please try again or contact Dev team"));
        }
    }

    //search/get device by brand
    @GetMapping("/getByBrand")
    public ResponseEntity<DeviceDTO> getDeviceByBrand(@RequestParam String brand) {
        DeviceDTO deviceDTO = iDeviceService.getDeviceByBrand(brand);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }
}
