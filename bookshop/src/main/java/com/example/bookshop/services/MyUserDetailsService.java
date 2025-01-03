package com.example.bookshop.services;

import com.example.bookshop.models.Role;
import com.example.bookshop.models.User;
import com.example.bookshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private static Logger LOG = LoggerFactory.getLogger(MyUserDetailsService.class);

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        LOG.info("Finded user : [" + username + "]");
        System.out.println("User found: " + user.getUsername() + ", password: " + user.getPassword());

        MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user);

        return myUserPrincipal;
    }


    public class MyUserPrincipal extends User implements UserDetails {
        private User user;

        private MyUserPrincipal(User user) {
            this.user = user;
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setRole(user.getRole());
            this.setIsAccountNonLocked(user.getIsAccountNonLocked());
        }

        public User getUser() {
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> authorities = new HashSet<>();

            // Додаємо тільки реальну роль користувача
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            return authorities;
        }


//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            Set<GrantedAuthority> authorities = new HashSet<>();
//            for (Role role : Role.values()) {
//                authorities.add(new SimpleGrantedAuthority(role.name()));  // Використовуємо .name() для ролі
//            }
//            return authorities;
//        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return user.getIsAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}