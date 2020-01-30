package org.seckill.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {

    /**
     * 获取时间时间戳
     *
     * @param localDateTime
     * @return
     */
    public static Long getTimeMillis(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }
}
