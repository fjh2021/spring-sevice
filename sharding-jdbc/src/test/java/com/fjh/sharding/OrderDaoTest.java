package com.fjh.sharding;

import com.fjh.sharding.dao.OrderDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author fjh
 * @Email
 * @Date 2021/8/25 12:13
 * @Description
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ShardingJdbcApplication.class})
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;
    @Test
    public void testInsertOrder(){
        for (int i = 0 ; i<10; i++){
            orderDao.insertOrder(new BigDecimal((i+1)*5),1L,"WAIT_PAY");
        }
    }

    @Test
    public void testSelectOrderbyIds(){
        List<Long> ids = new ArrayList<>();
        ids.add(637265431656136704L);
        ids.add(637265430532063233L);
        List<Map> maps = orderDao.selectOrderbyIds(ids);
        System.out.println(maps);
    }
}
