package inventory.inventory.controller;

import inventory.inventory.model.Supplier;
import inventory.inventory.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class SupplierController {
    
    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
    
    @Autowired
    private SupplierService supplierService;
    
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        logger.debug("Creating new supplier: {}", supplier.getName());
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        return ResponseEntity.ok(createdSupplier);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        logger.debug("Updating supplier with id: {}", id);
        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(updatedSupplier);
    }
    
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        logger.debug("Fetching all suppliers");
        try {
            List<Supplier> suppliers = supplierService.getAllSuppliers();
            logger.debug("Found {} suppliers", suppliers.size());
            return ResponseEntity.ok(suppliers);
        } catch (Exception e) {
            logger.error("Error fetching suppliers", e);
            throw e;
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        logger.debug("Fetching supplier with id: {}", id);
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        logger.debug("Deleting supplier with id: {}", id);
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}