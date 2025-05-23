package inventory.inventory.service;

import inventory.inventory.dto.UserRegistrationDto;
import inventory.inventory.dto.AdminUserCreationDto;
import inventory.inventory.dto.UserProfileUpdateDto;
import inventory.inventory.model.Role;
import inventory.inventory.model.User;
import inventory.inventory.repository.RoleRepository;
import inventory.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        Role role = roleRepository.findByName(registrationDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFullName(registrationDto.getFullName());
        user.setRole(role);
        user.setStatus("ACTIVE");
        
        return userRepository.save(user);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User createUserByAdmin(AdminUserCreationDto adminDto) {
        if (userRepository.existsByEmail(adminDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        Role role = roleRepository.findByName(adminDto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        
        User user = new User();
        user.setEmail(adminDto.getEmail());
        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        user.setFullName(adminDto.getFullName());
        user.setRole(role);
        user.setStatus(adminDto.getStatus());
        
        return userRepository.save(user);
    }

    public User updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
            throw new RuntimeException("Invalid status. Must be either ACTIVE or INACTIVE");
        }
        
        user.setStatus(status);
        return userRepository.save(user);
    }

    public User updateUserProfile(Long userId, UserProfileUpdateDto profileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify current password if provided
        if (profileDto.getCurrentPassword() != null && !profileDto.getCurrentPassword().isEmpty()) {
            if (!passwordEncoder.matches(profileDto.getCurrentPassword(), user.getPassword())) {
                throw new BadCredentialsException("Current password is incorrect");
            }
            
            // Update password if new password is provided
            if (profileDto.getNewPassword() != null && !profileDto.getNewPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(profileDto.getNewPassword()));
            }
        }

        // Update email if provided and different
        if (profileDto.getEmail() != null && !profileDto.getEmail().isEmpty() 
            && !profileDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(profileDto.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(profileDto.getEmail());
        }

        // Update full name if provided
        if (profileDto.getFullName() != null && !profileDto.getFullName().isEmpty()) {
            user.setFullName(profileDto.getFullName());
        }

        return userRepository.save(user);
    }
}