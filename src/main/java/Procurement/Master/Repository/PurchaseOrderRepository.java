package Procurement.Master.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {
	  List<PurchaseOrder> findBySupplierSupplierId(Long supplierId);
	
}