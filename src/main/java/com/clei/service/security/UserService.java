package com.clei.service.security;

import com.clei.entity.security.User;
import com.clei.mapper.security.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public Integer insert(User user){
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userDao.insert(user);
    }
}
