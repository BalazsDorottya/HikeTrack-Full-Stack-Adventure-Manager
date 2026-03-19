package edu.hike.idde.myproject.spring.controller;

import edu.hike.idde.myproject.spring.controller.dto.LoginDTO;
import edu.hike.idde.myproject.spring.controller.dto.RegisterDTO;
import edu.hike.idde.myproject.spring.controller.jwt.JwtUtil;
import edu.hike.idde.myproject.spring.model.User;
import edu.hike.idde.myproject.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody RegisterDTO dto) {
        User user = userService.register(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword());
        String token = jwtUtil.generateToken(user);
        return Map.of("token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO dto) {
        User user = userService.authenticate(dto.getEmail(), dto.getPassword());
        String token = jwtUtil.generateToken(user);
        return Map.of("token", token);
    }
}
