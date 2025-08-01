package com.cepsearch.cep_search_application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cepsearch.cep_search_application.data.UserDetailData;
import com.cepsearch.cep_search_application.model.User;
import com.cepsearch.cep_search_application.repository.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("Usuário não pode ser encontrado: " + username);
        }
        
        return new UserDetailData(user);
    }
    
}
