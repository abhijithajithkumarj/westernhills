package com.westernhills.westernhills.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.westernhills.westernhills.dto.CsvDto;
import com.westernhills.westernhills.dto.TimePeriod;
import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.repo.CheckOutRepository;
import com.westernhills.westernhills.service.interfaceService.CheckOutService;
import com.westernhills.westernhills.service.interfaceService.ProductService;
import com.westernhills.westernhills.service.interfaceService.SalesReportService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SalesReportServiceImpl implements SalesReportService {

    @Autowired
    private CheckOutService checkOutService;


    @Autowired
    private CheckOutRepository checkOutRepository;

    @Override
    public List<CheckOut> generateDailySalesReport(LocalDate date) {

        LocalDate endOfWeek=date.minusDays(1);
        LocalDate StartOfWeek=endOfWeek.minus(6, ChronoUnit.DAYS);


        Date startDate=Date.from(StartOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(endOfWeek.atTime(23 ,59,59).atZone(ZoneId.systemDefault()).toInstant());


        List<CheckOut> getAllSalesData=checkOutService.findAll();

        return getAllSalesData.
                stream().
                filter(checkOut -> checkOut.getCreatedAt().after(startDate)&&checkOut.getCreatedAt().before(endDate)).
                collect(Collectors.toList());
    }




    @Override
    public int calculateTotalSalesReportForPreviousWeek() {

        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeekStartDate = currentDate.minusDays(6);
        LocalDate lastWeekEndDate = currentDate.minusDays(1);


        Date startDateInclusive = Date.from(lastWeekStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateExclusive = Date.from(lastWeekEndDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());


        List<CheckOut> allSalesData = checkOutService.findAll();
        List<CheckOut> salesDataInRange = allSalesData.stream()
                .filter(checkOut -> checkOut.getCreatedAt().after(startDateInclusive) && checkOut.getCreatedAt().before(endDateExclusive))
                .collect(Collectors.toList());


        return salesDataInRange.stream()
                .mapToInt(CheckOut::getCount)
                .sum();
    }



    @Override
    public int generateMonthlySalesReport() {
        YearMonth currentYearMonth = YearMonth.now();

        LocalDate startDate = currentYearMonth.atDay(1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        Date startDateInclusive = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateExclusive = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<CheckOut> allSalesData = checkOutService.findAll();


        List<CheckOut> salesDataInCurrentMonth = allSalesData.stream()
                .filter(checkOut -> {
                    Date createdAt = checkOut.getCreatedAt();
                    boolean isAfterStart = createdAt.after(startDateInclusive);
                    boolean isBeforeEnd = createdAt.before(endDateExclusive);


                    return isAfterStart && isBeforeEnd;
                })
                .collect(Collectors.toList());

        return salesDataInCurrentMonth.stream()
                .mapToInt(CheckOut::getCount)
                .sum();
    }



    @Override
    public int generateYearlySalesReport() {

        Year currentYear = Year.now();


        LocalDate startDate = LocalDate.of(currentYear.getValue(), 1, 1);
        LocalDate endDate = LocalDate.of(currentYear.getValue(), 12, 31);


        Date startDateInclusive = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateExclusive = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());


        List<CheckOut> allSalesData = checkOutService.findAll();
        List<CheckOut> salesDataInCurrentYear = allSalesData.stream()
                .filter(checkOut -> checkOut.getCreatedAt().after(startDateInclusive) && checkOut.getCreatedAt().before(endDateExclusive))
                .collect(Collectors.toList());


        return salesDataInCurrentYear.stream()
                .mapToInt(CheckOut::getCount)
                .sum();
    }

//    @Override
//    public void exportToCSV(List<CheckOut> orders, HttpServletResponse response) {
//
//    }


    @Override
    public List<CheckOut> getOrderByTimePeriod(TimePeriod timePeriod) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        switch (timePeriod) {
            case daily:
                startDate = endDate;
                break;
            case weekly:
                startDate = endDate.minusDays(6);
                break;
            case monthly:
                startDate = endDate.minusMonths(1);
                break;
            case  yearly:
                startDate = endDate.minusYears(1);
                break;
            default:
                throw new IllegalArgumentException("Unsupported time period: " + timePeriod);
        }

        return checkOutRepository.findByCreatedAtBetween(
                Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().plusSeconds(86400))
        );
    }






    @Override
    public List<CheckOut> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate) {
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().plusSeconds(86400));
        return checkOutRepository.findByCreatedAtBetween(start, end);
    }





    @Override
    public void generatePdf(List<CheckOut> orders, HttpServletResponse response) {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=salesReport.pdf";
        response.setHeader(headerKey, headerValue);

        try  {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(8);
            float[] columnWidths = {1f, 4f, 2f, 2f, 2f, 1.5f, 1.5f, 1.5f}; // Adjust column widths as needed
            table.setWidths(columnWidths);
            table.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(new Phrase("Sales Report", headerFont));
            cell.setColspan(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);

            PdfPCell headerCell = new PdfPCell();
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            headerCell.setPadding(5);


            headerCell.setPhrase(new Phrase("SN", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Order ID", headerFont)); // Uncomment if you want Order ID
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("User", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Product", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Order Date", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Status", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Price", headerFont));
            table.addCell(headerCell);

            headerCell.setPhrase(new Phrase("Payment", headerFont));
            table.addCell(headerCell);

            int sn = 1;
            for (CheckOut order : orders) {
                PdfPCell dataCell = new PdfPCell();
                dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                dataCell.setPadding(4);

                dataCell.setPhrase(new Phrase(String.valueOf(sn), cellFont));
                table.addCell(dataCell);
                sn++;

                dataCell.setPhrase(new Phrase(order.getId().toString(), cellFont));

                dataCell.setPhrase(new Phrase(order.getUser().getUsername(), cellFont));
                table.addCell(dataCell);

                dataCell.setPhrase(new Phrase(order.getProduct().getName(), cellFont));
                table.addCell(dataCell);

                dataCell.setPhrase(new Phrase(order.getCreatedAt().toString(), cellFont));
                table.addCell(dataCell);

                dataCell.setPhrase(new Phrase(order.getOrderStatus().toString(), cellFont));
                table.addCell(dataCell);

                dataCell.setPhrase(new Phrase(String.valueOf(order.getProduct().getSelPrice()), cellFont));
                table.addCell(dataCell);

                dataCell.setPhrase(new Phrase(String.valueOf(order.getPaymentMethod()), cellFont));
                table.addCell(dataCell);
            }






            PdfPCell summaryCell = new PdfPCell(new Phrase("Summary", headerFont));
            summaryCell.setColspan(2);
            summaryCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(summaryCell);

            double totalSales = orders.stream().mapToDouble(order-> order.getProduct().getSelPrice()).sum();


            int totalOrders = orders.size();

            PdfPCell totalOrdersCell = new PdfPCell(new Phrase("Total Orders: " + totalOrders, cellFont));
            totalOrdersCell.setColspan(3);
            totalOrdersCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(totalOrdersCell);

            PdfPCell totalSalesCell = new PdfPCell(new Phrase("Total Sales: " + totalSales, cellFont));
            totalSalesCell.setColspan(3);
            totalSalesCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalSalesCell);
            document.add(table);
            document.close();






        }catch (DocumentException | IOException e){
            e.printStackTrace();
        }
    }




    @Override
    public void exportToCSV(List<CheckOut> orders, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; attachment;filename=salesReport.csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"ORDER ID", "USER", "PRODUCT", "ORDER DATE", "STATUS", "PRICE", "PAYMENT"};
        String[] nameMapping = {"orderId", "username","productName","orderDate","status","totalPrice","paymentMode"};
        csvBeanWriter.writeHeader(csvHeader);

        List<CsvDto> orderCsvDtoList = new ArrayList<>();


        for (CheckOut order : orders) {
            String productName = order.getProduct().getName();
            CsvDto orderCsvDto = getCsvDto(order, productName);
            orderCsvDtoList.add(orderCsvDto);
        }


        for (CsvDto orderCsvDto :orderCsvDtoList){
            csvBeanWriter.write(orderCsvDto, nameMapping);
        }


        Double totalSales =calculateTotalSales(orders);
        csvBeanWriter.writeHeader("TOTAL SALES ",String.valueOf(totalSales));
        int totalOrderCount = orders.size();
        csvBeanWriter.writeHeader("TOTAL ORDER COUNT ",String.valueOf(totalOrderCount));

        csvBeanWriter.close();
    }

    @NotNull
    private static CsvDto getCsvDto(CheckOut order, String productName) {
        CsvDto orderCsvDto = new CsvDto();

        orderCsvDto.setOrderId(String.valueOf(order.getId()));
        orderCsvDto.setUsername(order.getUser().getUsername());
        orderCsvDto.setTotalPrice(order.getProduct().getSelPrice());
        orderCsvDto.setOrderDate(order.getCreatedAt());
        orderCsvDto.setPaymentMode(String.valueOf(order.getPaymentMethod()));
        orderCsvDto.setStatus(String.valueOf(order.getOrderStatus()));
        orderCsvDto.setProductName(productName);
        return orderCsvDto;
    }


    public Double calculateTotalSales(List<CheckOut> checkOuts){
        double totalPrice=0;
        for (CheckOut orders:checkOuts) {
            totalPrice +=orders.getProduct().getCostPrice();
        }
        return totalPrice;
                
        
    }
    
    




    @Override
    public int totalSalesReport() {
        List<CheckOut> totalSalesReport = checkOutService.findAll();


        return totalSalesReport.stream()
                .mapToInt(CheckOut::getCount)
                .sum();

    }


















}
