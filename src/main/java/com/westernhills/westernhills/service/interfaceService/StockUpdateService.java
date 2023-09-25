package com.westernhills.westernhills.service.interfaceService;


import com.westernhills.westernhills.entity.admin.StockUpdate;
import com.westernhills.westernhills.entity.userEntity.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface StockUpdateService {


    List<StockUpdate> getAllStock();

    void  save(StockUpdate stock);

    void  orderCancellationAndStockUpdate(OrderStatus orderStatus, UUID id, String userName);






}
