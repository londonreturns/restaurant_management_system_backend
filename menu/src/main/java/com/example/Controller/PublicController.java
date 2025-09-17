package com.example.Controller;

import com.example.Dto.MenuDTO;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @Autowired
    PublicService publicService;

    @GetMapping("menuInfo/{menuId}")
    public ResponseEntity<MenuDTO> menuInfo(@PathVariable Long menuId) {
        return new ResponseEntity<>(publicService.menuInfo(menuId), HttpStatus.OK);
    }
}
