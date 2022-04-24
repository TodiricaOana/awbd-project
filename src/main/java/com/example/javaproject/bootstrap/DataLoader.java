package com.example.javaproject.bootstrap;

import com.example.javaproject.model.UserDetails;
import com.example.javaproject.model.security.Authority;
import com.example.javaproject.model.security.User;
import com.example.javaproject.repository.security.AuthorityRepository;
import com.example.javaproject.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadUserData();
    }

    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());

            UserDetails adminDetails = UserDetails.builder()
                    .address("Str. Florilor")
                    .phoneNumber("0779041991")
                    .otherInformation("bla")
                    .build();

            User admin = User.builder()
                    .email("admin@gmail.com")
                    .fullName("Admin")
                    .password(passwordEncoder.encode("password"))
                    .authority(adminRole)
                    .userDetails(adminDetails)
                    .build();

            UserDetails guestDetails = UserDetails.builder()
                    .address("Str. Mihail Sadoveanu")
                    .phoneNumber("0783735455")
                    .otherInformation("bla")
                    .build();

            User guest = User.builder()
                    .fullName("First user")
                    .email("guest@gmail.com")
                    .password(passwordEncoder.encode("password"))
                    .authority(guestRole)
                    .userDetails(guestDetails)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }
}
