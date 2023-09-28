package com.westernhills.westernhills.controller.admincontroller;


import com.westernhills.westernhills.dto.TimePeriod;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.service.interfaceService.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class SalesReportController {


    @Autowired
    private SalesReportService salesReportService;








    @GetMapping("/ShowSalesReport")
    public String getSalesReport(Model model){
        int totalSalesReport=salesReportService.totalSalesReport();
        int totalSalesReportForPreviousWeek=salesReportService.calculateTotalSalesReportForPreviousWeek();
        int totalGenerateMonthlySalesReport=salesReportService.generateMonthlySalesReport();
        int totalGenerateYearlySalesReport=salesReportService.generateYearlySalesReport();




        model.addAttribute("totalSalesReport",totalSalesReport);
        model.addAttribute("totalSalesReportForPreviousWeek",totalSalesReportForPreviousWeek);
        model.addAttribute("totalGenerateMonthlySalesReport",totalGenerateMonthlySalesReport);
        model.addAttribute("totalGenerateYearlySalesReport",totalGenerateYearlySalesReport);
        return "admin/salesReportPage";
    }



    @PostMapping("/dateSalesReport")
    public String checkOutByStarAndEndDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate endDate,
                                           Model model){

        List<CheckOut> orders= salesReportService.findByCreatedAtBetween(startDate,endDate);
        model.addAttribute("orders",orders);
        return "admin/salesReportList";
    }




    @PostMapping("/salesReportForAll")
    public String adminDashBoard(@RequestParam("selectedTimePeriod") TimePeriod selectedTimePeriod,
                                 HttpSession session, Model model){
        System.out.println(selectedTimePeriod);

        List<CheckOut> orders = salesReportService.getOrderByTimePeriod(selectedTimePeriod);
        String token= UUID.randomUUID().toString();
        session.setAttribute(token ,orders);
        model.addAttribute("token",token);
        System.out.println(token);


        model.addAttribute("orders",orders);
        return "admin/salesReportList";
    }


    @GetMapping("/generatePdf")
    public void generatePdf(HttpServletResponse response, HttpSession session,
                              HttpServletRequest request){
        String token = request.getParameter("token");


        List<CheckOut> orders = (List<CheckOut>) session.getAttribute(token);
        System.out.println(token);

        if (orders != null) {
            salesReportService.generatePdf(orders, response);
        } else {
            System.out.println("not found");
        }

    }





    @GetMapping("/daily")
    public String generateSalesReport(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<CheckOut> salesReport;
        if (date != null) {

            salesReport = salesReportService.generateDailySalesReport(date);
        } else {

            LocalDate currentDate = LocalDate.now();
            LocalDate previousWeekEndDate = currentDate.minusDays(1);
            salesReport = salesReportService.generateDailySalesReport(previousWeekEndDate);
        }


        model.addAttribute("salesReport", salesReport);


        return "salesReportView";
    }







}
