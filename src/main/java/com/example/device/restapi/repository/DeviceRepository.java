package com.example.device.restapi.repository;

import com.example.device.restapi.entity.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    // Page is a Spring Data JPA interface that represents a single page of results, typically used for pagination.
    // A Page contains the requested page of data, along with metadata such as the total number of pages,
    // the current page number, and the total number of records.

    Page<Device> findByDeviceBrand(String deviceBrand, Pageable pageable);

}
