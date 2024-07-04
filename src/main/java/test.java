
import actions.common.BasePage;
import actions.common.GlobalConstants;
import actions.helpers.ApiHelper;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.loaikhuyenmai.GiamGiaSPTheoBoiSo;

import java.util.Arrays;
import java.util.List;

public class test {

    @Test
    public void test1() {
        ApiHelper apiHelper= new ApiHelper();
        GiamGiaSPTheoBoiSo giamGiaSPTheoBoiSo= new GiamGiaSPTheoBoiSo();
        
        GiamGiaSPTheoBoiSo.Province province = giamGiaSPTheoBoiSo.new Province(1);
        GiamGiaSPTheoBoiSo.District district = giamGiaSPTheoBoiSo.new District(10);
        GiamGiaSPTheoBoiSo.Ward ward = giamGiaSPTheoBoiSo.new Ward(27184);
        GiamGiaSPTheoBoiSo.Customer customer = giamGiaSPTheoBoiSo.new Customer(province,district,ward);
        GiamGiaSPTheoBoiSo.Info info = giamGiaSPTheoBoiSo.new Info(customer,2,2,"COD","MERCHANT");

        int quanity= 2;
        String sku= "SKUP117883V334265";
        int maNPP=617895;
        String soDT="0368199222";
        List<Object> gifts = null;
        GiamGiaSPTheoBoiSo.ProductVariant productVariant = giamGiaSPTheoBoiSo.new ProductVariant(quanity,sku,maNPP,13000,1300, gifts);

        GiamGiaSPTheoBoiSo.Product product = giamGiaSPTheoBoiSo.new Product(117883,Arrays.asList(productVariant));
        GiamGiaSPTheoBoiSo.Cart cart = giamGiaSPTheoBoiSo.new Cart(617895,"FINVIET",Arrays.asList(product));
        GiamGiaSPTheoBoiSo.Order order = giamGiaSPTheoBoiSo.new Order("038c5c3d0a6604da8b2f19e0c28b0c17",Arrays.asList(cart),info,soDT,"MERCHANT");
        Response rsp=apiHelper.postRequestJsonEcom(order, GlobalConstants.URL_ECOM) ;
        System.out.println(rsp.getBody().prettyPrint());

    }
}


