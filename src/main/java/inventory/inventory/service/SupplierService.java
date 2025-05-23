package inventory.inventory.service;

import inventory.inventory.model.Supplier;
import inventory.inventory.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
    
    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        
        supplier.setName(supplierDetails.getName());
        supplier.setAddress(supplierDetails.getAddress());
        supplier.setCompanyInfo(supplierDetails.getCompanyInfo());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setPhone(supplierDetails.getPhone());
        
        return supplierRepository.save(supplier);
    }
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }
    
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}