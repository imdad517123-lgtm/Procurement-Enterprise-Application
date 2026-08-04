package Procurement.Master.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.PurchaseRequisition;



@Repository
public interface ManagerRepository
        extends JpaRepository<PurchaseRequisition, Long>{

    List<PurchaseRequisition> findByStatus(String status);

}