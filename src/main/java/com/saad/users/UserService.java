package com.saad.users;

import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static com.saad.users.UserDtos.*;
@Service @Transactional
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public UserService(UserRepository repository) { this.repository=repository; }
    public UserView create(CreateUser input) {
        if(input.password().getBytes(java.nio.charset.StandardCharsets.UTF_8).length>72) throw new InvalidInputException("Password must be at most 72 UTF-8 bytes.");
        String username=input.username().toLowerCase(Locale.ROOT);
        if(repository.existsByUsername(username)) throw new ConflictException();
        User u=new User(); u.username=username; u.fullName=input.fullName().trim();
        u.passwordHash=encoder.encode(input.password());
        return view(repository.saveAndFlush(u));
    }
    @Transactional(readOnly=true) public List<UserView> list() { return repository.findAll().stream().map(this::view).toList(); }
    @Transactional(readOnly=true) public UserView get(long id) { return view(find(id)); }
    public UserView update(long id, UpdateUser input) { User u=find(id);u.fullName=input.fullName().trim();return view(repository.save(u)); }
    public void delete(long id) { repository.delete(find(id)); }
    private User find(long id) { return repository.findById(id).orElseThrow(()->new NotFoundException("User not found.")); }
    private UserView view(User u) { return new UserView(u.id,u.username,u.fullName,u.createdAt); }
      }
