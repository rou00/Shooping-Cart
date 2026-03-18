package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.dto.OrderDTO;
import com.rou00.shopcart.model.entity.Order;
import com.rou00.shopcart.model.entity.User;
import com.rou00.shopcart.service.order.Impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDTO orderDto = orderService.convertToDTO(order);
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
        } catch (Exception e) {
             return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable long orderId){
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userOrders/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDTO> orders = orderService.getUserOrders(userId);
            return new ResponseEntity<>(orders, HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllOrder")
    public List<OrderDTO> getAllOrderMadeByUsers(){
        return orderService.getAllOrderMadeByUsers();
    }
}
