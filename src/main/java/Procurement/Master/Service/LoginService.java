package Procurement.Master.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Procurement.Master.Dto.LoginRequest;
import Procurement.Master.Dto.LoginResponse;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Entity.Supplier;
import Procurement.Master.Repository.EmployeeRepository;
import Procurement.Master.Repository.SupplierRepository;
import Procurement.Master.Security.JwtService;


@Service
public class LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SupplierRepository supplierRepository;

    public LoginResponse login(LoginRequest request) {
    	if ("SUPPLIER".equalsIgnoreCase(request.getRole())) {

    	    Supplier supplier = supplierRepository.findByEmail(request.getEmail())
    	            .orElseThrow(() ->
    	                    new RuntimeException("Invalid Email"));

    	    if (!passwordEncoder.matches(request.getPassword(), supplier.getPassword())) {
    	        throw new RuntimeException("Invalid Password");
    	    }

    	    LoginResponse response = new LoginResponse();

    	    response.setMessage("Login Successful");

    	    // ADD THESE TWO LINES
    	    response.setSupplierId(supplier.getSupplierId());
    	    response.setSupplierName(supplier.getSupplierName());

    	    response.setEmail(supplier.getEmail());
    	    response.setRole(supplier.getRole());

    	    String token = jwtService.generateToken(
    	            supplier.getSupplierId(),
    	            supplier.getEmail(),
    	            supplier.getRole());

    	    response.setToken(token);
         System.out.println("Hello " + supplier.getSupplierId());
         System.out.println(supplier.getSupplierName());
    	    return response;
    	}
        // Check Email
        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        // Check Password
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        // Check Role
        if (!employee.getRole().equalsIgnoreCase(request.getRole())) {
            throw new RuntimeException("Invalid Role");
        }

        // Return Response
        LoginResponse response = new LoginResponse();

        response.setMessage("Login Successful");
        response.setEmployeeId(employee.getEmployeeId());
        response.setEmployeeCompanyId(employee.getEmployeeCompanyId());
        response.setEmployeeName(employee.getEmployeeName());
        response.setEmail(employee.getEmail());
       response.setRole(employee.getRole());
        String token =
        		jwtService.generateToken(
        		employee.getEmployeeId(),
        		employee.getEmail(),
        		employee.getRole()
        		);


        		response.setToken(token);
        return response;
    }
}
