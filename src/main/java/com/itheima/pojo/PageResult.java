package com.itheima.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;

    // 关键修复：构造方法里，rows 永远不为 null
    public PageResult(Long total, List<T> rows) {
        this.total = total;
        // 如果 rows 是 null，自动给空集合
        this.rows = rows == null ? Collections.emptyList() : rows;
    }
}
