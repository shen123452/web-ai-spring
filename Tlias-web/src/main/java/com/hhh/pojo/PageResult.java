package com.hhh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total;//总记录数
    private List<T> rows;//这里的泛型T表示 rows的类型是泛型，T表示类型参数，可以在创建对象时指定具体类型
}
