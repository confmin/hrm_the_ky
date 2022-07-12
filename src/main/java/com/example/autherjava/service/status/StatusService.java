package com.example.autherjava.service.status;

import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.in.StatusIn;
import com.example.autherjava.utils.respon.ResponStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {

  @PreAuthorize("hasPermission('status','read')")
    List<StatusDto> get() ;
    @PreAuthorize("hasPermission('status','create')")
    ResponStatus add(StatusIn statusIn);
    @PreAuthorize("hasPermission('status','update')")
    ResponStatus update(Integer id , StatusIn statusIn) ;
    @PreAuthorize("hasPermission('status','update')")
    ResponStatus updateall(StatusIn statusIn);


}
