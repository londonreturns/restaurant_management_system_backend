package com.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "size_group_option_group")
public class SizeGroupOptionGroupDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optiongroup_id", nullable = false, referencedColumnName = "id")
    private OptionGroupDB optionGroup;

    @Column(name = "optiongroup_id", insertable = false, updatable = false)
    private Long optionGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sizegroup_id", nullable = false, referencedColumnName = "id")
    private SizeGroupDB sizeGroup;

    @Column(name = "sizegroup_id", insertable = false, updatable = false)
    private Long sizeGroupId;
}
