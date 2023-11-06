package com.swp.cageshop.repository;

import com.swp.cageshop.DTO.PaymentDTO;
import com.swp.cageshop.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {

    @Query("SELECT p FROM Pays p WHERE p.paymentCode = :paymentCode")
    Pays findByPaymentCode(@Param("paymentCode") String paymentCode);

    @Query("SELECT p FROM Pays p WHERE p.paymentCode = :paymentCode")
    PaypalPayment findPaypalPaymentByPaymentCode(@Param("paymentCode") String paymentCode);
    @Query("SELECT p.paymentCode FROM Pays p WHERE p.order.id = :orderId ORDER BY p.id DESC")
    String findPaymentCodeByOrderId(Long orderId);

    List<Pays> findByOrder_User_Id(Long userId);

    @Query("SELECT new com.swp.cageshop.DTO.PaymentDTO(p.id, p.createDate, p.price, p.paymentCode, p.status, p.order.id) FROM Pays p WHERE p.status = ?1")
    List<PaymentDTO> findByStatus(String status);


}
