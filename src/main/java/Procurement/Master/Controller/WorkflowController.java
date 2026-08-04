package Procurement.Master.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.ApprovalRequest;
import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Entity.Workflow;
import Procurement.Master.Service.WorkflowService;

import java.util.List;

@RestController
@RequestMapping("/api/workflow")
@CrossOrigin(origins = "*")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    // Approve Request
    @PutMapping("/approve/{requestId}")
    public String approveRequest(
            @PathVariable Long requestId,
            @RequestBody ApprovalRequest request) {

        workflowService.approveRequest(
                requestId,
                request.getApproverId(),
                request.getRemarks());

        return "Request Approved Successfully";
    }

    // Reject Request
    @PutMapping("/reject/{requestId}")
    public String rejectRequest(
            @PathVariable Long requestId,
            @RequestBody ApprovalRequest request) {

        workflowService.rejectRequest(
                requestId,
                request.getApproverId(),
                request.getRemarks());

        return "Request Rejected Successfully";
    }

    // Workflow Status
    @GetMapping("/{requestId}")
    public Workflow getWorkflow(
            @PathVariable Long requestId) {

        return workflowService.getWorkflow(requestId);
    }

    // Approval History
    @GetMapping("/history/{requestId}")
    public List<ApprovalHistory> getApprovalHistory(
            @PathVariable Long requestId) {

        return workflowService.getApprovalHistory(requestId);
    }

    // Pending Requests
    @GetMapping("/pending")
    public List<Workflow> getPendingRequests() {

        return workflowService.getPendingRequests();
    }
    @GetMapping
    public List<Workflow> getAllWorkflows() {
        return workflowService.getAllWorkflows();
    }
    // Approved Requests
    @GetMapping("/approved")
    public List<Workflow> getApprovedRequests() {

        return workflowService.getApprovedRequests();
    }

    // Rejected Requests
    @GetMapping("/rejected")
    public List<Workflow> getRejectedRequests() {

        return workflowService.getRejectedRequests();
    }
}