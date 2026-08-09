package Procurement.Master.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import Procurement.Master.Entity.Inventory;


public interface InventoryRepository 
extends JpaRepository<Inventory,Long>{

}
