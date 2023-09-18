package com.venture.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.venture.demo.model.User;
import com.venture.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByusername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found !!");
		}
		
		return new org.springframework.security.core.userdetails.User(
				user.getPassword(),
				user.getUsername(),
				true,
				true,
				true,
				true,
				getAuthorities(List.of(user.getRole())));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority>  authorities = new ArrayList<>();
        for(String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
