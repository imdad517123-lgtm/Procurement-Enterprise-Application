package Procurement.Master.Service;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Procurement.Master.Dto.PurchaseOrderRequest;
import Procurement.Master.Entity.ApprovalHistory;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseOrder;
import Procurement.Master.Entity.PurchaseRequisition;
import Procurement.Master.Entity.Supplier;
import Procurement.Master.Repository.ApprovalHistoryRepository;
import Procurement.Master.Repository.EmployeeRepository;
import Procurement.Master.Repository.PurchaseOrderRepository;
import Procurement.Master.Repository.PurchaseRequisitionRepository;
import Procurement.Master.Repository.SupplierRepository;


@Service
@Transactional
public class ProcurementService {


    @Autowired
    private PurchaseRequisitionRepository requisitionRepository;


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private ApprovalHistoryRepository approvalHistoryRepository;


    @Autowired
    private EmployeeRepository employeeRepository;



    // =====================================
    // GET MANAGER APPROVED REQUESTS
    // =====================================

    public List<PurchaseRequisition> getPendingRequests() {


        return requisitionRepository.findByStatusIn(
                List.of(
                        "MANAGER_APPROVED",
                        "PROCUREMENT_APPROVED"
                )
        );

    }



    // =====================================
    // CREATE PURCHASE ORDER
    // =====================================

    public String createPurchaseOrder(
            PurchaseOrderRequest request) {


        PurchaseRequisition requisition =
                requisitionRepository.findById(
                        request.getRequestId()
                )
                .orElseThrow(() ->
                new RuntimeException("Request Not Found"));



        Supplier supplier =
                supplierRepository.findById(
                        request.getSupplierId()
                )
                .orElseThrow(() ->
                new RuntimeException("Supplier Not Found"));



        PurchaseOrder po = new PurchaseOrder();



        po.setPurchaseRequisition(requisition);


        po.setSupplier(supplier);


        po.setStatus("SENT_TO_SUPPLIER");


        po.setPoDate(LocalDate.now());



        purchaseOrderRepository.save(po);



        requisition.setStatus("PO_CREATED");


        requisitionRepository.save(requisition);



        return "Purchase Order sent to supplier successfully";

    }





    // =====================================
    // GET ALL PURCHASE ORDERS
    // =====================================

    public List<PurchaseOrder> getAllPurchaseOrders() {


        return purchaseOrderRepository.findAll();

    }





    // =====================================
    // GET PURCHASE ORDER DETAILS
    // =====================================

    public PurchaseOrder getPODetails(Long poId) {


        return purchaseOrderRepository.findById(poId)

                .orElseThrow(() ->
                new RuntimeException("PO Not Found"));

    }





    // =====================================
    // PROCUREMENT APPROVE
    // =====================================

    public String approveRequest(Long requestId) {


        PurchaseRequisition requisition =
                requisitionRepository.findById(requestId)

                .orElseThrow(() ->
                new RuntimeException("Request Not Found"));



        requisition.setStatus("PROCUREMENT_APPROVED");


        requisitionRepository.save(requisition);



        Employee procurementOfficer =
                employeeRepository.findByRole(
                        "PROCUREMENT_OFFICER"
                )
                .orElseThrow(() ->
                new RuntimeException(
                "Procurement Officer Not Found"));



        ApprovalHistory history =
                new ApprovalHistory();


        history.setRequisition(requisition);


        history.setApprover(procurementOfficer);


        history.setAction(
                "PROCUREMNT_APPROVED"
        );


        history.setRemarks(
                "Approved by Procurement Officer"
        );



        approvalHistoryRepository.save(history);



        return "Request Approved Successfully";

    }





    // =====================================
    // PROCUREMENT REJECT
    // =====================================

    public String rejectRequest(Long requestId) {


        PurchaseRequisition requisition =
                requisitionRepository.findById(requestId)

                .orElseThrow(() ->
                new RuntimeException("Request Not Found"));



        requisition.setStatus(
                "PROCUREMENT_REJECTED"
        );


        requisitionRepository.save(requisition);



        Employee procurementOfficer =
                employeeRepository.findByRole(
                        "PROCUREMENT_OFFICER"
                )
                .orElseThrow(() ->
                new RuntimeException(
                "Procurement Officer Not Found"));



        ApprovalHistory history =
                new ApprovalHistory();



        history.setRequisition(requisition);


        history.setApprover(procurementOfficer);


        history.setAction(
                "PROCUREMENT REJECTED"
        );


        history.setRemarks(
                "Rejected by Procurement Officer"
        );



        approvalHistoryRepository.save(history);



        return "Request Rejected Successfully";

    }

}