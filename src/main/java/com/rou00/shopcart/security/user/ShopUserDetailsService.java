package com.rou00.shopcart.security.user;

import com.rou00.shopcart.model.entity.User;
import com.rou00.shopcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = Optional.ofNullable(userRepository.findByEmail(email))
               .orElseThrow(() -> new UsernameNotFoundException("USer not Found!"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
