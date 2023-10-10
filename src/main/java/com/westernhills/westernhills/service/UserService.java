package com.westernhills.westernhills.service;

import com.westernhills.westernhills.Otp.EmailUtil;
import com.westernhills.westernhills.Otp.OtpUtil;
import com.westernhills.westernhills.dto.CreateUserRequest;
import com.westernhills.westernhills.dto.OtpDto;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
public class UserService {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    int flag=0;

    @Autowired
    private EmailUtil emailUtil;


    @Autowired
    private OtpUtil otpUtil;








    public OtpDto   createUser(CreateUserRequest createUserRequest ) {
        Optional<Optional<User>> userByName= Optional.ofNullable(userRepository.findByUsername(createUserRequest.getUsername()));



        Optional<User> userByEmail= userRepository.findByEmail(createUserRequest.getEmail());

        if(userByName.isPresent()) {
            System.out.println("email present");


        }else if (userByEmail.isPresent()) {
            System.out.println("email exist");


        }
        else if(!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            System.out.println("password mismatch");
        }


            String otp = otpUtil.generateOtp();
            try {
                emailUtil.sendOtpEmail(createUserRequest.getEmail(),otp);
            } catch (MessagingException  e) {
                throw new RuntimeException("Unable to send email");
            }






            User newUser= User.builder()
                    .username(createUserRequest.getUsername())
                    .email(createUserRequest.getEmail())
                    .otp(otp)
                    .deleted(true)
                    .password(encoder.encode(createUserRequest.getPassword()))
                    .roles("ROLE_USER")
                    .otpGeneratedTime(LocalDateTime.now())
                    .build();
            userRepository.save(newUser);
        OtpDto otpDto = new OtpDto()
                .builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .status("pending")
                .build();

        return otpDto;


    }













    public boolean verifyAccount(OtpDto otpDto) {
        User user = userRepository.findById(otpDto.getId())

                .orElseThrow(() -> new RuntimeException("User not found with this email: " ));
        System.out.println(user);

        if (user.getOtp().equals(otpDto.getOtp())   && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (30 * 60)) {
            System.out.println("otp success");
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }






    public void updateUser(Long id, User user) {
        Optional<User> user_db = userRepository.findById(id);
        User updateUser = user_db.get();
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        userRepository.save(updateUser);
    }



    public User getUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }



    public User findByUsername(String username) {
        Optional<User> userOptional= userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            System.out.println(userOptional);
            return userOptional.get();
        }
        return null;

    }





    public  void blockUser(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(user1 -> {
            user1.setDeleted(false);
            userRepository.save(user1);
        });

    }





    public  void unBlockUser(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(user1 -> {
            user1.setDeleted(true);
            userRepository.save(user1);
        });


    }





}