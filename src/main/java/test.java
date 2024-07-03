import actions.common.BasePage;
import actions.helpers.ApiHelper;
import actions.helpers.ExcelHelper;
import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class test {
    public static void main(String[] args) {
        String excelPath = "src/main/resources/HTTangKemSPTheoBoiSo.xlsx";
        ExcelHelper excel= new ExcelHelper();
        excel.setExcelFile(excelPath, "valid_data");
        ApiHelper apiHelper= new ApiHelper();
        BasePage b= new BasePage();

        String schemeID="1a3d2a9f-ded2-4090-b8cb-0af8be1ccb5c";

        String rspFromAPI = apiHelper.getReponseKMFromAPI(excel, apiHelper, 3, schemeID);
        JSONObject jsonObjectRspFromAPI= new JSONObject(rspFromAPI);
        JSONArray jsonArrayPromotionApply=  jsonObjectRspFromAPI.getJSONArray("promotions_allow_apply");
        System.out.println("List schema from API: " + jsonArrayPromotionApply.getJSONObject(0).get("id"));
        JSONArray jsonArrayPromotion=  jsonObjectRspFromAPI.getJSONArray("orders").getJSONObject(0).getJSONArray("products").getJSONObject(0).getJSONArray("promotions");
            for(int i = 0 ; i < jsonArrayPromotionApply.length(); i++){
                String id= jsonArrayPromotionApply.getJSONObject(i).getString("id");
                if(id.equals(schemeID)){
                    String quantityAPI = "";
                    String name= jsonArrayPromotionApply.getJSONObject(i).getString("name");
                    for(int j = 0 ; j < jsonArrayPromotion.length(); j++) {
                        String idPro = jsonArrayPromotion.getJSONObject(j).getString("id");
                        if (idPro.equals(schemeID)) {
                            String discount_action = jsonArrayPromotion.getJSONObject(j).getString("discount_action");
                            JsonElement jsonEl = JsonParser.parseString(discount_action);
                            quantityAPI = jsonEl.getAsJsonObject().get("any").getAsJsonArray().get(0).getAsJsonObject().get("discountAction").getAsJsonObject().get("quantity").toString();
                            break;
                        }
                    }
                    System.out.println(id + " || "+name + "|| "+quantityAPI);
                    break;
                }
            }
//
//        String[] myArray = b.getArrayAfterPhanTachDauPhay(listIdMaKMFromAPI);
//        int findIndexFromID= b.getIndexSchemaIdInArray(myArray, schemeID);
//
//        String listDiscount=apiHelper.getReponseKMFromAPI(excel,apiHelper,2,schemeID,"orders[0].products[0].promotions["+findIndexFromID+"].discount_action");
////        System.out.println(listDiscount);
//
//        JsonElement jsonEl=JsonParser.parseString(listDiscount);
//        System.out.println(jsonEl.getAsJsonObject().get("any").getAsJsonArray().get(0).getAsJsonObject().get("discountAction").getAsJsonObject().get("quantity"));
//        String name=apiHelper.getReponseKMFromAPI(excel,apiHelper,2,schemeID,"promotions_allow_apply["+findIndexFromID+"].name");
//        System.out.println(name);
//        JsonArray jsonArray= new JsonArray();
//        jsonArray.add(jsonObject.get("any"));
//        System.out.println(jsonArray.get(0));
    }

    private static int findIndexFromID(String[] myArray, String valueToFind) {
        int index = -1;
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i].equals(valueToFind)) {
                index = i;
                break;
            }
        }
        return index;
    }
}

