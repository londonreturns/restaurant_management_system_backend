package com.example.Controller;

import com.example.Dto.SizeGroupDTO;
import com.example.Service.SizeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SizeGroupController {

    @Autowired
    private SizeGroupService sizeGroupService;

    @PostMapping("/createSizeGroup")
    public ResponseEntity<SizeGroupDTO> createSizeGroup(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return new ResponseEntity<>(sizeGroupService.createSizeGroup(sizeGroupDTO), HttpStatus.OK);
    }

    @GetMapping("/getSizeGroupById/{id}")
    public ResponseEntity<SizeGroupDTO> getSizeGroupById(@PathVariable Long id) {
        return new ResponseEntity<>(sizeGroupService.getSizeGroupById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllSizeGroups")
    public ResponseEntity<List<SizeGroupDTO>> getAllSizeGroups() {
        return new ResponseEntity<>(sizeGroupService.getAllSizeGroups(), HttpStatus.OK);
    }

    @PutMapping("/updateSizeGroup")
    public ResponseEntity<SizeGroupDTO> updateSizeGroup(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return new ResponseEntity<>(sizeGroupService.updateSizeGroup(sizeGroupDTO), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSizeGroup/{id}")
    public ResponseEntity<String> deleteSizeGroup(@PathVariable Long id) {
        return new ResponseEntity<>(sizeGroupService.deleteSizeGroup(id), HttpStatus.OK);
    }
}
