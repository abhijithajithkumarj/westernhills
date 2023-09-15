package com.westernhills.westernhills.controller.admincontroller.offerpage;


import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.OrderStatus;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderStatusAdminController {

    @Autowired
    private CheckOutRepository checkOutRepository;


    @Autowired
    private CheckOutService checkOutService;










}
