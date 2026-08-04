package Procurement.Master.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


	 Optional<Employee> findByEmployeeCompanyId(String employeeCompanyId);
	    Optional<Employee> findByEmail(String email);

	    Optional<Employee> findByRole(String role);

	    boolean existsByEmail(String email);
	    boolean existsByEmployeeCompanyId(String employeeCompanyId);
		
	}