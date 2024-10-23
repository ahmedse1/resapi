package com.example.device.restapi.controller;


import com.example.device.restapi.dto.*;
import com.example.device.restapi.service.IDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Create a new device", description = "Adds a new device to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody DeviceAddDTO deviceAddDTO) {
        iDeviceService.createDevice(deviceAddDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO("201", "Device created successfully"));
    }

    @Operation(summary = "Get device by ID", description = "Retrieve a device by its identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found",
                    content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceByIdentifier(@PathVariable int id) {
        DeviceDTO deviceDTO = iDeviceService.getDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }

    @Operation(summary = "Get all devices", description = "Retrieve a list of all devices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "204", description = "No devices found")
    })
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> devices = iDeviceService.getAllDevices();
        if(devices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }


    @Operation(summary = "Update device by ID", description = "Update an existing device by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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

    @Operation(summary = "Delete device by ID", description = "Delete a device from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device deleted successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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

    @Operation(summary = "Get devices by brand", description = "Search for devices by brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "404", description = "No devices found for the given brand",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<DeviceDTO>> getDeviceByBrand(@PathVariable String brand) {
        List<DeviceDTO> deviceDTO = iDeviceService.getDeviceByBrand(brand);
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }
}
