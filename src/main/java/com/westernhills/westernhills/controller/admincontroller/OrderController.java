    package com.westernhills.westernhills.controller.admincontroller;


    import com.westernhills.westernhills.exceptionHandiling.ExceptionController;
    import com.westernhills.westernhills.entity.userEntity.CheckOut;
    import com.westernhills.westernhills.entity.userEntity.OrderStatus;
    import com.westernhills.westernhills.repo.CheckOutRepository;
    import com.westernhills.westernhills.service.interfaceService.CheckOutService;
    import com.westernhills.westernhills.service.interfaceService.StockUpdateService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.util.Arrays;
    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;
    import java.util.stream.Collectors;

    @Controller
    public class OrderController {


        @Autowired
        private CheckOutRepository checkOutRepository;


        @Autowired
        private CheckOutService checkOutService;



        @Autowired
        private StockUpdateService stockUpdateService;



        @GetMapping("/showAllOrder")
        public String showOrders(Model model){
            List<CheckOut> orders =checkOutRepository.findAll().
                    stream()
                    .filter(order -> !order.isDeleted())
                    .collect(Collectors.toList());
            model.addAttribute("order",orders);

            return "Admin/Order";
        }


        @GetMapping("/orderDetail/{id}")
        public String getOrderDetail(@PathVariable UUID id, Model model) {
            Optional<CheckOut> optionalOrder = checkOutRepository.findById(id);

            if (optionalOrder.isPresent()) {
                CheckOut order = optionalOrder.get();
                model.addAttribute("order", order);


                List<OrderStatus> allStatus = getOrderStatus();
                model.addAttribute("allStatus", allStatus);

                return "Admin/OrderSubmitPage1";
            } else {

                return "redirect:/showAllOrder";
            }
        }




        @PostMapping("/submit")
        public String handleFormSubmission(
                                           @RequestParam("orderId") UUID orderId,
                                           @RequestParam("orderStatus") String orderStatusString,
                                           @AuthenticationPrincipal(expression = "username") String username) {
            OrderStatus orderStatus = convertStringToEnum(orderStatusString);

            System.out.println(orderStatus);



            if (orderStatus==OrderStatus.ORDER_CANCELLED|| orderStatus==OrderStatus.ORDER_RETURNED_CONFIRMED_AND_RETURNED_PAYMENT){
                checkOutService.orderCancellationAndMoneyTransferringInWallet(orderStatus,orderId,username);
                stockUpdateService.orderCancellationAndStockUpdate(orderStatus,orderId,username);

            }else {
               checkOutService.orderStatusSetting(orderStatus,orderId);
            }

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


        @GetMapping("/orderReturn/{orderId}")
        public String orderReturn(@PathVariable UUID orderId){
            checkOutService.orderReturn(orderId);
            return "redirect:/getUserOrder";

        }





        @GetMapping("/trackOrder/{orderId}")
        @ResponseBody
        public ResponseEntity<String> trackOrderPage(@PathVariable UUID orderId) {
            Optional<CheckOut> orderOptional = checkOutRepository.findById(orderId);

            if (orderOptional.isPresent()) {

                String orderStatus = String.valueOf(orderOptional.get().getOrderStatus());
                return ResponseEntity.ok(orderStatus);
            } else {
                return ResponseEntity.notFound().build();
            }
        }










        public List<OrderStatus> getOrderStatus(){
            return Arrays.asList(OrderStatus.values());
        }





    }
