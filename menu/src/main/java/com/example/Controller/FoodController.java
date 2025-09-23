package com.example.Controller;

import com.example.Dto.*;
import com.example.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    @Autowired
    FoodService foodService;

    @PostMapping("/createSizeGroupAndSizes")
    public ResponseEntity<SizeGroupDTO> createSizeGroupAndSize(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return new ResponseEntity<>(foodService.createSizeGroupAndSize(sizeGroupDTO), HttpStatus.OK);
    }

    @GetMapping("/getAllSizeGroupsAndSizes")
    public ResponseEntity<List<SizeGroupDTO>> getAllSizeGroup() {
        return new ResponseEntity<>(foodService.getAllSizeGroups(), HttpStatus.OK);
    }

    @GetMapping("/getSizeGroupAndSizesById/{sizeGroupId}")
    public ResponseEntity<SizeGroupDTO> getSizeGroupById(@PathVariable Long sizeGroupId) {
        return new ResponseEntity<>(foodService.getSizeGroupById(sizeGroupId), HttpStatus.OK);
    }

    @PutMapping("/updateSizeGroupAndSizes")
    public ResponseEntity<SizeGroupDTO> updateSizeGroupAndSize(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return new ResponseEntity<>(foodService.updateSizeGroupAndSize(sizeGroupDTO), HttpStatus.OK);
    }

    @PostMapping("/createMenuAndMenuSizesBySizeGroupId")
    public ResponseEntity<MenuDTO> createMenuAndMenuSizesBySizeGroupId(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.createMenuAndSize(menuDTO), HttpStatus.OK);
    }

    @PutMapping("/updateMenuAndMenuSizesBySizeGroupId")
    public ResponseEntity<MenuDTO> updateMenuAndMenuSizesBySizeGroupId(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.updateMenuAndSize(menuDTO), HttpStatus.OK);
    }

    @GetMapping("/getAllCategoriesAndMenus")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesAndMenus() {
        return new ResponseEntity<>(foodService.getAllCategory(), HttpStatus.OK);
    }

    @PostMapping("/createOptionGroupAndOption")
    public ResponseEntity<OptionGroupDTO> createOptionSizeAndOption(@RequestBody OptionGroupDTO optionGroupDTO) {
        return new ResponseEntity<>(foodService.createOptionGroupAndOption(optionGroupDTO), HttpStatus.OK);
    }

    @PutMapping("/updateOptionGroupAndOption")
    public ResponseEntity<OptionGroupDTO> updateOptionSizeAndOption(@RequestBody OptionGroupDTO optionGroupDTO) {
        return new ResponseEntity<>(foodService.updateOptionGroupAndOption(optionGroupDTO), HttpStatus.OK);
    }

    @PutMapping("/linkSizeGroupOptionGroup")
    public ResponseEntity<SizeGroupOptionGroupDTO> linkSizeGroupOptionGroup(@RequestBody SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO) {
        return new ResponseEntity<>(foodService.linkSizeGroupAndOptionGroup(sizeGroupOptionGroupDTO), HttpStatus.OK);
    }

    @PostMapping("/handleMenuOptions")
    public ResponseEntity<MenuDTO> handleMenuOptions(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.handleMenuOptions(menuDTO), HttpStatus.OK);
    }

    @GetMapping("getMenuOptions")
    public ResponseEntity<List<MenuDTO>> getMenuOptions() {
        return new ResponseEntity<>(foodService.getMenuOptions(), HttpStatus.OK);
    }

    @GetMapping("/getMenuOptionsDetailed/{id}")
    public ResponseEntity<MenuDTO> getMenuOptionsDetailed(@PathVariable Long id) {
        return new ResponseEntity<>(foodService.getMenuOptionsDetailed(id), HttpStatus.OK);
    }

    @PutMapping("/handleMenuOptionsDetailed")
    public ResponseEntity<MenuDTO> handleMenuOptionsDetailed(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.handleMenuOptionsDetailed(menuDTO), HttpStatus.OK);
    }
}
