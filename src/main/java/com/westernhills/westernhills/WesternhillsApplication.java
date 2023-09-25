package com.westernhills.westernhills;

import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WesternhillsApplication {


	public static void main(String[] args) {
		SpringApplication.run(WesternhillsApplication.class, args);


	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder) {
//		return args->{
//
//			User admin=User.builder().username("admin")
//					.email("admin@gmail.com")
//					.password(encoder.encode("password2"))
//					.roles("ROLE_USER,ROLE_ADMIN")
//					.build();
//			//userRepository.save(admin);
//		};
//	}
//
////	  System.out.println(cartList.get(0).getProduct());





}

