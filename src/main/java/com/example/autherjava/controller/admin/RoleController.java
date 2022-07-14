package com.example.autherjava.controller.admin;

import com.example.autherjava.model.dto.RoleDto;
import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.service.role.RoleService;
import com.example.autherjava.utils.constants.ControllerConstant;
import com.example.autherjava.utils.respon.ResponRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping(ControllerConstant.ROLE.CREATE)
    ResponseEntity<?> create(@RequestBody RoleIn roleIn) {
        return new  ResponseEntity< ResponRole >(roleService.create(roleIn), HttpStatus.CREATED);
    }
    @GetMapping(ControllerConstant.ROLE.GET)
    ResponseEntity<?> get()
    {
        return  new ResponseEntity<List<RoleDto>>(roleService.get(),HttpStatus.OK);
    }
    @PutMapping(ControllerConstant.ROLE.UPDATE)
    ResponseEntity<?> update(@PathVariable Integer id , @RequestBody RoleIn roleIn)
    {
        return  new ResponseEntity<>(roleService.update(id,roleIn),HttpStatus.OK);
    }
    @DeleteMapping(ControllerConstant.ROLE.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id)
    {
        return new ResponseEntity<>(roleService.delete(id),HttpStatus.ACCEPTED);
    }
}
