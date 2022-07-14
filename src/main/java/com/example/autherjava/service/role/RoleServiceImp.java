package com.example.autherjava.service.role;

import com.example.autherjava.model.dto.RoleDto;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.model.mapper.RoleMapper;
import com.example.autherjava.repository.RoleRepository;
import com.example.autherjava.utils.constants.ServiceConstant;
import com.example.autherjava.utils.respon.ResponRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository ;
    @Override
    public ResponRole create(RoleIn roleIn) {
        Boolean check_role = roleRepository.existsByName("ROLE_".concat(roleIn.getName().toUpperCase().replaceAll("\\s+","")));
        if (roleIn.getName().toUpperCase().contains("ROLE"))
        {
            return new ResponRole(false, ServiceConstant.Role.HAVE_ROLE_NAME);
        }
        else if (check_role)
        {
            return new ResponRole(false, ServiceConstant.Role.NAME_EXIST);
        }
        roleRepository.save(RoleMapper.map(roleIn)) ;
        return new ResponRole(true, ServiceConstant.Role.SUSSCESS);
    }

    @Override
    public List<RoleDto> get() {
       List<RoleDto> list_role = roleRepository.findAll().stream().map(RoleMapper::map)
               .collect(Collectors.toList());
       return list_role ;
    }

    @Override
    public ResponRole update(Integer id,RoleIn roleIn) {
        Optional<Role> check_id  = roleRepository.findById(id);

        if (!check_id.isPresent())
        {
            return  new ResponRole(false,ServiceConstant.Role.ID_NOT_EXIST);
        }
        Role role = roleRepository.getById(id);
        role.setId(id);
        Boolean check_role = roleRepository.existsByName("ROLE_".concat(roleIn.getName().toUpperCase().replaceAll("\\s+","")));
        if (roleIn.getName().toUpperCase().contains("ROLE"))
        {
            return new ResponRole(false, ServiceConstant.Role.HAVE_ROLE_NAME);
        }
        else if (check_role)
        {
            return new ResponRole(false, ServiceConstant.Role.NAME_EXIST);
        }
        role.setName("ROLE_".concat(roleIn.getName().toUpperCase().replaceAll("\\s+","")));
        roleRepository.save(role) ;
        return new ResponRole(true,ServiceConstant.Role.UPDATE_SUCCESS);
    }

    @Override
    public ResponRole delete(Integer id) {
        Optional<Role> check_id  = roleRepository.findById(id);

        if (!check_id.isPresent())
        {
            return  new ResponRole(false,ServiceConstant.Role.ID_NOT_EXIST);
        }
        Role role = roleRepository.getById(id);
        role.setId(id);
        roleRepository.delete(role);
        return new ResponRole(true,ServiceConstant.Role.DELETE_SUCCESS);
    }
}
