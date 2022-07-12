package com.example.autherjava.service.status;

import com.example.autherjava.model.mapper.StatusMapper;
import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.entity.Status;
import com.example.autherjava.model.in.StatusIn;
import com.example.autherjava.repository.StatusRepository;
import com.example.autherjava.utils.constants.ServiceConstant;
import com.example.autherjava.utils.respon.ResponStatus;


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
        List<Status> listdata = statusRepository.getOrderByUutien();
        List<StatusDto> listdto =  listdata.stream()
                .map(StatusMapper::map)
                .collect(Collectors.toList());
        return listdto ;
    }

    @Override
    public ResponStatus add(StatusIn statusIn) {
        Status status = StatusMapper.map(statusIn);
       
        Boolean checkName = statusRepository.getName(statusIn.getName()).isPresent();
        Boolean checkLevel = statusRepository.getLevel(statusIn.getLevel()).isPresent();

        if (checkName){
         return new ResponStatus(false, ServiceConstant.Status.NAME_EXIST);
        }
        else if (checkLevel)
        {
            return new ResponStatus(false, ServiceConstant.Status.LEVEL_EXIST);
        }
        else
            statusRepository.save(status);
        return new ResponStatus(true, ServiceConstant.Status.CREATE_SUSSCESS) ;
        }
    @Override
    public ResponStatus update(Integer id, StatusIn statusIn) {
        Status status = statusRepository.getById(id) ;
        status.setId(id);
        Boolean checkName = statusRepository.getName(statusIn.getName()).isPresent();
        Boolean checkLevel = statusRepository.getLevel(statusIn.getLevel()).isPresent();
            if (checkLevel)
            {
                if (checkName)
                {
                    return new ResponStatus(false, ServiceConstant.Status.NAME_EXIST);
                }
                else
                {
                    status.setName(statusIn.getName());
                    status.setLevel(statusIn.getLevel());
                    statusRepository.save(status);
                }
            }
            else
            {
                if (checkLevel && checkName)
                {
                    return new ResponStatus(false, ServiceConstant.Status.NAME_AND_LEVEL_EXIST);
                }
                else
                {
                    status.setName(statusIn.getName());
                    status.setLevel(statusIn.getLevel());
                    statusRepository.save(status);
                }
            }

        return new ResponStatus(true, ServiceConstant.Status.UPDATE_SUCCESS);
    }
    @Override
    public ResponStatus updateall(StatusIn statusIn) {

        List<StatusIn> status = statusIn.getStatusall();
        for (int i = 0 ; i < status.size();i++)
        {
            Status data = statusRepository.getById(status.get(i).getId());
            data.setId(status.get(i).getId());
            data.setLevel(status.get(i).getLevel());
            statusRepository.save(data);
        }
       return new ResponStatus(true,ServiceConstant.Status.UPDATE_SUCCESS);
    }

}
