package com.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "option")
public class OptionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optiongroup_id", nullable = false, referencedColumnName = "id")
    private OptionGroupDB optionGroup;

    @Column(name = "optiongroup_id", insertable = false, updatable = false)
    private Long optionGroupId;
}
