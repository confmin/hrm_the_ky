package com.example.autherjava.service.Imp;

import com.example.autherjava.mapper.UserMapper;
import com.example.autherjava.model.dto.UserDto;
import com.example.autherjava.model.entity.Status;
import com.example.autherjava.model.entity.User;
import com.example.autherjava.model.in.UserIn;
import com.example.autherjava.repository.StatusRepository;
import com.example.autherjava.repository.UserRepository;
import com.example.autherjava.respon.ResponPage;
import com.example.autherjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private StatusRepository statusRepository;
    @Override
    public List<UserDto> get() {

        List<User>  listdata = userRepository.getUser();
        List<UserDto> listdto = listdata.stream()
                .map(UserMapper::map)
                .collect(Collectors.toList());
        return  listdto ;
    }

    @Override
    public ResponPage add(UserIn userIn, Integer limit)  {
        User user = UserMapper.map(userIn);
        Status status = statusRepository.getById(userIn.getStatus());
        user.setStatus(status);
        user.setTime(LocalDateTime.now());
        int countpage = userRepository.countIdPage(statusRepository.getByLevel(status.getLevel())) ;
        int actpage = (int) Math.ceil(countpage/limit);
        System.out.println(countpage+"ccsad");
        System.out.println(limit+"wqeqwe");
        userRepository.save(user);
        return new ResponPage(actpage);
    }

    @Override
    public User update(Integer id, UserIn userIn) {
        User user = userRepository.findUserById(id);
        user.setId(id);
        Status status = user.getStatus();
           user.setName(userIn.getName());
           user.setStatus(statusRepository.getById(userIn.getStatus()));
           user.setName(userIn.getName());
           user.setStatus(statusRepository.getById(userIn.getStatus()));
           user.setTime(LocalDateTime.now());
       return  userRepository.save(user);

    }


    @Override
    public Map<String,Object> delete(Integer id) {
        User user = userRepository.findUserById(id);
            userRepository.delete(user);
        Map<String, Object> repon = new HashMap<>();
        repon.put("result", "ok");
            return  repon ;
    }

    @Override
    public List<User> getbest() {
        List<User> get = userRepository.getBest();
        return  get ;

    }

    @Override
    public ResponPage getpage(Integer pageNo, Integer pageSize) {
        Sort sort = Sort.by("status","time").ascending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<User>pageResult = userRepository.getPage(pageable);
        return  new ResponPage(true,"done",pageResult.getNumber(),pageResult.getTotalPages(),pageResult.toList());
    }

    @Override
    public User getuser(Integer id) {
        User users = userRepository.findUserById(id);
        return users ;
    }




}
