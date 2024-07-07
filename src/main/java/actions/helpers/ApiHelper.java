package actions.helpers;

import actions.common.GlobalConstants;
import actions.pageobject.loaikhuyenmai.ApDungSanPhamPO;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import pojo.GetSchemaIDAndSTT;
import pojo.loaikhuyenmai.GiamGiaSPTheoBoiSo;
import pojo.loaikhuyenmai.TangKemSPTheoBoiSo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    Gson gson = new Gson();
    Response rsp;
    TangKemSPTheoBoiSo tangKemSPTheoBoiSo = new TangKemSPTheoBoiSo();

    public Response postRequestJson(Object bodyData, String url) {
        Allure.step("Call Api: " + url);
        Allure.step("Body data: " + gson.toJson(bodyData));
        RequestSpecification req = given();
        rsp = req.baseUri(GlobalConstants.URL)
                .contentType("application/json")
                .header("x-app-id", "ca5e7185-9b34-4321-b51e-019f0a5ff63f")
                .body(gson.toJson(bodyData)).when().post(url);
        return rsp;
    }
    public Response postRequestJsonEcom(Object bodyData, String url) {
        Allure.step("Call Api: " + url);
        Allure.step("Body data: " + gson.toJson(bodyData));
        RequestSpecification req = given();
        rsp = req.baseUri(url)
                .contentType("application/json")
                .header("Authorization",GlobalConstants.TOKEN)
                .body(gson.toJson(bodyData)).when().post(url);
        return rsp;
    }

    public String getReponse(Response rsp, String jsonPath) {
        return rsp.getBody().path(jsonPath).toString();
    }

    public TangKemSPTheoBoiSo.Orders getOrders(String sku, int quantity, String company_id, String agent_phone) {

        TangKemSPTheoBoiSo.HowToLoad howToLoadPojo= tangKemSPTheoBoiSo.new HowToLoad(true, "VALID_PROMOTION", "SELF_PICK", false, 0);
        TangKemSPTheoBoiSo.Product proDct = tangKemSPTheoBoiSo.new Product(sku, 11111, quantity, howToLoadPojo);
        List<TangKemSPTheoBoiSo.Product> products = new ArrayList<>();
        TangKemSPTheoBoiSo.Order orderPojo = tangKemSPTheoBoiSo.new Order(products, company_id, "CodChannel", 66666, howToLoadPojo);

        products.add(proDct);
        List<TangKemSPTheoBoiSo.Order> orders = new ArrayList<>();
        orders.add(orderPojo);

        TangKemSPTheoBoiSo.Orders orderList = tangKemSPTheoBoiSo.new Orders(
                orders,
                agent_phone,
                null,
                "ecomv2",
                "CodChannel",
                66666,
                howToLoadPojo,
                "",
                "",
                false,
                false
        );
        return orderList;
    }

    public List<GetSchemaIDAndSTT> getGetSchemaIDAndSTTS(ExcelHelper excel) {
        int soLuongDong = excel.countRowsHasData();
        List<GetSchemaIDAndSTT> listSchemIDAndSTT = new ArrayList<GetSchemaIDAndSTT>();
        for (int j = 1; j < soLuongDong; j++) {
            String schemeID = excel.getCellData("SchemaID", j);
            String STT_Sheet_TangKemSPTheoBoiSo = excel.getCellData("STT", j);
            String name=excel.getCellData("SchemaName", j);
            int STT = Integer.parseInt(STT_Sheet_TangKemSPTheoBoiSo);
            listSchemIDAndSTT.add(new GetSchemaIDAndSTT(schemeID, STT,name));
        }
        return listSchemIDAndSTT;
    }

    public String getReponseKMFromAPI(ExcelHelper excel, ApiHelper apiHelper, int i) {
        String sku = excel.getCellData("sku", i);
        int quantity = Integer.parseInt(excel.getCellData("quantity", i));
        String company_id = excel.getCellData("company_id", i);
        String agent_phone = excel.getCellData("agent_phone", i);
        System.out.println("Data test dòng  " + i + "|| " + sku + "||" + quantity + "||" + company_id + "||" + agent_phone);
        Allure.step("Data test dòng  " + i + "|| " + sku + "||" + quantity + "||" + company_id + "||" + agent_phone);
        TangKemSPTheoBoiSo.Orders orderList = getOrders(sku, quantity, company_id, agent_phone);

        Response rsp = apiHelper.postRequestJson(orderList, GlobalConstants.URL_API);
        if (rsp.statusCode() == 200) {
            return rsp.body().asString();
        }else {
            return null;
        }
    }

    public  GiamGiaSPTheoBoiSo.Order getOrder(int maNPP, String sku, int quanity, String soDT) {
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

    public String getReponseFromAPIEcom(ExcelHelper excel, ApiHelper apiHelper, int i){
        String sku = excel.getCellData("sku", i);
        int quantity = Integer.parseInt(excel.getCellData("quantity", i));
        String company_id = excel.getCellData("company_id", i);
        String agent_phone = excel.getCellData("agent_phone", i);

        System.out.println("Data push: "+sku+" || "+quantity+" || "+company_id+" || "+agent_phone);

        GiamGiaSPTheoBoiSo.Order order = getOrder(Integer.parseInt(company_id), sku, quantity, agent_phone);
        Gson gson = new Gson();
        Response rsp = postRequestJsonEcom(order, GlobalConstants.URL_ECOM);
        if (rsp.statusCode() == 200) {
            return rsp.body().asString();
        }else {
            return null;
        }
    }

    public JSONObject gotoProduct_variant(JSONObject jsonObjectRsp) {
        return jsonObjectRsp.getJSONObject("data").getJSONArray("carts").getJSONObject(0)
                .getJSONArray("products").getJSONObject(0).getJSONArray("product_variants")
                .getJSONObject(0);
    }

    public JSONArray gotoPromotionList(JSONObject jsonObjectRsp) {
        return gotoProduct_variant(jsonObjectRsp).getJSONArray("promotions_list");
    }


    public JSONObject gotoDiscountAction(JSONObject jsonObjectRsp, int i) {
        return gotoPromotionList(jsonObjectRsp).getJSONObject(i).getJSONObject("discount_action");
    }
}
