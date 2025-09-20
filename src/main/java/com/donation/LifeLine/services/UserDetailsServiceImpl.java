package com.donation.LifeLine.services;

import ch.qos.logback.classic.spi.LoggingEventVO;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





    @Service
    public class UserDetailsServiceImpl implements UserDetailsService {
        @Autowired
        UserRepository userRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

            LoggingEventVO Username;
            return UserDetailsServiceImpl.build(user);
        }

        private static UserDetails build(User user) {
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new))
                    .build();
        }
    }

