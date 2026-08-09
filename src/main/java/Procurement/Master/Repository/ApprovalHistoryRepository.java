package Procurement.Master.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Entity.Workflow;

import java.util.List;

@Repository
public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Long> {

	 List<ApprovalHistory> findByRequisition(PurchaseRequisition requisition);

	    List<ApprovalHistory> findByApprover(Employee approver);

//		void deleteByRequestId(Long id);
	    void deleteByRequisition(PurchaseRequisition requisition);

	    List<ApprovalHistory> findByRequisitionEmployeeEmployeeId(Long employeeId);
	    List<ApprovalHistory> findByRequisitionRequestIdOrderByActionDateAsc(Long requestId);

	    List<ApprovalHistory> findByApprover_EmployeeId(Long employeeId);
}