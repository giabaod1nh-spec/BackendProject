package com.example.trainning.point.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {
    @Column(name = "create_date")
    LocalDateTime createDate;

    @Column(name = "create_by")
    LocalDateTime startBy;

    @Column(name = "update_date")
    LocalDateTime updateDate;

    @Column(name = "update_by")
    LocalDateTime updateBy;

    @Column(name = "is_delete")
    Boolean isDelete = false;

    @PrePersist
    public void preCreate(){
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    public void prePersit(){
        this.updateDate = LocalDateTime.now();
    }
}
