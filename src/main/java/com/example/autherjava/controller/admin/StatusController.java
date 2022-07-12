package com.example.autherjava.controller.admin;


import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.in.StatusIn;
import com.example.autherjava.utils.respon.ResponStatus;
import com.example.autherjava.service.status.StatusService;
import com.example.autherjava.utils.constants.ControllerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class StatusController {
    @Autowired
    private StatusService statusService ;

    @GetMapping(ControllerConstant.STATUS.GET_ALL)
    public ResponseEntity<List<StatusDto>> get()
    {

        return new ResponseEntity(statusService.get(),HttpStatus.OK);
    }

    @PostMapping(ControllerConstant.STATUS.CREATE)
    public ResponseEntity<?> add(@RequestBody StatusIn statusIn)
    {
        return new ResponseEntity<ResponStatus>(statusService.add(statusIn),HttpStatus.OK) ;
    }

    @PutMapping(ControllerConstant.STATUS.UPDATE)
    public  ResponseEntity<?> update(@PathVariable Integer id , @RequestBody StatusIn statusIn)
    {
        return new ResponseEntity<ResponStatus>(statusService.update(id , statusIn),HttpStatus.OK) ;
    }
    @PutMapping(ControllerConstant.STATUS.UPDATE_ALL)
    public ResponseEntity<?> updateall(@RequestBody StatusIn statusIn)
    {
        return new ResponseEntity<ResponStatus>(statusService.updateall(statusIn),HttpStatus.OK);
    }

}
