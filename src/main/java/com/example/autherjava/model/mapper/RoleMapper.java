package com.example.autherjava.model.mapper;

import com.example.autherjava.model.dto.RoleDto;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.utils.constants.ServiceConstant;

public class RoleMapper {
    public static Role map(RoleIn roleIn)
    {
        Role role   = new Role();
        role.setName("ROLE_".concat(roleIn.getName().toUpperCase().replaceAll("\\s+","")));
        return role ;
    }
    public static RoleDto map(Role role)
    {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto ;
    }
}
