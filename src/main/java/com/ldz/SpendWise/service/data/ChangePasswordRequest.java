package com.ldz.SpendWise.service.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "Old password cannot be empty")
    @Size(min = 8)
    private String oldPassword;
    @NotBlank(message = "New password cannot be empty")
    @Size(min = 8)
    private String newPassword;
}
