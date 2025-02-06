package com.pets.scheduler;

import com.pets.service.impl.PetOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional(rollbackFor = Exception.class)
public class OrderStatusScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OrderStatusScheduler.class);

    private final PetOrderServiceImpl petOrderService;

    public OrderStatusScheduler(PetOrderServiceImpl petOrderService) {
        this.petOrderService = petOrderService;
    }

    @Scheduled(cron = "${scheduler.cron.expression}")
    public void updateExpiredOrders() {
        logger.info("检查超时订单任务开始，当前时间: {}", LocalDateTime.now());
        long startTime = System.currentTimeMillis();
        try {
            int updatedRows = petOrderService.updateExpiredOrders();
            long endTime = System.currentTimeMillis();
            logger.info("超时订单更新数量: {}, 总耗时: {}ms", updatedRows, endTime - startTime);
        } catch (DataAccessException dae) {
            logger.error("数据库访问异常，错误信息: {}", dae.getMessage(), dae);
        } catch (Exception e) {
            logger.error("更新超时订单时发生未知异常，错误信息: {}", e.getMessage(), e);
        }

    }
}


