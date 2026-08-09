package Procurement.Master.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Procurement.Master.Dto.SupplierRequest;
import Procurement.Master.Entity.Supplier;
import Procurement.Master.Repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // ==========================================
    // GET VERIFIED / ACTIVE SUPPLIERS
    // ==========================================

    public List<Supplier> getVerifiedSuppliers() {

        return supplierRepository.findByStatus("ACTIVE");
    }


    // ==========================================
    // GET SUPPLIER BY ID
    // ==========================================

    public Supplier getSupplierById(Long id) {

        return supplierRepository
                .findById(id)
                .orElse(null);
    }


    // ==========================================
    // REGISTER SUPPLIER
    // ==========================================

    public Supplier saveSupplier(SupplierRequest request) {
    	  if (supplierRepository.existsByGstNumber(
    	            request.getGstNumber())) {

    	        throw new RuntimeException(
    	                "GST Number already exists"
    	        );
    	    }
    	    if (supplierRepository.existsByEmail(
                    request.getEmail())) {

                throw new RuntimeException(
                        "Email already exists"
                );
            }
    	  

            // ==========================================
            // DUPLICATE PAN
            // ==========================================

            if (supplierRepository.existsByPanNumber(
                    request.getPanNumber())) {

                throw new RuntimeException(
                        "PAN Number already exists"
                );
            }


            // ==========================================
            // DUPLICATE PHONE
            // ==========================================

            if (supplierRepository.existsByPhone(
                    request.getPhone())) {

                throw new RuntimeException(
                        "Phone number already exists"
                );
            }

        Supplier supplier = new Supplier();

        supplier.setSupplierName(request.getSupplierName());

        supplier.setEmail(request.getEmail());

        supplier.setPhone(request.getPhone());

        supplier.setCity(request.getCity());

        supplier.setGstNumber(request.getGstNumber());

        supplier.setPanNumber(request.getPanNumber());

        // Password encryption
        supplier.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        // Automatically set role
        supplier.setRole("SUPPLIER");

        // New supplier waits for verification
        supplier.setStatus("PENDING");

        return supplierRepository.save(supplier);
    }


    // ==========================================
    // APPROVE SUPPLIER
    // ==========================================

    public Supplier approveSupplier(Long id) {

        Supplier supplier = supplierRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found"
                        )
                );

        supplier.setStatus("ACTIVE");

        return supplierRepository.save(supplier);
    }


    // ==========================================
    // REJECT SUPPLIER
    // ==========================================

    public Supplier rejectSupplier(Long id) {

        Supplier supplier = supplierRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier not found"
                        )
                );

        supplier.setStatus("REJECTED");

        return supplierRepository.save(supplier);
    }
}