#数据库初始化脚本

#创建数据库
create database seckill;
#使用数据库
use seckill;
#创建秒杀库存表
create table seckill(
    `seckill_id` bigint not null AUTO_INCREMENT COMMENT '商品库存id',
    `name` varchar(120) not null comment '商品名称',
    `number` int NOT NULL comment '库存数量',
    `start_time` timestamp  not null  comment '秒杀开始时间',
    `end_time` timestamp not null comment '秒杀结束时间',
    `create_time`  timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
    PRIMARY KEY (seckill_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
) ENGINE= InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'

#初始化数据
insert into
    seckill(name,number,start_time,end_time)
values
  ('1000元秒杀iphone',100,'2015-11-01 00:00:00','2015-11-01 00:00:00'),
  ('500元秒杀ipad2',200,'2015-11-01 00:00:00','2015-11-01 00:00:00'),
  ('300元秒杀小米4',100,'2015-11-01 00:00:00','2015-11-01 00:00:00'),
  ('200元秒杀红米note',100,'2015-11-01 00:00:00','2015-11-01 00:00:00');

#秒杀成功明细表
#用户登陆认证相关的信息
create table success_killed(
    `seckill_id` bigint not null comment '秒杀商品id',
    `user_phone` bigint not null comment '用户手机号',
    `state` tinyint not null default -1 comment '状态标示：-1无效，0：成功 1：已付款',
    `create_time` timestamp not null comment '创建时间',
    PRIMARY KEY(seckill_id,user_phone),/*联合主键*/
    key idx_create_time(create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

#秒杀执行存储过程
#定义存储过程
#参数:in输入参数;out 输出参数
#ROW_COUNT():返回上一条修改类型sql(DELETE,INSERT,UPDATE)的影响行数
#ROW_COUNT():0:未修改数据;>0表示修改的行数<0:sql错误/未执行修改sql
CREATE  PROCEDURE `seckill`.`execute_seckill`( IN v_seckill_id BIGINT, IN v_phone BIGINT, IN v_kill_time TIMESTAMP, OUT r_result INT )
BEGIN
		DECLARE insert_count INT DEFAULT 0;
		START TRANSACTION;

		INSERT IGNORE INTO success_killed ( seckill_id, user_phone, state, create_time )
		VALUES
			( v_seckill_id, v_phone, 0, NOW( ));

		SELECT ROW_COUNT( ) INTO insert_count;

		IF( insert_count = 0 ) THEN
				ROLLBACK;
				SET r_result = -1;
		ELSEIF ( insert_count < 0 ) THEN
				ROLLBACK;
				SET r_result = -2;
		ELSE
				UPDATE seckill
				SET number = number - 1
				WHERE
					seckill_id = v_seckill_id
					AND v_kill_time BETWEEN start_time
					AND end_time
					AND number > 0;

				SELECT ROW_COUNT( ) INTO insert_count;

				IF( insert_count = 0 ) THEN
					ROLLBACK;
					SET r_result = 0;
				ELSEIF ( insert_count < 0 ) THEN
					ROLLBACK;
					SET r_result = -2;
				ELSE
					COMMIT;
					SET r_result = 1;
				END IF;
		END IF;
END;
#存储过程
#1.存储过程优化:事务行级锁持有的时间
#2.不要过度依赖存储过程
#3.简单逻辑可以应用存储过程
#4.QPS:一个秒杀单6000/qps