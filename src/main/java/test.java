
import actions.common.BasePage;
import actions.common.BaseTest;
import actions.common.GlobalConstants;
import actions.helpers.ApiHelper;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.logging.log4j.core.config.Order;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pojo.loaikhuyenmai.GiamGiaSPTheoBoiSo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test extends BaseTest {

    @Test
    public void test1() {
        BasePage basePage = new BasePage();
        ApiHelper apiHelper = new ApiHelper();
        int quanity = 6;
        String sku = "SKUP117799V334126";
        //	Sữa Abbott Grow Gold Hộp	SKUP117883V334265 ok
        //Nước ngọt test Lon	SKUP117799V334126
        int maNPP = 617895;
        String soDT = "0968686868";
        System.out.println("Data push: "+sku+" || "+quanity+" || "+maNPP+" || "+soDT);

        GiamGiaSPTheoBoiSo.Order order = getOrder(maNPP, sku, quanity, soDT);
        Gson gson = new Gson();
        Response rsp = apiHelper.postRequestJsonEcom(order, GlobalConstants.URL_ECOM);
//        System.out.println(gson.toJson(order));
        if (rsp.statusCode() == 200) {
            String rsp1 = rsp.body().asString();
            JSONObject jsonObjectRsp = new JSONObject(rsp1);

            String quantity_purchase = jsonObjectRsp.getJSONObject("data").get("quantity_purchase").toString();
            String total_amount_cart = jsonObjectRsp.getJSONObject("data").get("total_amount").toString();

            Integer special_price = Integer.parseInt(gotoProduct_variant(jsonObjectRsp).get("special_price").toString());
            Integer base_price = Integer.parseInt (gotoProduct_variant(jsonObjectRsp).get("base_price").toString());

            System.out.println("Tổng SL sp: " + quantity_purchase);
            System.out.println("Tổng tiền hàng: " + total_amount_cart);
            System.out.println("Giá sp sau KM: " + special_price);


            int sizePromtionList = gotoPromotionList(jsonObjectRsp).length();
            if (sizePromtionList != 0) {
                System.out.println("Có "+sizePromtionList +" promotion cho sp này");
                for (int i = 0; i < sizePromtionList; i++) {
                    String operator= gotoPromotionList(jsonObjectRsp).getJSONObject(i).get("operator").toString();
                    if(operator.equals("discountFixedAmount")) {
                        String promtion_list_id = gotoPromotionList(jsonObjectRsp).getJSONObject(i).get("promotion_id").toString();
                        String promtion_list_name = gotoPromotionList(jsonObjectRsp).getJSONObject(i).get("promotion_name").toString();
                        Integer from_quantity = Integer.parseInt(gotoDiscountAction(jsonObjectRsp, i).get("from_quantity").toString());
                        Integer discount_value =  Integer.parseInt(gotoDiscountAction(jsonObjectRsp, i).get("discount_value").toString());

                        System.out.println("Thông tin KM " + i + ": " + promtion_list_id + " || " + promtion_list_name + " || Mua SL " + from_quantity + "|| discount_value " + discount_value);

                        //  Giá = (Giá * SL mua - $discount_value) / SL mua;
                        int GiaSauKMCongThuc=(base_price*quanity-discount_value)/quanity;
                        verifyTrue(GiaSauKMCongThuc==special_price,"Check giá KM CT "+GiaSauKMCongThuc+" || Thực tế: "+special_price);
                    }
                }
            } else {
                System.out.println("SP ko có khuyến mãi");
            }
        }else{
            System.out.println("Lỗi call api status code "+rsp.getStatusCode());
    }
    }

    private static GiamGiaSPTheoBoiSo.Order getOrder(int maNPP, String sku, int quanity, String soDT) {
        GiamGiaSPTheoBoiSo giamGiaSPTheoBoiSo = new GiamGiaSPTheoBoiSo();
        GiamGiaSPTheoBoiSo.Province province = giamGiaSPTheoBoiSo.new Province(1);
        GiamGiaSPTheoBoiSo.District district = giamGiaSPTheoBoiSo.new District(10);
        GiamGiaSPTheoBoiSo.Ward ward = giamGiaSPTheoBoiSo.new Ward(27184);
        GiamGiaSPTheoBoiSo.Customer customer = giamGiaSPTheoBoiSo.new Customer(province, district, ward);
        GiamGiaSPTheoBoiSo.Info info = giamGiaSPTheoBoiSo.new Info(customer, 2, 2, "COD", "MERCHANT");

        List<Object> gift = new ArrayList<>();
        List<Object> vouchers = new ArrayList<>();
        GiamGiaSPTheoBoiSo.ProductVariant productVariant = giamGiaSPTheoBoiSo.new ProductVariant(maNPP, sku, quanity, 13000, 1300, gift);

        GiamGiaSPTheoBoiSo.Product product = giamGiaSPTheoBoiSo.new Product(117883, Arrays.asList(productVariant));
        GiamGiaSPTheoBoiSo.Cart cart = giamGiaSPTheoBoiSo.new Cart(617895, "FINVIET",vouchers, Arrays.asList(product));
        GiamGiaSPTheoBoiSo.Order order = giamGiaSPTheoBoiSo.new Order("038c5c3d0a6604da8b2f19e0c28b0c17", Arrays.asList(cart), info, soDT, "MERCHANT",false);
        return order;
    }

    private static JSONObject gotoProduct_variant(JSONObject jsonObjectRsp) {
        return jsonObjectRsp.getJSONObject("data").getJSONArray("carts").getJSONObject(0)
                .getJSONArray("products").getJSONObject(0).getJSONArray("product_variants")
                .getJSONObject(0);
    }

    private static JSONArray gotoPromotionList(JSONObject jsonObjectRsp) {
        return gotoProduct_variant(jsonObjectRsp).getJSONArray("promotions_list");
    }


    private static JSONObject gotoDiscountAction(JSONObject jsonObjectRsp, int i) {
        return gotoPromotionList(jsonObjectRsp).getJSONObject(i).getJSONObject("discount_action");
    }

}


