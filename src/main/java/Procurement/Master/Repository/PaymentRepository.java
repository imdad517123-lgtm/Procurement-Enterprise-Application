package Procurement.Master.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Procurement.Master.Entity.Payment;


public interface PaymentRepository 
extends JpaRepository<Payment,Long>{


}