package org.seckill.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.seckill.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆MD5
    private final String slat = "jkdjskdjnnbn56421216%^^&^%$%HJGFVBNB";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(Long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //秒杀商品不存在
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        LocalDateTime startTime = seckill.getStartTime();
        LocalDateTime endTime = seckill.getEndTime();
        //系统当前时间
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            return new Exposer(false, seckillId, TimeUtil.getTimeMillis(now), TimeUtil.getTimeMillis(startTime), TimeUtil.getTimeMillis(endTime));
        }
        //转化特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(Long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    @Override
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        try {
            //执行秒杀逻辑:减库存+记录购买行为
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, LocalDateTime.now());
            if (updateCount <= 0) {
                //没有更新到记录,秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //唯一:seckillId,userPhone
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //所有编译期异常，转换为运行期异常
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }
    }
}