package com.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="menu_option")
public class MenuOptionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
    private MenuDB menu;

    @Column(name = "menu_id", insertable = false, updatable = false)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_group_id", nullable = false, referencedColumnName = "id")
    private SizeGroupDB sizeGroup;

    @Column(name = "size_group_id", insertable = false, updatable = false)
    private Long sizeGroupId;
}