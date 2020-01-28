package org.seckill.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 秒杀成功明细表
 * </p>
 *
 */
@Data
public class SuccessKilled implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 秒杀商品id
     */
      private Long seckillId;

    /**
     * 用户手机号
     */
    private Long userPhone;

    /**
     * 状态标示：-1无效，0：成功 1：已付款
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 多对一
     */
    private Seckill seckill;

}
