import actions.common.BasePage;
import actions.helpers.ApiHelper;
import actions.helpers.ExcelHelper;
import com.google.gson.*;

public class test {
    public static void main(String[] args) {
        String excelPath = "src/main/resources/HTTangKemSPTheoBoiSo.xlsx";
        ExcelHelper excel= new ExcelHelper();
        excel.setExcelFile(excelPath, "valid_data");
        ApiHelper apiHelper= new ApiHelper();
        BasePage b= new BasePage();

        String schemeID="1a3d2a9f-ded2-4090-b8cb-0af8be1ccb5c";

        String listIdMaKMFromAPI = apiHelper.getReponseKMFromAPI(excel, apiHelper, 3, schemeID,"orders[0].products[0].promotions.id").replace("[","").replace("]","").replaceAll(" ","");
        System.out.println("List schema from API: " + listIdMaKMFromAPI);

        String[] myArray = b.getArrayAfterPhanTachDauPhay(listIdMaKMFromAPI);
        int findIndexFromID= b.getIndexSchemaIdInArray(myArray, schemeID);

        String listDiscount=apiHelper.getReponseKMFromAPI(excel,apiHelper,3,schemeID,"orders[0].products[0].promotions["+findIndexFromID+"].discount_action");
//        System.out.println(listDiscount);

        JsonElement jsonEl=JsonParser.parseString(listDiscount);
        System.out.println(jsonEl.getAsJsonObject().get("any").getAsJsonArray().get(0).getAsJsonObject().get("discountAction").getAsJsonObject().get("quantity"));
        String name=apiHelper.getReponseKMFromAPI(excel,apiHelper,3,schemeID,"promotions_allow_apply["+findIndexFromID+"].name");
        System.out.println(name);
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

