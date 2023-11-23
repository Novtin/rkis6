package javaClasses.service;

import javaClasses.entity.Role;
import javaClasses.entity.User;
import javaClasses.repository.RoleRepository;
import javaClasses.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findUserByLogin(user.getLogin());
        if (userFromDB != null) {
            return false;
        }
        Set<Role> roleSet = new HashSet<>();
        Role userRole = user.getLogin().equals("admin") ? roleRepository.getRoleByName("ROLE_ADMIN") :
                roleRepository.getRoleByName("ROLE_USER");
        roleSet.add(userRole);
        user.setRoles(roleSet);
        user.setPasswordEncoded(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean loginUser(String login, String password) {
        User user = userRepository.findUserByLogin(login);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

}
