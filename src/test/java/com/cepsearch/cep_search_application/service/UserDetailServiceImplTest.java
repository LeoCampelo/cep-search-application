package com.cepsearch.cep_search_application.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cepsearch.cep_search_application.data.UserDetailData;
import com.cepsearch.cep_search_application.model.User;
import com.cepsearch.cep_search_application.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceImplTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        String username = "testuser";
        User mockUser = new User();

        when(userRepository.findByLogin(username)).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertInstanceOf(UserDetailData.class, userDetails);
        verify(userRepository, times(1)).findByLogin(username);
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserDoesNotExist() {
        String username = "nonexistent";

        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailService.loadUserByUsername(username);
        });
        verify(userRepository, times(1)).findByLogin(username);
    }
}
