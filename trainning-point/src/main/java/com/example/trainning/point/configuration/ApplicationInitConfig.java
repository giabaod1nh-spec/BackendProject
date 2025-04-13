package com.example.trainning.point.configuration;

import com.example.trainning.point.entity.Role;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.repository.RoleRepository;
import com.example.trainning.point.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

//Tao auto 1 tk admin
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository , RoleRepository roleRepository) {
        return args -> {
        if (userRepository.findByEmail("admin").isEmpty()) {
             var roles = new HashSet<String>();
             roleRepository.findById(com.example.trainning.point.enums.Role.ADMIN.name());
             roles.add(com.example.trainning.point.enums.Role.ADMIN.name());
            User user = User.builder()
                    .email("admin")
                    .password(passwordEncoder.encode("admin")) //can them passwordencode -> tao 1 bean rieng de su dung
                    //.active(true)
                   // .roles(roles)
                    .build();

            userRepository.save(user);
            log.warn("Admin user has been created with default password: admin");
        }
       };
    }
}
