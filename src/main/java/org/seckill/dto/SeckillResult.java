package org.seckill.dto;

import lombok.Data;

/**
 * @Description: 所有ajax请求返回类型，封装json结果
 * @Author: taoxudong
 * @CreateDate: 2020/1/31 15:29
 * @Version: 1.0
 */
@Data
public class SeckillResult<T> {

    private Boolean success;

    private T data;

    private String error;

    public SeckillResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
