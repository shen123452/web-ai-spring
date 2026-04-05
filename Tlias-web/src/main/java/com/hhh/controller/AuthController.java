package com.hhh.controller;

import com.hhh.pojo.Emp;
import com.hhh.pojo.LoginRequest;
import com.hhh.pojo.Result;
import com.hhh.service.EmpService;
import com.hhh.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final EmpService empService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest == null ? null : loginRequest.getUsername();
        log.info("Employee login, username: {}", username);
        Emp emp = empService.login(loginRequest);
        if (emp == null) {
            return Result.error("用户名或密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", emp.getId());
        claims.put("username", emp.getUsername());
        claims.put("name", emp.getName());

        Map<String, Object> loginUser = new HashMap<>();
        loginUser.put("id", emp.getId());
        loginUser.put("username", emp.getUsername());
        loginUser.put("name", emp.getName());
        loginUser.put("token", jwtUtils.generateToken(claims));
        return Result.success(loginUser);
    }
}
