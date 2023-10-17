package com.swp.cageshop.service.orderdetailService;

import com.swp.cageshop.DTO.OrderDTO;
import com.swp.cageshop.DTO.OrderDetailDTO;
import com.swp.cageshop.entity.OrderDetail;
import com.swp.cageshop.entity.Orders;
import com.swp.cageshop.entity.Products;
import com.swp.cageshop.repository.OrderDetailsRepository;
import com.swp.cageshop.repository.ProductsRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private OrderDetailsRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public OrderDetailDTO addOrderDetail(OrderDetailDTO orderDetailDTO) {
        double totalCost,hireCost,totalProduct;
        int quantity;
        Products product = productsRepository.getReferenceById(orderDetailDTO.getProductId());
        totalProduct = product.getTotalPrice();
        hireCost = orderDetailDTO.getHirePrice();
        quantity = orderDetailDTO.getQuantity();
            if(quantity > 1){
                totalProduct = totalProduct * quantity;
        }
        orderDetailDTO.setTotalOfProd(totalProduct);
            totalCost = totalProduct + hireCost;
        orderDetailDTO.setTotalCost(totalCost);
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail = orderDetailRepository.save(orderDetail);
        OrderDetailDTO result = modelMapper.map(orderDetail, OrderDetailDTO.class);
        return result;
    }




    @Override
    public OrderDetailDTO updateOrderDetailDTO(long orderDetailId, int newQuantity, double newPrice, OrderDetailDTO updatedOrderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElse(null);
        if (orderDetail != null) {
            orderDetail.setQuantity(newQuantity);
            OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
            OrderDetailDTO updatedOrderDetailDTO1 = modelMapper.map(updatedOrderDetail, OrderDetailDTO.class);
            orderDetailRepository.save(updatedOrderDetail);
            return updatedOrderDetailDTO1;
        } else {
            // Xử lý trường hợp không tìm thấy OrderDetail
            throw new ResourceNotFoundException("Không tìm thấy OrderDetail với ID: " + orderDetailId);
        }
    }

    @Override
    public boolean deleteOrderDetailDTO(long id) {
        return false;
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetailDTOs() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        // Sử dụng Java Stream để chuyển đổi danh sách OrderDetail thành danh sách OrderDetailDTO
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
                .collect(Collectors.toList());
        return orderDetailDTOs;
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetailsByOrderId(Long orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orderId);
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetailList) {
            OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
            orderDetailDTOList.add(orderDetailDTO);
        }

        return orderDetailDTOList;
    }

    @Override
    public List<OrderDetail> listAll() {
        return orderDetailRepository.findAll();
    }

}
