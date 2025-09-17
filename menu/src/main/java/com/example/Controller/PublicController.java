package com.example.Controller;

import com.example.Dto.MenuDTO;
import com.example.Service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @Autowired
    PublicService publicService;

    @PutMapping("/handleMenuOptionsDetailed")
    public ResponseEntity<MenuDTO> handleMenuOptionsDetailed(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(publicService.handleMenuOptionsDetailed(menuDTO), HttpStatus.OK);
    }
}
