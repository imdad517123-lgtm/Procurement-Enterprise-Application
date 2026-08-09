package Procurement.Master.Repository;






import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Procurement.Master.Entity.Delivery;
import Procurement.Master.Entity.PurchaseOrder;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByPurchaseOrderSupplierSupplierId(Long supplierId);
   

    Optional<Delivery> findByPurchaseOrder(PurchaseOrder purchaseOrder);

}