package com.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "menus")
public class MenuDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private float basePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    private CategoryDB category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_group_id", nullable = false, referencedColumnName = "id")
    private SizeGroupDB sizeGroup;

    @Column(name = "size_group_id", insertable = false, updatable = false)
    private Long sizeGroupId;
}