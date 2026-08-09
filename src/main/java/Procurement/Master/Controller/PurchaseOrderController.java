package Procurement.Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.PurchaseOrderRequest;
import Procurement.Master.Dto.PurchaseOrderResponse;
import Procurement.Master.Dto.StatusRequest;
import Procurement.Master.Service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin("*")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService service;

    // ==========================
    // CREATE PURCHASE ORDER
    // ==========================
    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestBody PurchaseOrderRequest request) {

        return ResponseEntity.ok(
                service.createPurchaseOrder(request));
    }

    // ==========================
    // GET PURCHASE ORDERS OF SUPPLIER
    // ==========================
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrderResponse>> supplierOrders(
            @PathVariable Long supplierId) {

        return ResponseEntity.ok(
                service.getSupplierOrders(supplierId));
    }

    // ==========================
    // ACCEPT PURCHASE ORDER
    // ==========================
    @PutMapping("/{poId}/accept")
    public ResponseEntity<String> accept(
            @PathVariable Long poId) {

        return ResponseEntity.ok(
                service.acceptPurchaseOrder(poId));
    }

    // ==========================
    // REJECT PURCHASE ORDER
    // ==========================
    @PutMapping("/{poId}/reject")
    public ResponseEntity<String> reject(
            @PathVariable Long poId) {

        return ResponseEntity.ok(
                service.rejectPurchaseOrder(poId));
    }

    // ==========================
    // DELIVER PURCHASE ORDER
    // ==========================
    @PutMapping("/{poId}/deliver")
    public ResponseEntity<String> deliver(
            @PathVariable Long poId) {

        return ResponseEntity.ok(
                service.deliverPurchaseOrder(poId));
    }
    @PutMapping("/{poId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long poId,
            @RequestBody StatusRequest request) {

        return ResponseEntity.ok(
                service.updatePurchaseOrderStatus(
                        poId,
                        request.getStatus()));
    }
}