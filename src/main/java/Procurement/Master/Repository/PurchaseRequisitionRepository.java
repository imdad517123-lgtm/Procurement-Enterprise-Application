package Procurement.Master.Repository;





import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.PurchaseRequisition;

@Repository
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Long> {

    List<PurchaseRequisition> findByEmployee(Employee employee);

    List<PurchaseRequisition> findByStatus(String status);
    Optional<PurchaseRequisition> findTopByEmployeeAndItemNameOrderByCreatedDateDesc(
            Employee employee,
            String itemName);
}