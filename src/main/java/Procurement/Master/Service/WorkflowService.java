package Procurement.Master.Service;




import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private PurchaseRequisitionRepository requisitionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ApprovalHistoryRepository approvalHistoryRepository;

    // APPROVE REQUEST
    public void approveRequest(Long requestId, Long approverId, String remarks) {

        PurchaseRequisition requisition = requisitionRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Requisition Not Found"));

        Workflow workflow = workflowRepository.findByRequisition(requisition)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workflow Not Found"));

        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Approver Not Found"));

        // Employee cannot approve own request
        if (requisition.getEmployee().getEmployeeId().equals(approverId)) {
            throw new RuntimeException("Employees cannot approve their own requests.");
        }

        // Only assigned approver
        if (!workflow.getCurrentApprover().getEmployeeId().equals(approverId)) {
            throw new RuntimeException("You are not authorized to approve this request.");
        }

        // Save Approval History
        ApprovalHistory history = new ApprovalHistory();
        history.setRequisition(requisition);
        history.setApprover(approver);
        history.setAction("APPROVED");
        history.setRemarks(remarks);
        history.setActionDate(LocalDateTime.now());

        approvalHistoryRepository.save(history);

        // Level 1 -> Procurement Officer
        if (workflow.getCurrentLevel() == 1) {

            Employee procurementOfficer =
                    employeeRepository.findByRole("PROCUREMENT_OFFICER")
                    .orElseThrow(() ->
                        new ResourceNotFoundException("Procurement Officer Not Found"));

            workflow.setCurrentLevel(2);
            workflow.setCurrentApprover(procurementOfficer);
            workflow.setWorkflowStatus("PENDING");
            requisition.setStatus("PENDING");
        }
        // Level 2 -> Finance
        else if (workflow.getCurrentLevel() == 2) {

            Employee finance =
                    employeeRepository.findByRole("FINANCE")
                    .orElseThrow(() ->
                        new ResourceNotFoundException("Finance Not Found"));

            workflow.setCurrentLevel(3);
            workflow.setCurrentApprover(finance);
            workflow.setWorkflowStatus("PENDING");
        }
        // Final Approval
        else {

            workflow.setWorkflowStatus("APPROVED");
            requisition.setStatus("APPROVED");
        }

        workflowRepository.save(workflow);
        requisitionRepository.save(requisition);
    }

    // REJECT REQUEST
    public void rejectRequest(Long requestId, Long approverId, String remarks) {

        PurchaseRequisition requisition = requisitionRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Requisition Not Found"));

        Workflow workflow = workflowRepository.findByRequisition(requisition)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workflow Not Found"));

        Employee approver = employeeRepository.findById(approverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Approver Not Found"));

        if (!workflow.getCurrentApprover().getEmployeeId().equals(approverId)) {
            throw new RuntimeException("Unauthorized Rejection");
        }

        ApprovalHistory history = new ApprovalHistory();

        history.setRequisition(requisition);
        history.setApprover(approver);
        history.setAction("REJECTED");
        history.setRemarks(remarks);
        history.setActionDate(LocalDateTime.now());

        approvalHistoryRepository.save(history);

        workflow.setWorkflowStatus("REJECTED");
        requisition.setStatus("REJECTED");

        workflowRepository.save(workflow);
        requisitionRepository.save(requisition);
    }

    // WORKFLOW STATUS
    public Workflow getWorkflow(Long requestId) {

        PurchaseRequisition requisition = requisitionRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Requisition Not Found"));

        return workflowRepository.findByRequisition(requisition)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Workflow Not Found"));
    }

    // APPROVAL HISTORY
    public List<ApprovalHistory> getApprovalHistory(Long requestId) {

        PurchaseRequisition requisition = requisitionRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Requisition Not Found"));

        return approvalHistoryRepository.findByRequisition(requisition);
    }

    // PENDING WORKFLOWS
    public List<Workflow> getPendingRequests() {
        return workflowRepository.findByWorkflowStatus("PENDING");
    }

    // APPROVED WORKFLOWS
    public List<Workflow> getApprovedRequests() {
        return workflowRepository.findByWorkflowStatus("APPROVED");
    }
    public List<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }
    // REJECTED WORKFLOWS
    public List<Workflow> getRejectedRequests() {
        return workflowRepository.findByWorkflowStatus("REJECTED");
    }
}