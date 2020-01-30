package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.App;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)//你的启动类
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        seckills.forEach(System.out::println);
    }

    @Test
    public void testReduceNumber() {
        int i = seckillDao.reduceNumber(1000L, LocalDateTime.now());
        System.out.println(i);
    }

}