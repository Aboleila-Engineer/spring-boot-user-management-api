package com.saad.users;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.saad.users.UserDtos.*;
@RestController @RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service) { this.service=service; }
    @PostMapping ResponseEntity<UserView> create(@Valid @RequestBody CreateUser input) { UserView u=service.create(input);return ResponseEntity.created(URI.create("/api/users/"+u.id())).body(u); }
    @GetMapping List<UserView> list() { return service.list(); }
    @GetMapping("/{id}") UserView get(@PathVariable long id) { return service.get(id); }
    @PutMapping("/{id}") UserView update(@PathVariable long id,@Valid @RequestBody UpdateUser input) { return service.update(id,input); }
    @DeleteMapping("/{id}") ResponseEntity<Void> delete(@PathVariable long id) { service.delete(id);return ResponseEntity.noContent().build(); }
}
