package com.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "size_option")
public class SizeOptionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false, referencedColumnName = "id")
    private SizeDB size;

    @Column(name = "size_id", insertable = false, updatable = false)
    private Long sizeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "id")
    private OptionDB option;

    @Column(name = "option_id", insertable = false, updatable = false)
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_group_option_group_id", nullable = false, referencedColumnName = "id")
    private SizeGroupOptionGroupDB sizeGroupOptionGroup;

    @Column(name = "size_group_option_group_id", insertable = false, updatable = false)
    private Long sizeGroupOptionGroupId;
}
