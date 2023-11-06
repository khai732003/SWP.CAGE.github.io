package com.swp.cageshop.controller;

import com.swp.cageshop.DTO.OrderDTO;
import com.swp.cageshop.DTO.OrderDetailDTO;
import com.swp.cageshop.DTO.ProductDTO;
import com.swp.cageshop.entity.OrderDetail;
import com.swp.cageshop.entity.Orders;
import com.swp.cageshop.entity.Products;
import com.swp.cageshop.repository.OrderDetailsRepository;
import com.swp.cageshop.repository.OrdersRepository;
import com.swp.cageshop.repository.ProductsRepository;
import com.swp.cageshop.service.orderdetailService.IOrderDetailService;
import com.swp.cageshop.service.ordersService.IOrdersService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/cageshop/api")
public class OrderDetailController {
    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private IOrderDetailService iOrderDetailService;
    @PostMapping("/order_detail/add")
    public OrderDetailDTO addOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {

        return orderDetailService.addOrderDetail(orderDetailDTO);

    }



    @PutMapping("/order_detail/update/{orderDetailId}")
    public ResponseEntity<?> updateOrderDetail(
            @PathVariable Long orderDetailId,
            @RequestParam int newQuantity,
            @RequestParam double newPrice,
            @RequestBody OrderDetailDTO updatedOrderDetailDTO) {

        try {
            OrderDetailDTO updatedOrderDetail = orderDetailService.updateOrderDetailDTO(orderDetailId, newQuantity, newPrice, updatedOrderDetailDTO);

            if (updatedOrderDetail != null) {
                return ResponseEntity.ok(updatedOrderDetail);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/order_detail/list")
    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetailDTOs();
    }

    @DeleteMapping("/order_detail/{orderId}")
    public List<OrderDetailDTO> getAllOrderDetailsByOrderId(@PathVariable Long orderId) {
        return iOrderDetailService.getAllOrderDetailsByOrderId(orderId);
    }

    @DeleteMapping("/order_detail/delete/{orderId}")
    public ResponseEntity<?> deleteOrderDetailById(@PathVariable Long orderId){
        return iOrderDetailService.deleteById(orderId);
    }

    @GetMapping("/order_detail/listall")
    public List<OrderDetail> getAll() {
        return iOrderDetailService.listAll();
    }
}