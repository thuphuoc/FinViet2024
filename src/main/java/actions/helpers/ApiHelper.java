package actions.helpers;

import actions.common.GlobalConstants;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.GetSchemaIDAndSTT;
import pojo.HowToLoad;
import pojo.Order;
import pojo.Product;
import pojo.Orders;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    Gson gson = new Gson();
    Response rsp;

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
        rsp = req.baseUri(GlobalConstants.URL)
                .contentType("application/json")
                .header("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjE0Njg1NjFjMmU0OWNmMzdmOGJhMzNjOTE2YWJjZGUwNzdmNDQxN2EwOTY2Y2YwYWNjNjBjNjgzNjIwZDQ4OGNmMTQxNTU5ZjBhODI5NDcwIn0.eyJhdWQiOiIzIiwianRpIjoiMTQ2ODU2MWMyZTQ5Y2YzN2Y4YmEzM2M5MTZhYmNkZTA3N2Y0NDE3YTA5NjZjZjBhY2M2MGM2ODM2MjBkNDg4Y2YxNDE1NTlmMGE4Mjk0NzAiLCJpYXQiOjE3MjAwNTg2MTQsIm5iZiI6MTcyMDA1ODYxNCwiZXhwIjoxNzIwMTQ1MDEyLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.WG8gnP1T9QiukGPtILikWqKnvgCPI-D3Xk-D1W7HDod1IFH7_B6HRrUmsK5FCGf6aJHrztfS5AjMyapWJQIIe_XjMLPVQSgH7z49F9urchFb8nzQwnz-54Wgh7_9G-_K7T4CnB2eMzfBywpIhMdch-SWB1lBm2vpDsAn6yXRtqIkBWh4DodJv978XvCStPkVinbp7BGyE8p1_po2Z23ykBC2ZEVE6Zj301BGnCLN35uLWqq5hJkfL0FnBat9OSdv1AMowgi8uPOMLcMYWKtN2K_KjINRUNd-_Uc3BraLjxnmdKCFhKujkqVYGSKjnzlVjERpq0VREzkSX3c9s0d2FQc1OsbRtPxJhgUnuz06bIumFQ539qbxxk_csqU41_7cm2BY5zSZGKdlnWDwtOI1zJwIuX_aN1ljAaBFr6ByZUWP9MIk7QQnWfl9Egj2UHTk6Dbd5qvGBQnEAWKq9M5t5k-ksBoVc53Jrx2KIl5nsK3OSVTIAE4UKrZYZCtLEtKZ80BLb1e8DzEEeYLfqa-VPwL6rik827u63WiIVdrfCPy8g2UydNbjmUhyq4uy95YVOPjGeCxcf_mnkr_48DmI2qUUetD-V7Z-mw7AtND9yeMjzH7oUqCLQgyVpbMa1zsEHFuiO53VZoOW50XcxkODuymJP3MBDuWTRiDJKeIS_6E")
                .body(gson.toJson(bodyData)).when().post(url);
        return rsp;
    }

    public String getReponse(Response rsp, String jsonPath) {
        return rsp.getBody().path(jsonPath).toString();
    }

    public Orders getOrders(String sku, int quantity, String company_id, String agent_phone) {
        HowToLoad howToLoadPojo = new HowToLoad(true, "VALID_PROMOTION", "SELF_PICK", false, 0);
        Product proDct = new Product(sku, 11111, quantity, howToLoadPojo);
        List<Product> products = new ArrayList<>();
        Order orderPojo = new Order(products, company_id, "CodChannel", 66666, howToLoadPojo);

        products.add(proDct);
        List<Order> orders = new ArrayList<>();
        orders.add(orderPojo);

        Orders orderList = new Orders(
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
        Orders orderList = getOrders(sku, quantity, company_id, agent_phone);

        Response rsp = apiHelper.postRequestJson(orderList, GlobalConstants.URL_API);
        if (rsp.statusCode() == 200) {
            return rsp.body().asString();
        }else {
            return null;
        }
    }
}
