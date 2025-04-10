package com.example.trainning.point.entity;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class CriterionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    private Evaluate evaluate;

    @OneToMany(mappedBy = "criterionPoint", cascade = CascadeType.ALL)
    private List<DetailPoint> detailPoints;

    @ManyToOne
    private Criterion criterion;


}
