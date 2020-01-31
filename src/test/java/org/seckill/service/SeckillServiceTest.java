package org.seckill.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.App;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)//你的启动类
@Slf4j
public class SeckillServiceTest {
    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() {
        List<Seckill> list = seckillService.getSeckillList();
        log.info("list={}", list);
    }

    @Test
    public void testGetById() {
        Long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        log.info("seckill={}", seckill);
    }

    //集成测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogic() {
        Long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            log.info("exposer={}", exposer);
            Long phone = 15689745632L;
            String md5 = exposer.getMd5();

            try {
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                log.info("execution={}", execution);
            } catch (RepeatKillException e) {
                log.error(e.getMessage());
            } catch (SeckillCloseException e) {
                log.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            log.warn("exposer={}", exposer);
        }
    }

    @Test
    public void testExecuteSeckill() {
        Long id = 1000L;
        Long phone = 13952010252L;
        String md5 = "084c13c35971fb20da16be8849ac86c1";

        try {
            SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
            log.info("execution={}", execution);
        } catch (RepeatKillException e) {
            log.error(e.getMessage());
        } catch (SeckillCloseException e) {
            log.error(e.getMessage());
        }
    }

}