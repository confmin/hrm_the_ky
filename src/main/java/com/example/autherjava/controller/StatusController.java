package com.example.autherjava.controller;


import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.in.StatusIn;
import com.example.autherjava.respon.Respon;
import com.example.autherjava.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/status")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class StatusController {
    @Autowired
    private StatusService statusService ;
    @PreAuthorize("hasPermission(#STATUS,'READ')")
    @GetMapping
    public ResponseEntity<List<StatusDto>> get()
    {
        return new ResponseEntity(statusService.get(),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody StatusIn statusIn)
    {
        return new ResponseEntity<Respon>(statusService.add(statusIn),HttpStatus.OK) ;
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@PathVariable Integer id , @RequestBody StatusIn statusIn)
    {
        return new ResponseEntity<Respon>(statusService.update(id , statusIn),HttpStatus.OK) ;
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateall(@RequestBody StatusIn statusIn)
    {
        return new ResponseEntity<Respon>(statusService.updateall(statusIn),HttpStatus.OK);
    }

}
