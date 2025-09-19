package com.example.Controller;

import com.example.Dto.MenuDTO;
import com.example.Dto.SizeGroupOptionGroupDTO;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublicController {

    @Autowired
    PublicService publicService;

    @GetMapping("menuInfo/{menuId}")
    public ResponseEntity<MenuDTO> menuInfo(@PathVariable Long menuId) {
        return new ResponseEntity<>(publicService.menuInfo(menuId), HttpStatus.OK);
    }

    @GetMapping("/extraPrices/{sizeGroupOptionGroupId}")
    public ResponseEntity<SizeGroupOptionGroupDTO> extraPrices(@PathVariable Long sizeGroupOptionGroupId) {
        return new ResponseEntity<>(publicService.extraPrices(sizeGroupOptionGroupId), HttpStatus.OK);
    }

    @PostMapping("/handleExtraPrices")
    public ResponseEntity<SizeGroupOptionGroupDTO> extraPrices(@RequestBody SizeGroupOptionGroupDTO sizeGroupOptionGroupDTO) {
        return new ResponseEntity<>(publicService.handleExtraPrices(sizeGroupOptionGroupDTO), HttpStatus.OK);
    }
}
