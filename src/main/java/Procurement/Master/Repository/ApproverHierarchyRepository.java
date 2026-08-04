package Procurement.Master.Repository;




import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.ApprovalHierarchy;

import java.util.Optional;

@Repository
public interface ApproverHierarchyRepository extends JpaRepository<ApprovalHierarchy, Long> {

	   Optional<ApprovalHierarchy> findByLevel(int level);

	    Optional<ApprovalHierarchy> findByRole(String role);

}