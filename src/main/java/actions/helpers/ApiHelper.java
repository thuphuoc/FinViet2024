package actions.helpers;

import actions.common.GlobalConstants;
import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    Gson gson = new Gson();
    Response rsp;

    public Response postRequestJson(Object bodyData, String url) {
        Allure.step("Call Api: " + url);
        Allure.step("Body data: " + gson.toJson(bodyData));
//        System.out.println("Body: "+ gson.toJson(bodyData));
        RequestSpecification req = given();
        rsp = req.baseUri(GlobalConstants.URL)
                .contentType("application/json")
                .header("x-app-id", "ca5e7185-9b34-4321-b51e-019f0a5ff63f")
                .body(gson.toJson(bodyData)).when().post(url);
//        Allure.step("Response: " + rsp.prettyPrint());
        return rsp;
    }

    public String getReponse(Response rsp, String jsonPath) {
        return rsp.getBody().path(jsonPath).toString();
    }
}
