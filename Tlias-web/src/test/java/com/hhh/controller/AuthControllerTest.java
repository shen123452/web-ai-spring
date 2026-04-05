package com.hhh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhh.exception.GlobalExceptionHandler;
import com.hhh.interceptor.JwtTokenInterceptor;
import com.hhh.pojo.Emp;
import com.hhh.pojo.LoginRequest;
import com.hhh.service.EmpService;
import com.hhh.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(GlobalExceptionHandler.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpService empService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private JwtTokenInterceptor jwtTokenInterceptor;

    @Test
    void loginShouldReturnTokenWhenCredentialsAreValid() throws Exception {
        Emp emp = new Emp();
        emp.setId(1);
        emp.setUsername("admin");
        emp.setName("管理员");
        when(empService.login(any(LoginRequest.class))).thenReturn(emp);
        when(jwtUtils.generateToken(anyMap())).thenReturn("mock-token");

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.token").value("mock-token"))
                .andExpect(jsonPath("$.data.name").value("管理员"))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    void loginShouldReturnBusinessErrorWhenCredentialsAreInvalid() throws Exception {
        when(empService.login(any(LoginRequest.class))).thenReturn(null);

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("bad-password");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("用户名或密码错误"));
    }
}
