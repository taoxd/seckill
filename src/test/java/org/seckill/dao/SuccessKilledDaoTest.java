package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.App;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)//你的启动类
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() {
        /*
        第一次 : insertCount = 1
        第二次 : insertCount = 0
         */
        long id = 1000L;
        long phone = 18914490250L;
        int i = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount = " + i);
    }

    @Test
    public void testQueryByIdWithSeckill() {
        long id = 1000L;
        long phone = 18914490252L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}