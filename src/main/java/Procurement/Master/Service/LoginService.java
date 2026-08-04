package Procurement.Master.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Procurement.Master.Dto.LoginRequest;
import Procurement.Master.Dto.LoginResponse;
import Procurement.Master.Entity.Employee;
import Procurement.Master.Repository.EmployeeRepository;


@Service
public class LoginService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

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

        return response;
    }
}
