package com.clei.service.security;

import com.clei.entity.security.MyUserDetails;
import com.clei.entity.security.Role;
import com.clei.entity.security.User;
import com.clei.mapper.security.RoleMapper;
import com.clei.mapper.security.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userDao;
    @Autowired
    private RoleMapper roleDao;

    private static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("{} 用户尝试登陆",username);

        User user = userDao.selectByUsername(username);
        if(null != user){
            List<Role> roles = roleDao.selectByUserId(user.getId());
            if(null != roles && !roles.isEmpty()){
                List<SimpleGrantedAuthority> authorities = roles.stream().map(Role::getName)
                        // 加个 ROLE_前缀
                        .map( r -> "ROLE_" + r)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                MyUserDetails userDetails = new MyUserDetails(user.getLoginName(),user.getPassword(),authorities);

                logger.info("成功获取用户信息！！！");

                return userDetails;
            }else{
                throw new RuntimeException("账户" + username + "没有对应的角色");
            }
        }else{
            throw new RuntimeException("账户" + username + "不存在");
        }
    }
}
