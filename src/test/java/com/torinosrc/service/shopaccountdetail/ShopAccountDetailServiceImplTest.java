package com.torinosrc.service.shopaccountdetail;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.service.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class ShopAccountDetailServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopAccountDetailService shopAccountDetailService;

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testCalculateOrderProfit(){
        OrderView orderView=orderService.findByOrderNo("153243191205092717");
        shopAccountDetailService.calculateOrderProfit(orderView);
//       Shop shop= shopDao.getOne(2l);
//       System.out.println(shop.getPercent2());
//       System.out.println(shop);

    }
}
