package com.example.securityjpademo.service;

import com.example.securityjpademo.pojo.FKRole;
import com.example.securityjpademo.pojo.FKUser;
import com.example.securityjpademo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FKUser fkUser = userRepository.findByLoginName(username);
        if (fkUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<FKRole> roles = fkUser.getRoles();
        for (FKRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getAutority()));
        }
        return new User(fkUser.getUsername(), fkUser.getPassword(), authorities);
    }
}
