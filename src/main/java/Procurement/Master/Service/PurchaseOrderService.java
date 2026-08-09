package Procurement.Master.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Dto.PurchaseOrderRequest;
import Procurement.Master.Dto.PurchaseOrderResponse;
import Procurement.Master.Entity.Delivery;
import Procurement.Master.Entity.PurchaseOrder;

import Procurement.Master.Entity.Supplier;
import Procurement.Master.Repository.DeliveryRepository;
import Procurement.Master.Repository.PurchaseOrderRepository;
import Procurement.Master.Repository.PurchaseRequisitionRepository;
import Procurement.Master.Repository.SupplierRepository;


@Service
public class PurchaseOrderService {


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    @Autowired
    private PurchaseRequisitionRepository purchaseRequisitionRepository;


    @Autowired
    private SupplierRepository supplierRepository;


@Autowired
private DeliveryRepository deliveryRepository;
    // =====================================
    // CREATE PURCHASE ORDER
    // =====================================

    public String createPurchaseOrder(PurchaseOrderRequest request) {


        PurchaseRequisition requisition =
                purchaseRequisitionRepository.findById(request.getRequestId())
                .orElseThrow(() ->
                new RuntimeException("Request not found"));



        Supplier supplier =
                supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() ->
                new RuntimeException("Supplier not found"));



        PurchaseOrder po = new PurchaseOrder();


        po.setPurchaseRequisition(requisition);

        po.setSupplier(supplier);

        po.setStatus("SENT_TO_SUPPLIER");

        po.setPoDate(LocalDate.now());



        purchaseOrderRepository.save(po);



        return "Purchase Order sent to supplier successfully";

    }





    // =====================================
    // GET ALL PURCHASE ORDERS
    // =====================================

    public List<PurchaseOrderResponse> getAllPurchaseOrders() {


        return purchaseOrderRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }





    // =====================================
    // GET PURCHASE ORDER BY ID
    // =====================================

    public PurchaseOrderResponse getPODetails(Long poId) {


        PurchaseOrder po =
                purchaseOrderRepository.findById(poId)
                .orElseThrow(() ->
                new RuntimeException("Purchase Order not found"));



        return convertToResponse(po);

    }

    public List<PurchaseOrder> getPurchaseOrdersBySupplier(Long supplierId) {

        return purchaseOrderRepository.findBySupplierSupplierId(supplierId);

    }



    // =====================================
    // SUPPLIER RECEIVES PURCHASE ORDERS
    // =====================================

    public List<PurchaseOrderResponse> getSupplierOrders(Long supplierId) {


        return purchaseOrderRepository
                .findBySupplierSupplierId(supplierId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }





    // =====================================
    // SUPPLIER ACCEPTS PO
    // =====================================


    public String acceptPurchaseOrder(Long poId) {

        PurchaseOrder po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase Order Not Found"));

        // Update Purchase Order Status
        po.setStatus("ACCEPTED");
        purchaseOrderRepository.save(po);

        // Create Delivery only if it doesn't already exist
        if (deliveryRepository.findByPurchaseOrder(po).isEmpty()) {

            Delivery delivery = new Delivery();

            delivery.setPurchaseOrder(po);
            delivery.setReceivedQuantity(po.getPurchaseRequisition().getQuantity());
            delivery.setRemarks("Purchase Order Accepted");
            delivery.setDeliveryStatus("PACKED");
            delivery.setDeliveryDate(LocalDateTime.now());

            deliveryRepository.save(delivery);
        }

        return "Purchase Order Accepted Successfully";
    }




    // =====================================
    // SUPPLIER REJECTS PO
    // =====================================

    public String rejectPurchaseOrder(Long poId) {


        PurchaseOrder po =
                purchaseOrderRepository.findById(poId)
                .orElseThrow(() ->
                new RuntimeException("Purchase Order not found"));



        po.setStatus("SUPPLIER_REJECTED");


        purchaseOrderRepository.save(po);



        return "Purchase Order rejected successfully";

    }





    // =====================================
    // ENTITY TO RESPONSE DTO
    // =====================================

    private PurchaseOrderResponse convertToResponse(
            PurchaseOrder po) {


        PurchaseOrderResponse response =
                new PurchaseOrderResponse();



        response.setPoId(po.getPoId());



        response.setRequestId(
                po.getPurchaseRequisition()
                .getRequestId()
        );



        response.setSupplierName(
                po.getSupplier()
                .getSupplierName()
        );



        response.setSupplierEmail(
                po.getSupplier()
                .getEmail()
        );



        response.setItemName(
                po.getPurchaseRequisition()
                .getItemName()
        );



        response.setQuantity(
                po.getPurchaseRequisition()
                .getQuantity()
        );



        response.setEstimatedCost(
                po.getPurchaseRequisition()
                .getEstimatedCost()
        );



        response.setPoDate(
                po.getPoDate()
        );



        response.setStatus(
                po.getStatus()
        );



        return response;

    }
 // =====================================
 // SUPPLIER DELIVERS PO
 // =====================================

 public String deliverPurchaseOrder(Long poId) {

     PurchaseOrder po =
             purchaseOrderRepository.findById(poId)
             .orElseThrow(() ->
             new RuntimeException("Purchase Order not found"));

     po.setStatus("PACKED");

     purchaseOrderRepository.save(po);

     // Optional: Update Purchase Requisition Status
     PurchaseRequisition requisition = po.getPurchaseRequisition();
     requisition.setStatus("DELIVERED");
     purchaseRequisitionRepository.save(requisition);

     return "Purchase Order Delivered Successfully";

 }
 public String updatePurchaseOrderStatus(Long poId, String status) {

	    PurchaseOrder po = purchaseOrderRepository.findById(poId)
	            .orElseThrow(() ->
	                    new RuntimeException("Purchase Order not found"));

	    // Allow only valid statuses
	    switch (status.toUpperCase()) {

	        case "PACKED":
	        case "SHIPPED":
	        case "IN_TRANSIT":
	        case "DELIVERED":
	            po.setStatus(status.toUpperCase());
	            break;

	        default:
	            throw new RuntimeException("Invalid Status");
	    }

	    purchaseOrderRepository.save(po);

	    // Update requisition only after final delivery
	    if ("DELIVERED".equalsIgnoreCase(status)) {

	        PurchaseRequisition requisition = po.getPurchaseRequisition();
	        requisition.setStatus("DELIVERED");
	        purchaseRequisitionRepository.save(requisition);
	    }

	    return "Purchase Order Status Updated to " + status;
	}
}