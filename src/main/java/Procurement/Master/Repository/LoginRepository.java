
	package Procurement.Master.Repository;

	import java.util.Optional;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	import Procurement.Master.Entity.Employee;

	@Repository
	public interface LoginRepository extends JpaRepository<Employee, Long> {

	    // Login
	    Optional<Employee> findByEmail(String email);

	    // Employee Company ID
	    Optional<Employee> findByEmployeeCompanyId(String employeeCompanyId);

	    // Find by Role
	    Optional<Employee> findByRole(String role);

	    // Validation
	    boolean existsByEmail(String email);

	    boolean existsByEmployeeCompanyId(String employeeCompanyId);
	}

