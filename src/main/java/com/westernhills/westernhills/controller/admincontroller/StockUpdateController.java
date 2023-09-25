package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.service.interfaceService.CheckOutService;
import com.westernhills.westernhills.service.interfaceService.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StockUpdateController {

    @Autowired
    private StockUpdateService stockUpdateService;


    @Autowired
    private CheckOutService checkOutService;






}
