package com.example.autherjava.model.mapper;

import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.entity.Status;

import com.example.autherjava.model.in.StatusIn;
import org.springframework.beans.factory.annotation.Autowired;

public class StatusMapper {
    @Autowired

    public  static StatusDto map(Status status)
    {
        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setName(status.getName());
        statusDto.setLevel(status.getLevel());
        return statusDto ;
    }
    public static Status map(StatusIn statusIn)
    {

        Status  status = new Status();

        if (statusIn.getId() != null){
            status.setId(statusIn.getId());
        }
        status.setName(statusIn.getName());

            status.setLevel(statusIn.getLevel());

        return status ;
    }
}
