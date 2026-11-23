package com.saad.users;

import jakarta.validation.constraints.*;
import java.time.Instant;
public final class UserDtos {
    private UserDtos() {}
    public record CreateUser(@NotBlank @Pattern(regexp="[a-zA-Z0-9._-]{3,50}") String username,
                             @NotBlank @Size(max=120) String fullName,
                             @NotBlank @Size(min=8,max=72) String password) {}
    public record UpdateUser(@NotBlank @Size(max=120) String fullName) {}
    public record UserView(Long id, String username, String fullName, Instant createdAt) {}
}
