package com.rou00.shopcart.service.order.Impl;

import com.rou00.shopcart.enums.OrderStatus;
import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.dto.OrderDTO;
import com.rou00.shopcart.model.entity.Cart;
import com.rou00.shopcart.model.entity.Order;
import com.rou00.shopcart.model.entity.OrderItem;
import com.rou00.shopcart.model.entity.Product;
import com.rou00.shopcart.repository.CartRepository;
import com.rou00.shopcart.repository.OrderRepository;
import com.rou00.shopcart.repository.ProductRepository;
import com.rou00.shopcart.service.Cart.Impl.CartServiceImpl;
import com.rou00.shopcart.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartServiceImpl cartService;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setTotalAmount(calaculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cart.setTotalAmount(BigDecimal.valueOf(0));
        cartService.clearCart(cart.getId());
        cartRepository.deleteById(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }

    private BigDecimal calaculateTotalAmount(List<OrderItem> orderItems){
        return orderItems.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                                  .reduce(BigDecimal.ZERO,BigDecimal::add);
    }


    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this ::convertToDTO)
                .orElseThrow(() -> new ResourceNotFound("Order not Found"));
    }

    @Override
    public List<OrderDTO> getAllOrderMadeByUsers() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> ordersDto = new ArrayList<>();
        for(Order r :orders){
            ordersDto.add(convertToDTO(r));
        }
        return ordersDto;
    }


    @Override
    public List<OrderDTO> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId)
                .stream().map(this :: convertToDTO)
                .toList();
    }

    @Override
    public OrderDTO convertToDTO(Order order){
        return  modelMapper.map(order,OrderDTO.class);
    }
}
