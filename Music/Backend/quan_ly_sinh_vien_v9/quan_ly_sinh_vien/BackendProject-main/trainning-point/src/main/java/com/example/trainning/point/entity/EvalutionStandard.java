package com.example.trainning.point.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "tbl_evalution")
public class EvalutionStandard extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String name;

    @Column(name = "min_point")
    Double minPoint;

    @Column(name = "max_point")
    Double maxPoint;

    @OneToMany(mappedBy = "evalutionStandard")
    List<EvalutionPerson> evalutionPersonList;

    @ManyToOne
    @JoinColumn(name = "evalution_category_id")
    EvalutionCategory evalutionCategory;
}
