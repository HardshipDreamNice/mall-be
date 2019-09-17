package com.torinosrc.service.indexproducttypeproduct.impl;

import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.dao.indexproducttypeproduct.IndexProductTypeProductDao;
import com.torinosrc.model.entity.indexproducttypeproduct.IndexProductTypeProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class IndexProductTypeProductServiceImplTest {

    @Autowired
    private IndexProductTypeProductDao indexProductTypeProductDao;

    @Test
    public void testFindIndexProductTypeProductsByIndexProductTypeId(){
        Long typeId = 1L;
        int num = 6;
        List<IndexProductTypeProduct> productTypeProducts = indexProductTypeProductDao.findByIndexProductTypeIdOrderByWeightLimit(typeId, num);
        System.out.println("productTypeProducts.size: " + productTypeProducts.size());
    }
}