package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.App;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)//你的启动类
public class RedisDaoTest {

    private Long id = 1001L;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() {

        Seckill seckill = redisDao.getSeckill(id);
        if (ObjectUtils.isEmpty(seckill)) {
            seckill = seckillDao.queryById(id);
            if (!ObjectUtils.isEmpty(seckill)) {
                redisDao.putSeckill(seckill);
                seckill = redisDao.getSeckill(id);
                System.out.println("redis缓存: " + seckill);
            }

        }
    }

}