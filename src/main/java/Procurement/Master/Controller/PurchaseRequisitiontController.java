package Procurement.Master.Controller;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import Procurement.Master.Dto.PurchaseRequisitionRequest;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Service.PurchaseRequisitionService;
import java.util.List;
	@RestController
	@RequestMapping("/api/requisitions")
	@CrossOrigin(origins = "*")
	public class PurchaseRequisitiontController {

	    @Autowired
	    private PurchaseRequisitionService requisitionService;

	    // Create Purchase Requisition
	    @PostMapping
	    public PurchaseRequisition createRequisition(
	            @Valid @RequestBody PurchaseRequisitionRequest request) {

	        return requisitionService.createRequisition(request);
	    }

	    // Get All Requisitions
	    @GetMapping
	    public List<PurchaseRequisition> getAllRequisitions() {

	        return requisitionService.getAllRequisitions();
	    }

	    // Get Requisition By Id
	    @GetMapping("/{id}")
	    public PurchaseRequisition getRequisitionById(
	            @PathVariable Long id) {

	        return requisitionService.getRequisitionById(id);
	    }

	    // Update Requisition
	    @PutMapping("/{id}")
	    public PurchaseRequisition updateRequisition(
	            @PathVariable Long id,
	            @Valid @RequestBody PurchaseRequisitionRequest request) {

	        return requisitionService.updateRequisition(id, request);
	    }
	    // Delete Requisition
	    @DeleteMapping("/{id}")
	    public String deleteRequisition(
	            @PathVariable Long id) {

	        requisitionService.deleteRequisition(id);

	        return "Purchase Requisition Deleted Successfully";
	    }
	}