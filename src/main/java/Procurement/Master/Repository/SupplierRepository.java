package Procurement.Master.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Procurement.Master.Entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	Optional<Supplier> findByEmail(String email);
    List<Supplier> findByStatus(String status);
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByGstNumber(String gstNumber);

    boolean existsByPanNumber(String panNumber);
}
