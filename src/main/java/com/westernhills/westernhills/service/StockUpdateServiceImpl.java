package com.westernhills.westernhills.service;


import com.westernhills.westernhills.entity.admin.Product;
import com.westernhills.westernhills.entity.admin.StockUpdate;
import com.westernhills.westernhills.entity.userEntity.CheckOut;
import com.westernhills.westernhills.entity.userEntity.OrderStatus;
import com.westernhills.westernhills.entity.userEntity.User;
import com.westernhills.westernhills.exceptionHandiling.ExceptionController;
import com.westernhills.westernhills.repo.StockUpdateRepository;
import com.westernhills.westernhills.repo.UserRepository;
import com.westernhills.westernhills.service.interfaceService.CheckOutService;
import com.westernhills.westernhills.service.interfaceService.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StockUpdateServiceImpl  implements StockUpdateService {



    @Autowired
    private StockUpdateRepository stockUpdateRepository;



    @Autowired
    private CheckOutService checkOutService;


    @Autowired
    private UserRepository userRepository;




    @Override
    public List<StockUpdate> getAllStock() {
        return stockUpdateRepository.findAll();
    }

    @Override
    public void save(StockUpdate stock) {
        stockUpdateRepository.save(stock);

    }

    @Override
    public void orderCancellationAndStockUpdate(OrderStatus orderStatus,UUID id, String userName) {
        Optional<CheckOut> checkOutProduct=checkOutService.getProductId(id);
        Optional<User> user=userRepository.findByUsername(userName);


        Product product = checkOutProduct.get().getProduct();
        StockUpdate stockUpdate=new StockUpdate();
        stockUpdate.setProduct(product);
        stockUpdate.setReturnStock((long) checkOutProduct.get().getCount());
        stockUpdate.setUser(checkOutProduct.get().getUser());
        stockUpdate.setOrderStatus(orderStatus);
        stockUpdateRepository.save(stockUpdate);

    }


}
