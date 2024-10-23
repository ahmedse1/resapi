package com.example.device.restapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/*
This class serves the purpose of a parent class for entity objects.
It contains shared attributes to be used by other entities.
In our case, we only have one entity for Device,
but if we add more entities in the future, this will serve as a Base Entity.
 */
@MappedSuperclass
@Setter @Getter @ToString
public class BaseEntity {

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
