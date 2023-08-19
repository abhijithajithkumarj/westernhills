package com.westernhills.westernhills.service;


import com.westernhills.westernhills.Otp.EmailUtil;
import com.westernhills.westernhills.Otp.OtpUtil;
import com.westernhills.westernhills.dto.ResetPassDto;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailUtil emailUtil;


    @Autowired
    private OtpUtil otpUtil;


    @Override
    public User resetPass(ResetPassDto resetPassDto) {
        Optional<User> userEmail= userRepository.findByEmail(resetPassDto.getEmail());
        System.out.println("User email: " + userEmail.get());


        if (userEmail.isPresent()){


            User user=userEmail.get();
            String otp = otpUtil.generateOtp();
            user.setOtp(otp);

            try{
                emailUtil.sendOtpEmail(resetPassDto.getEmail(),otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send email");
            }

            return userRepository.save(user);
        }else {
            System.out.println("email is not available");
        }


        return null;
    }



    @Override
    public boolean verifyEmail(ResetPassDto resetPassDto) {
        Optional<User> user = userRepository.findByEmail(resetPassDto.getEmail());

        User user1=user.get();
        if (user1.getOtp().equals(resetPassDto.getOtp())){
            System.out.println("otp success");
            return true;

        }

        return false;
    }

    @Override
    public User passwordUpdate(ResetPassDto resetPassDto) {

        Optional<User> userPassword = userRepository.findByEmail(resetPassDto.getEmail());
        User user1=userPassword.get();
        System.out.println(user1);
        user1.setPassword(encoder.encode(resetPassDto.getPassword()));
        userRepository.save(user1);
        return null;
    }


}
