package com.example.trainning.point.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Proof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;
    private String proofType;
    private String status;

    @ManyToOne
    @JoinColumn(name = "detail_point_id")
    private DetailPoint detailPoint;

    // Lombok tự generate getter/setter, bạn không cần viết lại thủ công nếu đã có @Getter/@Setter
}
