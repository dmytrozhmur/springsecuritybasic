package com.griddynamics.springsecuritybasic.security.config;

import com.griddynamics.springsecuritybasic.model.Authority;
import com.griddynamics.springsecuritybasic.model.Customer;
import com.griddynamics.springsecuritybasic.repo.CustomerRepository;
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
import java.util.Set;

@Service
public class EazyBankUserDetails implements UserDetailsService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String username, password;
        List<GrantedAuthority> authorities;
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers.isEmpty()) {
            throw new UsernameNotFoundException("No users with email " + email);
        } else {
            username = customers.get(0).getEmail();
            password = customers.get(0).getPwd();
            authorities = getGrantedAuthorities(customers.get(0).getAuthorities());
        }
        return new User(username, password, authorities);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        authorities.forEach(authority ->
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName())));
        return grantedAuthorities;
    }
}
