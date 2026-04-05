package com.hhh.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hhh.mapper.ClazzMapper;
import com.hhh.pojo.Clazz;
import com.hhh.pojo.ClazzQueryParam;
import com.hhh.pojo.EmpLog;
import com.hhh.pojo.PageResult;
import com.hhh.service.ClazzService;
import com.hhh.service.EmpLogService;
import com.hhh.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClazzServiceImpl implements ClazzService {
    private final ClazzMapper clazzMapper;
    private final EmpLogService empLogService;

    @Override
    public PageResult<Clazz> page(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        Page<Clazz> clazzPage = (Page<Clazz>) clazzMapper.list(clazzQueryParam);
        return new PageResult<>(clazzPage.getTotal(), clazzPage.getResult());
    }

    @Override
    public List<Clazz> list() {
        return clazzMapper.listAll();
    }

    @Override
    public Clazz getById(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Clazz clazz) {
        validateDateRange(clazz.getBeginDate(), clazz.getEndDate());
        LocalDateTime now = LocalDateTime.now();
        clazz.setCreateTime(now);
        clazz.setUpdateTime(now);
        clazzMapper.insert(clazz);
        writeLog(now, "ADD_CLAZZ", clazz.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Clazz clazz) {
        Clazz dbClazz = clazzMapper.getById(clazz.getId());
        if (dbClazz == null) {
            throw new IllegalArgumentException("班级不存在");
        }
        validateDateRange(clazz.getBeginDate(), clazz.getEndDate());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
        writeLog(LocalDateTime.now(), "UPDATE_CLAZZ", String.valueOf(clazz.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Clazz clazz = clazzMapper.getById(id);
        if (clazz == null) {
            throw new IllegalArgumentException("班级不存在");
        }
        Integer studentCount = clazzMapper.countStudentByClazzId(id);
        if (studentCount != null && studentCount > 0) {
            throw new IllegalStateException("当前班级下存在学员，无法删除");
        }
        clazzMapper.deleteById(id);
        writeLog(LocalDateTime.now(), "DELETE_CLAZZ", String.valueOf(id));
    }

    private void validateDateRange(LocalDate beginDate, LocalDate endDate) {
        if (beginDate != null && endDate != null && endDate.isBefore(beginDate)) {
            throw new IllegalArgumentException("结课时间不能早于开课时间");
        }
    }

    private void writeLog(LocalDateTime time, String action, String detail) {
        String info = "operator=" + UserContext.getUsernameOrDefault("system")
                + ";action=" + action
                + ";detail=" + detail;
        empLogService.insertLog(new EmpLog(null, time, info));
    }
}
