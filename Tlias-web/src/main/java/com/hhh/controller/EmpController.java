package com.hhh.controller;

import com.hhh.pojo.Emp;
import com.hhh.pojo.EmpQueryParam;
import com.hhh.pojo.PageResult;
import com.hhh.pojo.Result;
import com.hhh.service.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emps")
public class EmpController {
    private static final Logger log = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private EmpService empService;

//   @GetMapping("/emps")
//    public Result<PageResult<Emp>> page(
//            @RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer pageSize,
//            String name, Integer gender,
//            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
//    ) {
//        log.info("分页查询员工: {}, {}, {}, {}, {}, {}", page, pageSize, name, gender, begin, end);
//        return Result.success(empService.page(page, pageSize, name, gender, begin, end));
//    }
    @GetMapping("/emps")
    public Result<PageResult<Emp>> page(EmpQueryParam empQueryParam) {
        log.info("分页查询员工: {}",empQueryParam);
        return Result.success(empService.page(empQueryParam));
    }

    /*

    新增员工
     */

    @PostMapping("/emps")
    public Result add(@RequestBody Emp emp)throws Exception {
        log.info("新增员工: {}", emp);
        empService.add(emp);
        return Result.success();
    }

    /*
    更新员工
     */

    @PutMapping("/emps/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Emp emp) {
        emp.setId(id);
        log.info("更新员工: {}", emp);
        empService.update(emp);
        return Result.success();
    }

    @DeleteMapping("/emps/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除员工：{}", id);
        empService.delete(id);
        return Result.success();
    }
}
