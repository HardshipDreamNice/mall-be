package com.torinosrc.service.product.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.product.ProductDao;
import com.torinosrc.dao.productdetail.ProductDetailDao;
import com.torinosrc.dao.shop.ShopDao;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.view.order.OrderView;
import com.torinosrc.service.order.OrderService;
import com.torinosrc.service.shopaccountdetail.ShopAccountDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Test
    public void testGetProductPriceRange() {
        Long productId = 1L;
        Map productPriceMap = productDao.findProductPriceRange(productId);
        System.out.println(productPriceMap.get("min_price"));
        System.out.println(productPriceMap.get("max_price"));
    }

    @Test
    public void testGetUppestPrice() {
        Long productId = 1L;
        ProductDetail productDetail = productDetailDao.findFirstByProductIdOrderByUppestPriceDesc(productId);
        System.out.println(productDetail.getUppestPrice());
    }

}
