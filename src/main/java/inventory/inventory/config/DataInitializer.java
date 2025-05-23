package inventory.inventory.config;

import inventory.inventory.model.Role;
import inventory.inventory.model.User;
import inventory.inventory.repository.RoleRepository;
import inventory.inventory.repository.UserRepository;
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
    }
}