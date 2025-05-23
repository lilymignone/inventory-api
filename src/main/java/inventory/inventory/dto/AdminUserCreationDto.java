package inventory.inventory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserCreationDto {
    private String email;
    private String fullName;
    private String password;
    private String roleName;
    private String status;
} 