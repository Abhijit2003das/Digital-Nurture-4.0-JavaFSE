// src/main/java/com/example/orderservice/service/OrderService.java
package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Value("${user.service.url}") // Inject the user service base URL from application.properties
    private String userServiceUrl;

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClient = webClientBuilder.build(); // Build the WebClient instance
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        // 1. Validate User exists by calling User Service
        UserDto userDto = webClient.get()
                .uri(userServiceUrl + "/api/users/{userId}", orderRequest.getUserId())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                          response -> Mono.error(new RuntimeException("User not found with ID: " + orderRequest.getUserId())))
                .bodyToMono(UserDto.class)
                .block(); // Block to make it synchronous for simplicity here. In a reactive app, you'd chain Monos.

        // If userDto is null or user not found, an exception would have been thrown by onStatus.
        // If we reach here, user exists.

        // 2. Create the order
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setStatus(orderRequest.getStatus() != null ? orderRequest.getStatus() : "PENDING"); // Default status

        Order savedOrder = orderRepository.save(order);

        // 3. Construct OrderResponse with user details
        OrderResponse orderResponse = new OrderResponse(savedOrder);
        orderResponse.setUserDetails(userDto); // Attach the fetched user details

        return orderResponse;
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapOrderToOrderResponse) // Helper method to fetch user details for each order
                .collect(Collectors.toList());
    }

    public Optional<OrderResponse> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapOrderToOrderResponse); // Helper method to fetch user details
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::mapOrderToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse updateOrder(Long id, OrderRequest orderDetails) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // You might want to re-validate userId if it's changing
        if (!existingOrder.getUserId().equals(orderDetails.getUserId())) {
             UserDto userDto = webClient.get()
                    .uri(userServiceUrl + "/api/users/{userId}", orderDetails.getUserId())
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals,
                              response -> Mono.error(new RuntimeException("New User not found with ID: " + orderDetails.getUserId())))
                    .bodyToMono(UserDto.class)
                    .block();
             existingOrder.setUserId(orderDetails.getUserId());
        }

        existingOrder.setTotalAmount(orderDetails.getTotalAmount());
        existingOrder.setStatus(orderDetails.getStatus());

        Order updatedOrder = orderRepository.save(existingOrder);
        return mapOrderToOrderResponse(updatedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Helper method to fetch user details and map to OrderResponse
    private OrderResponse mapOrderToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse(order);

        // Fetch user details from User Service
        // Using .block() here for simplicity. In a fully reactive application, you'd compose reactive streams.
        UserDto userDto = webClient.get()
                .uri(userServiceUrl + "/api/users/{userId}", order.getUserId())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, // Handle 404 from User Service gracefully
                          response -> {
                              System.err.println("User not found for order ID: " + order.getId() + ", User ID: " + order.getUserId());
                              return Mono.empty(); // Return an empty Mono if user not found
                          })
                .bodyToMono(UserDto.class)
                .onErrorResume(e -> { // Handle other potential errors during API call
                    System.err.println("Error fetching user for order ID: " + order.getId() + ", User ID: " + order.getUserId() + " - " + e.getMessage());
                    return Mono.empty(); // Return empty Mono on error
                })
                .block(); // Blocking call

        orderResponse.setUserDetails(userDto); // userDto might be null if user not found or error occurred

        return orderResponse;
    }
}