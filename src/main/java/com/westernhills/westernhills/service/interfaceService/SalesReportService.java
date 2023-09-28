package com.westernhills.westernhills.service.interfaceService;

import com.westernhills.westernhills.dto.TimePeriod;
import com.westernhills.westernhills.entity.userEntity.CheckOut;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface SalesReportService {

    List<CheckOut> generateDailySalesReport(LocalDate date);

    List<CheckOut> generateMonthlySalesReport(int year, Month month);

    int totalSalesReport();

    int calculateTotalSalesReportForPreviousWeek();

    int generateMonthlySalesReport();


    int generateYearlySalesReport();


    List<CheckOut> getOrderByTimePeriod(TimePeriod timePeriod);


    List<CheckOut> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);


    void generatePdf(List<CheckOut> orders, HttpServletResponse response);


    List<CheckOut> generateYearlySalesReport(int year);

    List<CheckOut> generateCustomSalesReport(LocalDate startDate, LocalDate endDate);

    List<CheckOut> getTopSellingProducts(LocalDate startDate, LocalDate endDate, int limit);

    BigDecimal      getTotalRevenue(LocalDate startDate, LocalDate endDate);




}
