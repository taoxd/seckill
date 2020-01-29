package org.seckill.exception;

/**
 * @Description: 秒杀关闭异常
 * @Author: taoxudong
 * @CreateDate: 2020/1/29 22:34
 * @Version: 1.0
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
