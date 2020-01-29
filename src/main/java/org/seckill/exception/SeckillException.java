package org.seckill.exception;
/**
* @Description:    秒杀相关业务异常
* @Author:         taoxudong
* @CreateDate:     2020/1/29 22:36
* @Version:        1.0
*/
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
