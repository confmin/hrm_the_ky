package com.example.autherjava.mapper;

import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.entity.Status;

import com.example.autherjava.model.in.StatusIn;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StatusMapper {
    @Autowired

    public  static StatusDto map(Status status)
    {
        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setStatus(status.getStatus());
        statusDto.setLevel(status.getLevel());
        return statusDto ;
    }
    public static Status map(StatusIn statusIn)
    {
        StatusMapper cc = new StatusMapper();

        Status  status = new Status();

        if (statusIn.getId() != null){
            status.setId(statusIn.getId());
        }
        status.setStatus(statusIn.getStatus());

            status.setLevel(statusIn.getLevel());

        return status ;
    }
}
