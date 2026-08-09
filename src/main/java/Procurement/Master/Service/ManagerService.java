package Procurement.Master.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Repository.ApprovalHistoryRepository;
import Procurement.Master.Repository.EmployeeRepository;
import Procurement.Master.Repository.ManagerRepository;

@Service
public class ManagerService {
	@Autowired
	private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ApprovalHistoryRepository historyRepository;

    public List<PurchaseRequisition> getManagerRequests() {

        return managerRepository.findByStatus("PENDING");
    }
    public String approveRequest(Long requestId) {

        PurchaseRequisition request = managerRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Not Found"));

        request.setStatus("MANAGER_APPROVED");
        managerRepository.save(request);

        Employee manager = employeeRepository.findByRole("MANAGER")
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        ApprovalHistory history = new ApprovalHistory();

        history.setRequisition(request);
        history.setApprover(manager);
        history.setAction("MANAGER_APPROVED");
        history.setRemarks("Successfully Approved by Manager");
        history.setActionDate(LocalDateTime.now());

        historyRepository.save(history);

        return "Successfully Approved by Manager";
    }

    public String rejectRequest(Long requestId) {

        PurchaseRequisition request = managerRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Not Found"));

        request.setStatus("REJECTED");
        managerRepository.save(request);

        Employee manager = employeeRepository.findByRole("MANAGER")
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        ApprovalHistory history = new ApprovalHistory();

        history.setRequisition(request);
        history.setApprover(manager);
        history.setAction("MANAGER_REJECTED");
        history.setRemarks("Rejected by Manager");
        history.setActionDate(LocalDateTime.now());

        historyRepository.save(history);

        return "Request Rejected by Manager";
    }
}