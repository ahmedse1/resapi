package com.example.device.restapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Device extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="device_id")
    private int deviceId;

    @Column(name="device_name")
    private String deviceName;

    @Column(name="device_brand")
    private String deviceBrand;

    public Device(String deviceName, String deviceBrand) {
        this.deviceName = deviceName;
        this.deviceBrand = deviceBrand;
    }
}
