package inventory.inventory.config;

import inventory.inventory.model.Role;
import inventory.inventory.model.User;
import inventory.inventory.model.Supplier;
import inventory.inventory.repository.RoleRepository;
import inventory.inventory.repository.UserRepository;
import inventory.inventory.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create roles if they don't exist
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setName("ADMIN");
            role.setDescription("System Administrator with full access");
            return roleRepository.save(role);
        });
        
        Role managerRole = roleRepository.findByName("MANAGER").orElseGet(() -> {
            Role role = new Role();
            role.setName("MANAGER");
            role.setDescription("Manager with limited access");
            return roleRepository.save(role);
        });
        
        // Create default admin user if it doesn't exist
        userRepository.findByEmail("admin@inventory.com").orElseGet(() -> {
            User adminUser = new User();
            adminUser.setEmail("admin@inventory.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setFullName("System Administrator");
            adminUser.setRole(adminRole);
            adminUser.setStatus("ACTIVE");
            return userRepository.save(adminUser);
        });
        
        // Create default manager user if it doesn't exist
        userRepository.findByEmail("manager@inventory.com").orElseGet(() -> {
            User managerUser = new User();
            managerUser.setEmail("manager@inventory.com");
            managerUser.setPassword(passwordEncoder.encode("manager123"));
            managerUser.setFullName("Inventory Manager");
            managerUser.setRole(managerRole);
            managerUser.setStatus("ACTIVE");
            return userRepository.save(managerUser);
        });

        // Create some test suppliers if none exist
        if (supplierRepository.count() == 0) {
            Supplier supplier1 = new Supplier();
            supplier1.setName("Tech Supplies Inc.");
            supplier1.setAddress("123 Tech Street, Silicon Valley, CA 94000");
            supplier1.setCompanyInfo("Leading technology supplier since 2000");
            supplier1.setEmail("contact@techsupplies.com");
            supplier1.setPhone("4081234567");
            supplierRepository.save(supplier1);

            Supplier supplier2 = new Supplier();
            supplier2.setName("Office Solutions Ltd.");
            supplier2.setAddress("456 Office Ave, Business District, NY 10001");
            supplier2.setCompanyInfo("Complete office supply solutions");
            supplier2.setEmail("info@officesolutions.com");
            supplier2.setPhone("2125555678");
            supplierRepository.save(supplier2);
        }
    }
}