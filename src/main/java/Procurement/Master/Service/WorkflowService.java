package Procurement.Master.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Procurement.Master.Entity.ApprovalHierarchy;
import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.Workflow;
import Procurement.Master.Repository.ApprovalHistoryRepository;
import Procurement.Master.Repository.ApproverHierarchyRepository;
import Procurement.Master.Repository.EmployeeRepository;
import Procurement.Master.Repository.PurchaseRequisitionRepository;
import Procurement.Master.Repository.WorkflowRepository;

@Service
public class WorkflowService {

    @Autowired
    private ApproverHierarchyRepository app;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private PurchaseRequisitionRepository requisitionRepository;

    @Autowired
    private EmployeeRepository emp;

    @Autowired
    private ApprovalHistoryRepository approvalHistoryRepository;


    // =====================================================
    // GET PENDING REQUESTS FOR LOGGED USER
    // =====================================================

    public List<Workflow> getPendingRequests(Employee employee) {

        return workflowRepository
                .findByCurrentApproverAndWorkflowStatus(
                        employee,
                        "PENDING");
    }


    // =====================================================
    // APPROVE REQUEST
    // =====================================================

    @Transactional
    public String approveRequest(
            Long requestId,
            Employee employee,
            String remarks) {

        Workflow workflow =
                workflowRepository
                        .findByRequisition_RequestId(requestId);

        if (workflow == null) {
            throw new RuntimeException("Workflow not found");
        }


        // =================================================
        // AUTHORIZATION CHECK
        // =================================================

        if (workflow.getCurrentApprover() == null) {
            throw new RuntimeException(
                    "No approver assigned to this request");
        }

        if (!workflow.getCurrentApprover()
                .getEmployeeId()
                .equals(employee.getEmployeeId())) {

            throw new RuntimeException(
                    "You are not authorized to approve this request");
        }


        // =================================================
        // MANAGER APPROVAL
        // =================================================

        if ("MANAGER".equalsIgnoreCase(employee.getRole())) {

            workflow.getRequisition()
                    .setStatus("MANAGER_APPROVED");

            requisitionRepository
                    .save(workflow.getRequisition());

            // Save history
            saveApprovalHistory(
                    workflow.getRequisition(),
                    employee,
                    "MANAGER_APPROVED",
                    remarks,
                    "Approved by Manager");


            // Find next approval level
            int nextLevel =
                    workflow.getCurrentLevel() + 1;

            Optional<ApprovalHierarchy> nextHierarchy =
                    app.findByLevel(nextLevel);


            if (nextHierarchy.isPresent()) {

                ApprovalHierarchy hierarchy =
                        nextHierarchy.get();

                Employee nextApprover =
                        emp.findByRole(
                                hierarchy.getRole())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Next Approver Not Found"));


                workflow.setCurrentLevel(nextLevel);

                workflow.setCurrentApprover(
                        nextApprover);

                workflow.setApprovalHierarchy(
                        hierarchy);

                workflow.setWorkflowStatus(
                        "PENDING");

            } else {

                workflow.setWorkflowStatus(
                        "APPROVED");

                workflow.setCurrentApprover(
                        null);
            }
        }


        // =================================================
        // PROCUREMENT OFFICER APPROVAL
        // =================================================

        else if ("PROCUREMENT_OFFICER"
                .equalsIgnoreCase(employee.getRole())) {

            workflow.getRequisition()
                    .setStatus("PROCUREMENT_APPROVED");

            requisitionRepository
                    .save(workflow.getRequisition());


            // Save history
            saveApprovalHistory(
                    workflow.getRequisition(),
                    employee,
                    "PROCUREMENT_APPROVED",
                    remarks,
                    "Approved by Procurement Officer");


            // ---------------------------------------------
            // Move to next level
            // ---------------------------------------------

            int nextLevel =
                    workflow.getCurrentLevel() + 1;

            Optional<ApprovalHierarchy> nextHierarchy =
                    app.findByLevel(nextLevel);


            if (nextHierarchy.isPresent()) {

                ApprovalHierarchy hierarchy =
                        nextHierarchy.get();

                Employee nextApprover =
                        emp.findByRole(
                                hierarchy.getRole())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Next Approver Not Found"));


                workflow.setCurrentLevel(
                        nextLevel);

                workflow.setCurrentApprover(
                        nextApprover);

                workflow.setApprovalHierarchy(
                        hierarchy);

                workflow.setWorkflowStatus(
                        "PENDING");

            } else {

                workflow.setWorkflowStatus(
                        "APPROVED");

                workflow.setCurrentApprover(
                        null);
            }
        }


        // =================================================
        // OTHER APPROVAL ROLES
        // =================================================

        else {

            workflow.getRequisition()
                    .setStatus("FINANCE_APPROVED");

            requisitionRepository
                    .save(workflow.getRequisition());


            saveApprovalHistory(
                    workflow.getRequisition(),
                    employee,
                    "FINANCE_APPROVED",
                    remarks,
                    "Approved by Finance Manager");


            workflow.setWorkflowStatus(
                    "APPROVED");

            workflow.setCurrentApprover(
                    null);
        }


        // =================================================
        // SAVE WORKFLOW
        // =================================================

        workflowRepository.save(workflow);

        return "Request Approved Successfully";
    }


    // =====================================================
    // REJECT REQUEST
    // =====================================================

    @Transactional
    public String rejectRequest(
            Long requestId,
            Employee employee,
            String remarks) {

        Workflow workflow =
                workflowRepository
                        .findByRequisition_RequestId(requestId);

        if (workflow == null) {
            throw new RuntimeException(
                    "Workflow not found");
        }


        // =================================================
        // AUTHORIZATION CHECK
        // =================================================

        if (workflow.getCurrentApprover() == null) {
            throw new RuntimeException(
                    "No approver assigned to this request");
        }

        if (!workflow.getCurrentApprover()
                .getEmployeeId()
                .equals(employee.getEmployeeId())) {

            throw new RuntimeException(
                    "Not Authorized");
        }


        // =================================================
        // UPDATE WORKFLOW
        // =================================================

        workflow.setWorkflowStatus(
                "REJECTED");

        workflow.setCurrentApprover(
                null);


        // =================================================
        // UPDATE REQUEST
        // =================================================

        workflow.getRequisition()
                .setStatus("REJECTED");

        requisitionRepository
                .save(workflow.getRequisition());


        // =================================================
        // SAVE APPROVAL HISTORY
        // =================================================

        String action;

        String defaultRemarks;


        if ("MANAGER".equalsIgnoreCase(
                employee.getRole())) {

            action = "MANAGER_REJECTED";

            defaultRemarks =
                    "Rejected by Manager";

        } else if ("PROCUREMENT_OFFICER"
                .equalsIgnoreCase(employee.getRole())) {

            action = "PROCUREMENT_REJECTED";

            defaultRemarks =
                    "Rejected by Procurement Officer";

        } else {

            action = "FINANCE_REJECTED";

            defaultRemarks =
                    "Rejected by Finance Manager";
        }


        saveApprovalHistory(
                workflow.getRequisition(),
                employee,
                action,
                remarks,
                defaultRemarks);


        // =================================================
        // SAVE WORKFLOW
        // =================================================

        workflowRepository.save(workflow);

        return "Request Rejected Successfully";
    }


    // =====================================================
    // SAVE APPROVAL HISTORY
    // =====================================================

    private void saveApprovalHistory(
            Procurement.Master.Entity.PurchaseRequisition requisition,
            Employee employee,
            String action,
            String remarks,
            String defaultRemarks) {

        ApprovalHistory history =
                new ApprovalHistory();

        history.setRequisition(
                requisition);

        history.setApprover(
                employee);

        history.setAction(
                action);

        if (remarks == null ||
                remarks.isBlank()) {

            history.setRemarks(
                    defaultRemarks);

        } else {

            history.setRemarks(
                    remarks);
        }

        history.setActionDate(
                LocalDateTime.now());

        approvalHistoryRepository
                .save(history);
    }


    // =====================================================
    // GET ONE WORKFLOW
    // =====================================================

    public Workflow getWorkflow(
            Long requestId) {

        return workflowRepository
                .findByRequisition_RequestId(
                        requestId);
    }


    // =====================================================
    // GET ALL WORKFLOWS
    // =====================================================

    public List<Workflow> getAllWorkflows() {

        return workflowRepository.findAll();
    }


    // =====================================================
    // APPROVED
    // =====================================================

    public List<Workflow> getApprovedRequests() {

        return workflowRepository
                .findByWorkflowStatus(
                        "APPROVED");
    }


    // =====================================================
    // REJECTED
    // =====================================================

    public List<Workflow> getRejectedRequests() {

        return workflowRepository
                .findByWorkflowStatus(
                        "REJECTED");
    }
}