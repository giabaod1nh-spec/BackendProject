package com.example.trainning.point.entity;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Evaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private int totalScore;

    @OneToMany(mappedBy = "evaluate", cascade = CascadeType.ALL)
    private List<CriterionPoint> criterionPoints;

}
