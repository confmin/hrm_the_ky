package com.example.autherjava.service.Imp;

import com.example.autherjava.mapper.StatusMapper;
import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.entity.Status;
import com.example.autherjava.model.in.StatusIn;
import com.example.autherjava.repository.StatusRepository;
import com.example.autherjava.respon.Respon;


import com.example.autherjava.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component

public class StatusServiceImp implements StatusService {
@Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusDto> get() {

//        Set<Status> listdata = new HashSet<>();
        List<Status> listdata = statusRepository.getOrderByUutien();
        System.out.println("listdata"+listdata);
        List<StatusDto> listdto =  listdata.stream()
                .map(StatusMapper::map)
                .collect(Collectors.toList());
        System.out.println(listdto+"aa");
        return listdto ;
    }

    @Override
    public Respon add(StatusIn statusIn) {
        Status status = StatusMapper.map(statusIn);
       
        Boolean checkName = statusRepository.getName(statusIn.getStatus()).isPresent();
        Boolean checkLevel = statusRepository.getLevel(statusIn.getLevel()).isPresent();

        if (checkName){
         return new Respon(false," Status da ton tai ","");
        }
        else if (checkLevel)
        {
            return new Respon(false," Level da ton tai ","");
        }
        else {
            statusRepository.save(status);
        }
        return new Respon(true,"Add thanh cong",status) ;
        }
    @Override
    public Respon update(Integer id, StatusIn statusIn) {
        Status status = statusRepository.getById(id) ;
        status.setId(id);
        Boolean checkName = statusRepository.getName(statusIn.getStatus()).isPresent();
        Boolean checkLevel = statusRepository.getLevel(statusIn.getLevel()).isPresent();
        System.out.println("checkname"+checkName);
        System.out.println("checklevel"+checkLevel);
            if (checkLevel)
            {
                if (checkName)
                {
                    return new Respon(false,"Ten status da ton tai",null);
                }
                else
                {
                    status.setStatus(statusIn.getStatus());
                    status.setLevel(statusIn.getLevel());
                    statusRepository.save(status);
                }
            }
            else
            {
                if (checkLevel && checkName)
                {
                    return new Respon(false,"Ten status or trang thai da ton tai",null);
                }
                else
                {
                    status.setStatus(statusIn.getStatus());
                    status.setLevel(statusIn.getLevel());
                    statusRepository.save(status);
                }
            }

        return new Respon(true,"Update suss",status);
    }
    @Override
    public Respon updateall(StatusIn statusIn) {

        List<StatusIn> status = statusIn.getStatusall();
//        List<Integer> status = statusRepository.getAllById() ;
//        List<Integer> store = statusIn.getStatusall() ;
         System.out.printf("ss"+ status.get(1).getId());
//        System.out.printf("cc"+ status.get(0));
        for (int i = 0 ; i < status.size();i++)
        {
            Status data = statusRepository.getById(status.get(i).getId());
            data.setId(status.get(i).getId());
            data.setLevel(status.get(i).getLevel());
            statusRepository.save(data);
        }
       return new Respon(true,"done",null);
    }

//    @Override
//    public Respon updateall(Integer id, StatusIn statusIn) {
//        Status status = statusRepository.getById(id) ;
//        status.setId(id);
//        status.setStatus(statusRepository.findStatusById());
//        status.setLevel(statusIn.getLevel());
//        return null ;
//    }


}
