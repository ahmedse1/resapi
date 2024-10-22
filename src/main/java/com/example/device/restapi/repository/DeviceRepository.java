package com.example.device.restapi.repository;

import com.example.device.restapi.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    Optional<Device> findByDeviceBrand(String deviceBrand);
}
