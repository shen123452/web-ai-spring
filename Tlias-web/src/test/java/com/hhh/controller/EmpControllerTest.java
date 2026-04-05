package com.hhh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhh.exception.GlobalExceptionHandler;
import com.hhh.interceptor.JwtTokenInterceptor;
import com.hhh.pojo.Emp;
import com.hhh.service.EmpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpController.class)
@Import(GlobalExceptionHandler.class)
class EmpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpService empService;

    @MockBean
    private JwtTokenInterceptor jwtTokenInterceptor;

    @BeforeEach
    void setUp() throws Exception {
        when(jwtTokenInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @Test
    void addShouldAcceptExprListPayload() throws Exception {
        String requestBody = """
                {
                  "username": "zhangsan",
                  "name": "张三",
                  "gender": 1,
                  "job": 5,
                  "image": "https://example.com/avatar.png",
                  "entryDate": "2021-08-26",
                  "deptId": 3,
                  "phone": "15539623561",
                  "salary": 21,
                  "exprList": [
                    {
                      "begin": "1986-09-27",
                      "end": "1992-08-08",
                      "company": "Acme",
                      "job": "Engineer"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/emps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

        ArgumentCaptor<Emp> captor = ArgumentCaptor.forClass(Emp.class);
        verify(empService).add(captor.capture());
        assertThat(captor.getValue().getExprList()).hasSize(1);
        assertThat(captor.getValue().getEntryDate()).hasToString("2021-08-26");
    }

    @Test
    void addShouldReturnWrappedErrorWhenServiceThrows() throws Exception {
        doThrow(new RuntimeException("mock failure")).when(empService).add(any(Emp.class));

        mockMvc.perform(post("/emps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Emp())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").value("操作失败，请稍后重试"));
    }
}
