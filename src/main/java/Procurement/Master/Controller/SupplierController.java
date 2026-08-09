package Procurement.Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.SupplierRequest;
import Procurement.Master.Entity.PurchaseOrder;
import Procurement.Master.Entity.Supplier;
import Procurement.Master.Service.PurchaseOrderService;
import Procurement.Master.Service.SupplierService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    // ==========================================
    // REGISTER SUPPLIER
    // ==========================================

    @PostMapping("/register")
    public Supplier registerSupplier(
            @RequestBody @Valid SupplierRequest request) {

        return supplierService.saveSupplier(request);
    }


    // ==========================================
    // GET VERIFIED SUPPLIERS
    // ==========================================

    @GetMapping("/verified")
    public List<Supplier> verifiedSuppliers() {

        return supplierService.getVerifiedSuppliers();
    }


    // ==========================================
    // GET SUPPLIER BY ID
    // ==========================================

    @GetMapping("/{id}")
    public Supplier getSupplierById(
            @PathVariable Long id) {

        return supplierService.getSupplierById(id);
    }


    // ==========================================
    // GET SUPPLIER PURCHASE ORDERS
    // ==========================================

    @GetMapping("/purchase-orders/{supplierId}")
    public List<PurchaseOrder> getPurchaseOrders(
            @PathVariable Long supplierId) {

        return purchaseOrderService
                .getPurchaseOrdersBySupplier(supplierId);
    }


    // ==========================================
    // APPROVE SUPPLIER
    // ==========================================

    @PutMapping("/approve/{id}")
    public Supplier approveSupplier(
            @PathVariable Long id) {

        return supplierService.approveSupplier(id);
    }


    // ==========================================
    // REJECT SUPPLIER
    // ==========================================

    @PutMapping("/reject/{id}")
    public Supplier rejectSupplier(
            @PathVariable Long id) {

        return supplierService.rejectSupplier(id);
    }
}