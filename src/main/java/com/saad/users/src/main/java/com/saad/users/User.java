package com.saad.users;

import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name="app_users", uniqueConstraints=@UniqueConstraint(columnNames="username"))
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    @Column(nullable=false, length=50) String username;
    @Column(nullable=false, length=120) String fullName;
    @Column(nullable=false, length=100) String passwordHash;
    @Column(nullable=false, updatable=false) Instant createdAt;
    protected User() {}
    @PrePersist void onCreate() { createdAt=Instant.now(); }
}
