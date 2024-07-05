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
        rsp = req.baseUri(url)
                .contentType("application/json")
                .header("Authorization",GlobalConstants.TOKEN)
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
