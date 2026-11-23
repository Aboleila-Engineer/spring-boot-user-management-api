package com.saad.users;

import org.springframework.data.jpa.repository.JpaRepository;
interface UserRepository extends JpaRepository<User, Long> { boolean existsByUsername(String username); }
