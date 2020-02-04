package org.seckill.dao.cache;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class RedisDao {

    @Autowired
    private StringRedisTemplate strRedis;

    public Seckill getSeckill(Long seckillId) {
        //redis操作逻辑
        String key = "seckill:" + seckillId;
        //并没有实现内部序列化操作
        //get -> byte[] -> 反序列化 -> Object(Seckill)
        String str = strRedis.opsForValue().get(key);
        if (!StringUtils.isEmpty(str)) {
            return JSON.parseObject(str, Seckill.class);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {
        String key = "seckill:" + seckill.getSeckillId();
        //超时缓存
        int timeout = 60 * 60;//一小时
        strRedis.opsForValue().set(key, JSON.toJSONString(seckill), timeout);
        return null;
    }

}
