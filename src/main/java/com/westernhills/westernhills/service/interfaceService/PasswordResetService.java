package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.dto.OtpDto;
import com.westernhills.westernhills.dto.ResetPassDto;
import com.westernhills.westernhills.entity.userEntity.User;

public interface PasswordResetService {

 User resetPass(ResetPassDto password);


 boolean verifyEmail(ResetPassDto resetPassDto);

 User passwordUpdate(ResetPassDto resetPassDto);




}
