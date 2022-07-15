package com.example.autherjava.model.seeders;

import com.example.autherjava.model.entity.Permission;
import com.example.autherjava.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class PermissonSeeder {

    @Autowired
    PermissionRepository permissionRepository;
/*
-  Tat khi da khoi tao xong data

* */

    @PostConstruct
    public void run()
    {
 // init_permisson();
    }
    public void init_permisson() {

        List<Permission> setup = new ArrayList<>();
        setup.add(new Permission("USER.ADD"));
        setup.add(new Permission("USER.CREATE"));
        setup.add(new Permission("USER.UPDATE"));
        setup.add(new Permission("USER.DELETE"));
        setup.add(new Permission("STATUS.READ"));
        setup.add(new Permission("STATUS.CREATE"));
        setup.add(new Permission("STATUS.UPDATE"));
        setup.add(new Permission("STATUS.DELETE"));
        setup.add(new Permission("HR.READ"));
        setup.add(new Permission("HR.CREATE"));
        setup.add(new Permission("HR.UPDATE"));
        setup.add(new Permission("HR.DELETE"));
        setup.add(new Permission("ADMIN.READ"));
        setup.add(new Permission("ADMIN.CREATE"));
        setup.add(new Permission("ADMIN.UPDATE"));
        setup.add(new Permission("ADMIN.DELETE"));
            permissionRepository.saveAll(setup);


    }
}
