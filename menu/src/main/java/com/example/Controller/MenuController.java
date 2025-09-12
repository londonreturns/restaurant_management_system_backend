package com.example.Controller;

import com.example.Dto.MenuRequestDto;
import com.example.Dto.MenuResponseDto;
import com.example.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/getMenus")
    public ResponseEntity<List<MenuResponseDto>> getAllMenuDetails() {
        List<MenuResponseDto> menuDtoList = menuService.getAllMenuDetails();
        return new ResponseEntity<>(menuDtoList, HttpStatus.OK);
    }

    @GetMapping("/getMenu/{id}")
    public ResponseEntity<MenuResponseDto> getMenuById(@PathVariable Long id) {
        MenuResponseDto menuDto = menuService.getMenuDetailsById(id);
        return new ResponseEntity<>(menuDto, HttpStatus.OK);
    }

    @PostMapping("/createMenu")
    public ResponseEntity<MenuResponseDto> createMenu(@RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponse = menuService.createMenu(menuRequestDto);
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @PutMapping("/updateMenu/{id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = menuService.updateMenu(id, menuRequestDto);
        return new ResponseEntity<>(menuResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("deleteMenu/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return new ResponseEntity<>("Menu Deleted Successfully", HttpStatus.OK);
    }
}