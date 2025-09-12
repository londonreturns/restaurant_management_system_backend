package com.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "menus")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "name", nullable = false)
    private String menuName;

    @Column(name = "description")
    private String menuDescription;

    @Column(name = "availability")
    private String menuAvailability;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemEntity> items;

    public MenuEntity(Long menuId, String menuName, String menuDescription, String menuAvailability) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.menuAvailability = menuAvailability;
        this.items = new ArrayList<>();
    }


}
