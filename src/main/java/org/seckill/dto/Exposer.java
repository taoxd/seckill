package org.seckill.dto;

import lombok.Data;

/**
 * @Description: 暴露秒杀地址DTO
 * @Author: taoxudong
 * @CreateDate: 2020/1/29 21:56
 * @Version: 1.0
 */
@Data
public class Exposer {

    //是否开启秒杀
    private boolean exposed;

    //一种加密措施
    private String md5;

    //id
    private Long seckillId;

    //系统当前时间(毫秒)
    private Long now;

    //开启时间
    private Long start;

    //结束时间
    private Long end;

    public Exposer(boolean exposed, String md5, Long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, Long seckillId, Long now, Long start, Long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, Long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
