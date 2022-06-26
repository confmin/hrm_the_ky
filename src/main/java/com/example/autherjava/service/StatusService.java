package com.example.autherjava.service;

import com.example.autherjava.model.dto.StatusDto;
import com.example.autherjava.model.in.StatusIn;
import com.example.autherjava.respon.Respon;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface StatusService {

    @PreAuthorize("hasPermission(#hr, 'read')")
    List<StatusDto> get() ;
    Respon add(StatusIn statusIn);
    Respon update(Integer id , StatusIn statusIn) ;
  Respon updateall( StatusIn statusIn);


}
