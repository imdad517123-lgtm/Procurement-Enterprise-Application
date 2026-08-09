package Procurement.Master.Service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Procurement.Master.Dto.ApprovalHistoryResponse;
import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Repository.ApprovalHistoryRepository;

@Service
public class ApprovalHistoryService {

    @Autowired
    private ApprovalHistoryRepository approvalHistoryRepository;

    // Get history by Request ID
  
    public List<ApprovalHistory> getApprovalHistory(Long requestId) {

        return approvalHistoryRepository
                .findByRequisitionRequestIdOrderByActionDateAsc(requestId);

    }



    // Get all history
    public List<ApprovalHistory> getAllHistory() {

        return approvalHistoryRepository.findAll();

    }
    public List<ApprovalHistoryResponse> getApprovalHistoryByEmployee(Long employeeId) {

        List<ApprovalHistory> histories =
                approvalHistoryRepository.findByApprover_EmployeeId(employeeId);

        return histories.stream().map(history -> {

            ApprovalHistoryResponse response =
                    new ApprovalHistoryResponse();

            // =====================================
            // HISTORY ID
            // =====================================

            response.setHistoryId(
                    history.getHistoryId()
            );


            // =====================================
            // ACTION
            // =====================================

            response.setAction(
                    history.getAction()
            );


            // =====================================
            // REMARKS
            // =====================================

            response.setRemarks(
                    history.getRemarks()
            );


            // =====================================
            // ACTION DATE
            // =====================================

            response.setActionDate(
                    history.getActionDate()
            );


            // =====================================
            // REQUISITION
            // =====================================

            if (history.getRequisition() != null) {

                response.setRequestId(
                        history.getRequisition().getRequestId()
                );


                // =================================
                // EMPLOYEE
                // =================================

                if (history.getRequisition().getEmployee() != null) {

                    response.setEmployeeName(
                            history.getRequisition()
                                   .getEmployee()
                                   .getEmployeeName()
                    );

                }
            }


            // =====================================
            // APPROVER
            // =====================================

            if (history.getApprover() != null) {

                response.setApproverName(
                        history.getApprover()
                               .getEmployeeName()
                );

                response.setApproverRole(
                        history.getApprover()
                               .getRole()
                );

            }


            return response;

        }).toList();
    }
}
