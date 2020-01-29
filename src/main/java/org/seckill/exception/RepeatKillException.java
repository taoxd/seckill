package org.seckill.exception;

/**
 * @Description: 重复秒杀异常(spring只回滚运行期异常)
 * @Author: taoxudong
 * @CreateDate: 2020/1/29 22:12
 * @Version: 1.0
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
