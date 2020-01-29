package org.seckill.dto;

import lombok.Data;
import org.seckill.entity.SuccessKilled;

/**
 * @Description: 封装秒杀执行后结果
 * @Author: taoxudong
 * @CreateDate: 2020/1/29 22:06
 * @Version: 1.0
 */
@Data
public class SeckillExecution {

    private Long seckillId;

    //秒杀执行结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(Long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    public SeckillExecution(Long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }
}
