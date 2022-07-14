package com.example.autherjava.service.role;

import com.example.autherjava.model.dto.RoleDto;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.utils.respon.ResponRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleService {
    ResponRole create(RoleIn roleIn) ;
    List<RoleDto> get();
    ResponRole update(Integer id , RoleIn roleIn);
    ResponRole delete(Integer id);
}
