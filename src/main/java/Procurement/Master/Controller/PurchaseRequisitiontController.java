package Procurement.Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.PurchaseRequisitionDto;
import Procurement.Master.Service.PurchaseRequisitionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/requisitions")
@CrossOrigin(origins = "*")
@Validated
public class PurchaseRequisitiontController {

    @Autowired
    private PurchaseRequisitionService requisitionService;

    @PostMapping
    public PurchaseRequisitionDto createRequisition(
            @Valid @RequestBody PurchaseRequisitionDto request) {

        return requisitionService.createRequisition(request);
    }

    @GetMapping
    public List<PurchaseRequisitionDto> getAllRequisitions() {

        return requisitionService.getAllRequisitions();
    }

    @GetMapping("/{id}")
    public PurchaseRequisitionDto getRequisitionById(
            @PathVariable Long id) {

        return requisitionService.getRequisitionById(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PurchaseRequisitionDto> getEmployeeRequisitions(
            @PathVariable Long employeeId) {

        return requisitionService.getRequisitionsByEmployee(employeeId);
    }

    @PutMapping("/{id}")
    public PurchaseRequisitionDto updateRequisition(
            @PathVariable Long id,
            @Valid @RequestBody PurchaseRequisitionDto request) {

        return requisitionService.updateRequisition(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteRequisition(@PathVariable Long id) {

        requisitionService.deleteRequisition(id);

        return "Purchase Requisition Deleted Successfully";
    }
}