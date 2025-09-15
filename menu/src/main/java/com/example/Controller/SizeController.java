package com.example.Controller;

import com.example.Dto.SizeDTO;
import com.example.Service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @PostMapping("/createSize")
    public ResponseEntity<SizeDTO> createSize(@RequestBody SizeDTO sizeDTO) {
        return new ResponseEntity<>(sizeService.createSize(sizeDTO), HttpStatus.OK);
    }

    @GetMapping("/getSizeById/{id}")
    public ResponseEntity<SizeDTO> findSizeById(@PathVariable Long id) {
        return new ResponseEntity<>(sizeService.findSizeById(id), HttpStatus.OK);
    }

    @GetMapping("getAllSizes")
    public ResponseEntity<List<SizeDTO>> findAllSize() {
        return new ResponseEntity<>(sizeService.findAllSize(), HttpStatus.OK);
    }

    @PutMapping("/updateSize")
    public ResponseEntity<SizeDTO> updateSize(@RequestBody SizeDTO sizeDTO) {
        return new ResponseEntity<>(sizeService.updateSize(sizeDTO), HttpStatus.OK);
    }

    @DeleteMapping("deleteSize")
    public ResponseEntity<String> deleteSizeById(@RequestParam Long id) {
        return new ResponseEntity<>(sizeService.deleteSize(id), HttpStatus.OK);
    }
}
