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
import org.springframework.http.HttpStatus;
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

    public OrderDetailDTO addOrderDetail(OrderDetailDTO orderDetailDTO) {

        double totalCost, hireCost, totalProduct;
        int quantity;

        // Lấy thông tin sản phẩm
        Products product = productsRepository.getReferenceById(orderDetailDTO.getProductId());

        // Kiểm tra xem sản phẩm đã tồn tại trong đơn hàng chưa
        OrderDetail existing = orderDetailRepository.findByOrderIdAndProductId(orderDetailDTO.getOrderId(), orderDetailDTO.getProductId());

        if (existing != null) {
            // Nếu đã tồn tại thì tăng số lượng lên 1
            existing.setQuantity(existing.getQuantity() + 1);

            // Cập nhật lại tổng giá
            totalProduct = product.getTotalPrice() * existing.getQuantity();
            existing.setTotalOfProd(totalProduct);

            totalCost = totalProduct + existing.getHirePrice();
            existing.setTotalCost(totalCost);

            orderDetailRepository.save(existing);

            return modelMapper.map(existing, OrderDetailDTO.class);

        } else {
            // Lấy thông tin hình ảnh sản phẩm
            String productImg = product.getProductImage();

            quantity = orderDetailDTO.getQuantity();
            hireCost = orderDetailDTO.getHirePrice();
            totalProduct = product.getTotalPrice();

            if (quantity > 1) {
                totalProduct = totalProduct * quantity;
            }

            orderDetailDTO.setTotalOfProd(totalProduct);
            totalCost = totalProduct + hireCost;
            orderDetailDTO.setTotalCost(totalCost);
            orderDetailDTO.setProductImg(productImg);

            OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
            orderDetail.setProductImage(productImg);
            orderDetail = orderDetailRepository.save(orderDetail);

            return modelMapper.map(orderDetail, OrderDetailDTO.class);
        }

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

//    @Override
//    public List<OrderDetailDTO> getAllOrderDetailDTOs() {
//        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
//        // Sử dụng Java Stream để chuyển đổi danh sách OrderDetail thành danh sách OrderDetailDTO
//        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
//                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class))
//                .collect(Collectors.toList());
//        return orderDetailDTOs;
//    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetailDTOs() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<OrderDetailDTO> orderDetailDTOs = orderDetails.stream()
            .map(orderDetail -> {
                OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
                String productImage = orderDetail.getProduct().getProductImage();
                orderDetailDTO.setProductImg(productImage);
                return orderDetailDTO;
            })
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

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        try {
            orderDetailRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Đã xóa.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Xóa không thành công.");
        }
    }


}