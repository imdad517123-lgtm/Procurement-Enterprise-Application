package Procurement.Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.PurchaseOrderRequest;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Service.ProcurementService;

@RestController
@RequestMapping("/api/procurement")
@CrossOrigin(origins = "*")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @GetMapping("/pending")
    public ResponseEntity<List<PurchaseRequisition>> getPendingRequests() {

        List<PurchaseRequisition> requests = procurementService.getPendingRequests();
        return ResponseEntity.ok(requests);

    }

    @PostMapping("/create-po")
    public ResponseEntity<String> createPurchaseOrder(
            @RequestBody PurchaseOrderRequest request) {

        procurementService.createPurchaseOrder(request);

        return ResponseEntity.ok("Purchase Order Created Successfully");
    }
    @PutMapping("/approve/{requestId}")
    public ResponseEntity<String> approveRequest(
            @PathVariable Long requestId) {

        procurementService.approveRequest(requestId);

        return ResponseEntity.ok("Approved Successfully");
    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<String> rejectRequest(
            @PathVariable Long requestId) {

        procurementService.rejectRequest(requestId);

        return ResponseEntity.ok("Rejected Successfully");
    }
}