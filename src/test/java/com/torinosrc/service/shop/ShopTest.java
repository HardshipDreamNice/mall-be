package com.torinosrc.service.shop;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.torinosrc.TorinosrcSpringBootBeApplication;
import com.torinosrc.commons.utils.GsonUtils;
import com.torinosrc.dao.shopproductdetail.ShopProductDetailDao;
import com.torinosrc.dao.user.UserDao;
import com.torinosrc.model.entity.product.Product;
import com.torinosrc.model.entity.productdetail.ProductDetail;
import com.torinosrc.model.entity.shop.Shop;
import com.torinosrc.model.entity.shopproduct.ShopProduct;
import com.torinosrc.model.entity.shopproductdetail.ShopProductDetail;
import com.torinosrc.model.entity.user.User;
import com.torinosrc.model.view.common.CustomPage;
import com.torinosrc.model.view.json.Condition;
import com.torinosrc.model.view.product.ProductView;
import com.torinosrc.model.view.shop.ShopView;
import com.torinosrc.model.view.shopproduct.ShopProductView;
import com.torinosrc.model.view.shopproductdetail.ShopProductDetailView;
import com.torinosrc.service.product.ProductService;
import com.torinosrc.service.shopproduct.ShopProductService;
import com.torinosrc.service.shopproductdetail.ShopProductDetailService;
import com.torinosrc.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <b><code>ShopTest</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>18:53
 *
 * @author PanXin
 * @version 1.0.0
 * @since mall-be  1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TorinosrcSpringBootBeApplication.class)
public class ShopTest {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopProductService shopProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopProductDetailService shopProductDetailService;


    @Autowired
    private ProductService productService;

    @Autowired
    private UserDao userDao;

    @Test
    public void testCreateShop(){

        ShopView shopView = new ShopView();
        shopView.setParentId(0L);
        shopView.setTitle("我的小店铺1");
        shopView.setUserId(1L);
        shopService.saveEntity(shopView);
    }

    @Test
    public void testDeleteShop(){

        shopService.deleteEntity(1L);

    }

    /**
     * 添加商品的情况
     */
    @Test
    public void testAddProduct(){

        Product product = new Product();
        product.setId(1L);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(1L);
        Shop shop = new Shop();
        shop.setId(1L);
        ShopProductView shopProductView = new ShopProductView();
        shopProductView.setProduct(product);
        shopProductView.setShopId(shop.getId());
        shopProductView.setRecommendReason("看多两眼就想买了");
        shopProductView = shopProductService.saveEntity(shopProductView);

        ShopProductDetailView shopProductDetailView = new ShopProductDetailView();
        shopProductDetailView.setProductDetail(productDetail);
        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(shopProductView.getId());

        shopProductDetailView.setShopProduct(shopProduct);
        shopProductDetailService.saveEntity(shopProductDetailView);
    }

    @Test
    public void testFindShop(){

       ShopView shopView =  shopService.findShopByUserId(1L);

        System.out.println("output: "+GsonUtils.toJsonStringWithNull(shopView));

    }

    @Test
    public void testShopProductList(){
        ShopProductView shopProductView = new ShopProductView();
//        shopProductView.setCondition("   {\n" +
//                "        \"where\": {\n" +
//                "            \"shopId\":{\n" +
//                "                \"$eq\": 1\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }");
        List<ShopProductView> list = shopProductService.getEntitiesByParms(shopProductView,0,65535).getContent();

        for (ShopProductView shopProductView1 : list) {
//            shopProductView1.setShopProductDetails(null);
            String mapJson = JSON.toJSONString(shopProductView1);
            System.out.println("output: "+mapJson);
        }

    }

    @Test
    public void testFindShopProduct(){

        ShopProductView shopProductView = new ShopProductView();
        shopProductView.setShopId(1L);

       CustomPage<ShopProductView> customPage = shopProductService.findShopProductViewsForWx(shopProductView,0,65535);

        System.out.println("customPage.getContent().size(): "+customPage.getContent().size());
    }

    @Test
    public void testAvator(){

        String avator = userDao.findAvatarByUserId(4L);

        System.out.println("avatar: "+avator);

    }

    @Test
    public void findWxProducts(){

        ProductView productView = new ProductView();
        CustomPage<ProductView> productViewCustomPage = productService.findProductListForWx(productView,0,6553500);
        for (ProductView productView1 : productViewCustomPage.getContent()) {

            System.out.println(productView1.getPriceIntervalUp()+"======================"+productView1.getPriceIntervaDown());
        }
    }

    @Test
    public void testCreateShopQrCode() {
        ShopView shopView = new ShopView();
        shopView.setRedirectUrlId(0L);
        shopView.setId(5L);
        shopService.createShopQrCode(shopView);
    }




}
