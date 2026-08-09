package Procurement.Master.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Procurement.Master.Entity.Delivery;
import Procurement.Master.Entity.PurchaseOrder;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Repository.DeliveryRepository;
import Procurement.Master.Repository.PurchaseOrderRepository;
import Procurement.Master.Repository.PurchaseRequisitionRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseRequisitionRepository purchaseRequisitionRepository;

    public List<Delivery> getSupplierDeliveries(Long supplierId) {

        return deliveryRepository.findByPurchaseOrderSupplierSupplierId(supplierId);

    }

    public Delivery updateStatus(Long deliveryId, String status) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery Not Found"));

        // Update Delivery Status
        delivery.setDeliveryStatus(status);

        // Update Purchase Order Status
        PurchaseOrder po = delivery.getPurchaseOrder();
        po.setStatus(status);
        purchaseOrderRepository.save(po);

        // Update Purchase Requisition only when delivered
        if ("DELIVERED".equalsIgnoreCase(status)) {

            PurchaseRequisition req = po.getPurchaseRequisition();
            req.setStatus("DELIVERED");
            purchaseRequisitionRepository.save(req);
        }

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries(){

        return deliveryRepository.findAll();

    }


}