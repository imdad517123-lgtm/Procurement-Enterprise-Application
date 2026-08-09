package Procurement.Master.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Procurement.Master.Dto.ApprovalHistoryResponse;
import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Service.ApprovalHistoryService;

@RestController
@RequestMapping("/api/approval-history")
@CrossOrigin(origins = "*")
public class ApprovalHistoryController {

    @Autowired
    private ApprovalHistoryService approvalHistoryService;

    // Get approval history by Purchase Requisition ID

    @GetMapping("/request/{requestId}")
    public List<ApprovalHistory> getHistory(@PathVariable Long requestId) {
        return approvalHistoryService.getApprovalHistory(requestId);
    }

    // Get all approval history
    @GetMapping("/all")
    public List<ApprovalHistory> getAllHistory() {
        return approvalHistoryService.getAllHistory();
    }
    @GetMapping("/employee/{employeeId}")
   
    public List<ApprovalHistoryResponse> getApprovalHistoryByEmployee(
            @PathVariable Long employeeId) {

        return approvalHistoryService.getApprovalHistoryByEmployee(employeeId);
    }
}
