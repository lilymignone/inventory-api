package inventory.inventory.service;

import inventory.inventory.dto.ProductDto;
import inventory.inventory.model.Category;
import inventory.inventory.model.Product;
import inventory.inventory.model.Supplier;
import inventory.inventory.repository.CategoryRepository;
import inventory.inventory.repository.ProductRepository;
import inventory.inventory.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public Product createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setCategory(category);
        product.setSupplier(supplier);
        
        return productRepository.save(product);
    }
    
    public Product updateProduct(UUID id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        if (productDto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(productDto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            product.setSupplier(supplier);
        }
        
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setAvailableQuantity(productDto.getAvailableQuantity());
        product.setUnitPrice(productDto.getUnitPrice());
        
        return productRepository.save(product);
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}