package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.exceptionHandiling.ExceptionController;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.OrderStatus;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class OrderController {




    @Autowired
    private CheckOutRepository checkOutRepository;


    @Autowired
    private CheckOutService checkOutService;



    @GetMapping("/showAllOrder")
    public String showOrders(Model model){
        List<CheckOut> orders =checkOutRepository.findAll().
                stream()
                .filter(order -> !order.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("order",orders);
        List<OrderStatus> allStatus = getOrderStatus();
        model.addAttribute("allStatus", allStatus);
        return "admin/Order";
    }





    @PostMapping("/submit")
    public String handleFormSubmission(
                                       @RequestParam("orderId") UUID orderId,
                                       @RequestParam("orderStatus") String orderStatusString) {
        OrderStatus orderStatus = convertStringToEnum(orderStatusString);
        checkOutService.orderStatusSetting(orderStatus,orderId);


        return "redirect:/showAllOrder";
    }


    public OrderStatus convertStringToEnum(String orderStatusString) {
        try {
            return OrderStatus.valueOf(orderStatusString);
        } catch (ExceptionController e) {

            throw new ExceptionController ("Invalid order status: " + orderStatusString);
        }
    }





    @GetMapping("/getUserOrder")
    public String getUserOrder(Model model,
                               @AuthenticationPrincipal(expression = "username") String username) {
        List<CheckOut> checkOutList = checkOutRepository.findByUser_Username(username);
        model.addAttribute("checkOutList", checkOutList);
        return "orderProduct";
    }



    @GetMapping("/cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable UUID orderId) {
        checkOutService.canselProduct(orderId);
        return "redirect:/getUserOrder";
    }









    public List<OrderStatus> getOrderStatus(){
        return Arrays.asList(OrderStatus.values());
    }





}
