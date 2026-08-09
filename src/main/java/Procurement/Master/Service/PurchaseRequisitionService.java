package Procurement.Master.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Procurement.Master.Dto.PurchaseRequisitionDto;
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
    private ApprovalHistoryRepository historyRepository;


    // =====================================================
    // CREATE PURCHASE REQUISITION
    // =====================================================

    @Transactional
    public PurchaseRequisitionDto createRequisition(
            PurchaseRequisitionDto dto) {

        // -----------------------------------------------
        // 1. Find Employee
        // -----------------------------------------------

        Employee employee = employeeRepository
                .findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found"));


        // -----------------------------------------------
        // 2. Check duplicate pending request
        // -----------------------------------------------

        Optional<PurchaseRequisition> existing =
                requisitionRepository
                        .findTopByEmployeeAndItemNameOrderByCreatedDateDesc(
                                employee,
                                dto.getItemName());

        if (existing.isPresent()) {

            PurchaseRequisition req = existing.get();

            if ("PENDING".equalsIgnoreCase(req.getStatus())
                    || "UNDER_REVIEW".equalsIgnoreCase(req.getStatus())) {

                throw new RuntimeException(
                        "A request for this item is already in progress.");
            }
        }


        // -----------------------------------------------
        // 3. Create Purchase Requisition
        // -----------------------------------------------

        PurchaseRequisition entity =
                new PurchaseRequisition();

        entity.setEmployee(employee);
        entity.setItemName(dto.getItemName());
        entity.setQuantity(dto.getQuantity());
        entity.setEstimatedCost(dto.getEstimatedCost());
        entity.setJustification(dto.getJustification());
        entity.setStatus("PENDING");
        entity.setCreatedDate(LocalDateTime.now());


        // -----------------------------------------------
        // 4. Save Purchase Requisition
        // -----------------------------------------------

        entity = requisitionRepository.save(entity);


        // -----------------------------------------------
        // 5. Find Manager
        // -----------------------------------------------

        Employee manager = employeeRepository
                .findByRole("MANAGER")
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Manager not found"));


        // -----------------------------------------------
        // 6. Create Workflow
        // -----------------------------------------------

        Workflow workflow = new Workflow();

        workflow.setRequisition(entity);
        workflow.setCurrentLevel(1);
        workflow.setCurrentApprover(manager);
        workflow.setWorkflowStatus("PENDING");

        workflowRepository.save(workflow);


        // -----------------------------------------------
        // 7. CREATE APPROVAL HISTORY
        // -----------------------------------------------

        ApprovalHistory history =
                new ApprovalHistory();

        history.setRequisition(entity);

        // Employee who raised the request
        history.setApprover(employee);

        history.setAction("REQUEST_CREATED");

        history.setRemarks(
                "Purchase request raised by employee");

        history.setActionDate(
                LocalDateTime.now());

        historyRepository.save(history);


        // -----------------------------------------------
        // 8. Return response
        // -----------------------------------------------

        return convertToDto(entity);
    }


    // =====================================================
    // GET ALL REQUISITIONS
    // =====================================================

    public List<PurchaseRequisitionDto> getAllRequisitions() {

        return requisitionRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // =====================================================
    // GET REQUISITION BY ID
    // =====================================================

    public PurchaseRequisitionDto getRequisitionById(Long id) {

        PurchaseRequisition entity =
                requisitionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Purchase Requisition Not Found"));

        return convertToDto(entity);
    }


    // =====================================================
    // GET REQUISITIONS BY EMPLOYEE
    // =====================================================

    public List<PurchaseRequisitionDto> getRequisitionsByEmployee(
            Long employeeId) {

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Employee Not Found"));

        return requisitionRepository
                .findByEmployee(employee)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // =====================================================
    // UPDATE REQUISITION
    // =====================================================

    public PurchaseRequisitionDto updateRequisition(
            Long id,
            PurchaseRequisitionDto dto) {

        PurchaseRequisition entity =
                requisitionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Purchase Requisition Not Found"));

        entity.setItemName(dto.getItemName());
        entity.setQuantity(dto.getQuantity());
        entity.setEstimatedCost(dto.getEstimatedCost());
        entity.setJustification(dto.getJustification());

        entity =
                requisitionRepository.save(entity);

        return convertToDto(entity);
    }


    // =====================================================
    // DELETE REQUISITION
    // =====================================================

    @Transactional
    public void deleteRequisition(Long id) {

        PurchaseRequisition entity =
                requisitionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Purchase Requisition Not Found"));


        // Delete approval history first
        historyRepository.deleteByRequisition(entity);


        // Delete workflow
        workflowRepository.findByRequisition(entity)
                .ifPresent(workflowRepository::delete);


        // Delete requisition
        requisitionRepository.delete(entity);
    }


    // =====================================================
    // CONVERT ENTITY TO DTO
    // =====================================================

    private PurchaseRequisitionDto convertToDto(
            PurchaseRequisition entity) {

        PurchaseRequisitionDto dto =
                new PurchaseRequisitionDto();

        dto.setRequestId(
                entity.getRequestId());

        dto.setEmployeeId(
                entity.getEmployee()
                        .getEmployeeId());

        dto.setItemName(
                entity.getItemName());

        dto.setQuantity(
                entity.getQuantity());

        dto.setEstimatedCost(
                entity.getEstimatedCost());

        dto.setJustification(
                entity.getJustification());

        dto.setStatus(
                entity.getStatus());

        dto.setCreatedDate(
                entity.getCreatedDate());

        return dto;
    }
}