package Procurement.Master.Service;




import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Procurement.Master.Dto.PurchaseRequisitionRequest;
import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Entity.Workflow;
import Procurement.Master.Exception.ResourceNotFoundException;
import Procurement.Master.Repository.ApprovalHistoryRepository;
import Procurement.Master.Repository.EmployeeRepository;
import Procurement.Master.Repository.PurchaseRequisitionRepository;
import Procurement.Master.Repository.WorkflowRepository;

@Service
public class PurchaseRequisitionService {

    @Autowired
    private PurchaseRequisitionRepository requisitionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkflowRepository workflowRepository;
@Autowired 
private ApprovalHistoryRepository  history;
    // Create Purchase Requisition
    public PurchaseRequisition createRequisition(PurchaseRequisitionRequest request) {

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        if (!employee.getRole().equalsIgnoreCase("EMPLOYEE")) {
            throw new RuntimeException("Only employees can create purchase requisitions.");
        }

        PurchaseRequisition requisition = new PurchaseRequisition();
        Optional<PurchaseRequisition> existingRequest =
                requisitionRepository.findTopByEmployeeAndItemNameOrderByCreatedDateDesc(
                        employee,
                        request.getItemName());

        if (existingRequest.isPresent()) {

            PurchaseRequisition existing = existingRequest.get();

            // Don't allow duplicate pending or under review requests
            if (existing.getStatus().equalsIgnoreCase("PENDING") ||
                existing.getStatus().equalsIgnoreCase("UNDER_REVIEW")) {

                throw new RuntimeException(
                        "A request for this item is already in progress.");
            }

            // Allow the same item only after one month from approval
            if (existing.getStatus().equalsIgnoreCase("APPROVED")) {

                LocalDateTime nextAllowedDate =
                        existing.getCreatedDate().plusMonths(1);

                if (LocalDateTime.now().isBefore(nextAllowedDate)) {
                    throw new RuntimeException(
                            "You can request this item again after "
                            + nextAllowedDate.toLocalDate());
                }
            }
        }
        requisition.setEmployee(employee);
        requisition.setItemName(request.getItemName());
        requisition.setQuantity(request.getQuantity());
        requisition.setEstimatedCost(request.getEstimatedCost());
        requisition.setJustification(request.getJustification());
        requisition.setStatus("PENDING");
        requisition.setCreatedDate(LocalDateTime.now());

        PurchaseRequisition savedRequisition = requisitionRepository.save(requisition);

        // Assign first approver (Manager)
        Employee manager = employeeRepository.findByRole("MANAGER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Manager not found"));

        Workflow workflow = new Workflow();
        workflow.setRequisition(savedRequisition);
        workflow.setCurrentLevel(1);
        workflow.setCurrentApprover(manager);
        workflow.setWorkflowStatus("PENDING");

        workflowRepository.save(workflow);

        return savedRequisition;
    }

    // Get All Requisitions
    public List<PurchaseRequisition> getAllRequisitions() {

        return requisitionRepository.findAll();
    }

    // Get Requisition By Id
    public PurchaseRequisition getRequisitionById(Long id) {

        return requisitionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Requisition not found"));
    }

    // Update Requisition
    public PurchaseRequisition updateRequisition(Long id,
            PurchaseRequisitionRequest request) {

        PurchaseRequisition requisition = getRequisitionById(id);

        if (!requisition.getStatus().equalsIgnoreCase("PENDING")) {
            throw new RuntimeException(
                    "Only pending requisitions can be updated.");
        }

        requisition.setItemName(request.getItemName());
        requisition.setQuantity(request.getQuantity());
        requisition.setEstimatedCost(request.getEstimatedCost());
        requisition.setJustification(request.getJustification());

        return requisitionRepository.save(requisition);
    }

    // Delete Requisition
//    public void deleteRequisition(Long id) {
//
//        PurchaseRequisition requisition = getRequisitionById(id);
//
////        if (!requisition.getStatus().equalsIgnoreCase("PENDING")) {
////            throw new RuntimeException(
////                    "Completed requisitions cannot be deleted.");
////        }
//        String status = requisition.getStatus();
//
//        if ("APPROVED".equals(status) || "REJECTED".equals(status)) {
//            throw new RuntimeException("Completed requisitions cannot be deleted.");
//        }
//        Workflow workflow = workflowRepository.findByRequisition(requisition)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Workflow not found"));
//
//        workflowRepository.delete(workflow);
//
//        requisitionRepository.delete(requisition);
//    }
//}
    @Transactional
    public void deleteRequisition(Long id) {

        PurchaseRequisition requisition = getRequisitionById(id);

        String status = requisition.getStatus();

        if ("APPROVED".equalsIgnoreCase(status)
                || "REJECTED".equalsIgnoreCase(status)) {
            throw new RuntimeException("Completed requisitions cannot be deleted.");
        }

        // Delete approval history first
        history.deleteByRequisition(requisition);

        // Delete workflow
        workflowRepository.findByRequisition(requisition)
                .ifPresent(workflowRepository::delete);

        // Finally delete requisition
        requisitionRepository.delete(requisition);
    }
}