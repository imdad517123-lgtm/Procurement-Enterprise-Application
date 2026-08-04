	package Procurement.Master.Service;
	
	
	import java.util.List;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
	
	import Procurement.Master.Dto.EmployeeRequest;
	import Procurement.Master.Entity.Employee;
	import Procurement.Master.Exception.ResourceNotFoundException;
	import Procurement.Master.Repository.EmployeeRepository;
	
	@Service
	public class EmployeeService {
		@Autowired
		private PasswordEncoder passwordEncoder;
	    @Autowired
	    private EmployeeRepository employeeRepository;
	
	    // Create Employee
//	    public String createEmployee(EmployeeRequest request) {
//	
//	        if (employeeRepository.existsByEmployeeCompanyId(request.getEmployeeCompanyId())) {
//	            throw new RuntimeException("Employee Company ID already exists.");
//	        }
//	
//	        if (employeeRepository.existsByEmail(request.getEmail())) {
//	            throw new RuntimeException("Email already exists.");
//	        }
//	
//	        Employee employee = new Employee();
//	
//	        employee.setEmployeeCompanyId(request.getEmployeeCompanyId());
//	        employee.setEmployeeName(request.getEmployeeName());
//	        employee.setEmail(request.getEmail());
//	        employee.setDepartment(request.getDepartment());
//	        employee.setRole(request.getRole());
//	
//	        employeeRepository.save(employee);
//	
//	        return "Employee created successfully. Employee Company ID: "
//	                + employee.getEmployeeCompanyId();
//	    }
	    public String createEmployee(EmployeeRequest request) {

	        if (employeeRepository.existsByEmployeeCompanyId(request.getEmployeeCompanyId())) {
	            throw new RuntimeException("Employee Company ID already exists.");
	        }

	        if (employeeRepository.existsByEmail(request.getEmail())) {
	            throw new RuntimeException("Email already exists.");
	        }

	        Employee employee = new Employee();

	        employee.setEmployeeCompanyId(request.getEmployeeCompanyId());
	        employee.setEmployeeName(request.getEmployeeName());
	        employee.setEmail(request.getEmail());

	        // Encrypt password before saving
	        employee.setPassword(passwordEncoder.encode(request.getPassword()));

	        employee.setDepartment(request.getDepartment());
	        employee.setRole(request.getRole());

	        employeeRepository.save(employee);

	        return "Employee created successfully. Employee Company ID: "
	                + employee.getEmployeeCompanyId();
	    }
	
	    // Get All Employees
	    public List<Employee> getAllEmployees() {
	
	        return employeeRepository.findAll();
	    }
	
	    // Get Employee By Id
	    public Employee getEmployeeById(Long id) {
	
	        return employeeRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Employee Not Found"));
	    }
	
	    // Update Employee
	    public Employee updateEmployee(Long id,
	                                   EmployeeRequest request) {
	
	        Employee employee = getEmployeeById(id);
	
	        employee.setEmployeeName(request.getEmployeeName());
	        employee.setEmail(request.getEmail());
	        employee.setDepartment(request.getDepartment());
	        employee.setRole(request.getRole());
	
	        return employeeRepository.save(employee);
	    }
	
	    // Delete Employee
	    public void deleteEmployee(Long id) {
	
	        Employee employee = getEmployeeById(id);
	
	        employeeRepository.delete(employee);
	    }
	
	}
