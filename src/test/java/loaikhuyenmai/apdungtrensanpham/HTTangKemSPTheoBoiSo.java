package loaikhuyenmai.apdungtrensanpham;

import actions.common.BasePage;
import actions.common.GlobalConstants;
import actions.helpers.ApiHelper;
import actions.helpers.ExcelHelper;
import actions.common.BaseTest;
import actions.pageobject.GeneratorManager;
import actions.pageobject.apdungtrensanpham.HTTangKemSPTheoBoiSoPO;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.GetSchemaIDAndSTT;

import java.util.List;

public class HTTangKemSPTheoBoiSo extends BaseTest {
    BasePage basePage= new BasePage();
    ExcelHelper excel;;
    ApiHelper apiHelper;
    String excelPath = "src/main/resources/HTTangKemSPTheoBoiSo.xlsx";
    String sheetName_ValidData = "valid_data";
    String sheetName_InvalidData = "invalid_data";
    String sheetName_TangKemSPTheoBoiSo = "TangKemSPTheoBoiSo";
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;
    HTTangKemSPTheoBoiSoPO httangKemSPTheoBoiSoPage;
    String[] kenhMuaHang = {"CONSUMER", "MERCHANT", "SALESMAN"};
    String textChung;
    String loaiKM = "Áp dụng trên sản phẩm";
    String hinhThucKM;
    String mucdoHienThi = "1";
    String loaiCT = "Chương trình bình thường";
    String maCTKM_TaoScheme;

    @BeforeClass
    public void initDriver() {
        deleteAllFilesInReportAllure();
        driver = getBrowserDriver("Chrome");
        khuyenMaiPage = GeneratorManager.getKhuyenMaiPage(driver);
        httangKemSPTheoBoiSoPage = GeneratorManager.getHTTangKemSPTheoBoiSoPage(driver);
        step("Mở trang: " + GlobalConstants.URL);
        khuyenMaiPage.openPageUrl(driver, GlobalConstants.URL);
        step("Login với username: " + GlobalConstants.USERNAME + "|| password: " + GlobalConstants.PASSWORD);
        khuyenMaiPage.loginPromotion(driver, GlobalConstants.USERNAME, GlobalConstants.PASSWORD);
    }

    @Test(priority = 1)
    public void TC001_TangKemSPTheoBoiSo_TaoKM() {
        excel = new ExcelHelper();
        excel.setExcelFile(excelPath, "TangKemSPTheoBoiSo");
        excel.deleteColumnData("SchemaID");
//        int soDongExcelCoData = 2;
        int soDongExcelCoData = excel.countRowsHasData();
        for (int i = 1; i < soDongExcelCoData; i++) {
            try {
                khuyenMaiPage.openPageUrl(driver, GlobalConstants.URL_PROMTION);
                threadSecond(3);
                System.out.println("Run row: " + i);
                step("Tạo khuyến mãi thứ " + i);
                hinhThucKM = "Tặng kèm sản phẩm theo bội số";
                khuyenMaiPage.clickTaoMoiKhuyenMai("NPP trực thuộc NCC");
                khuyenMaiPage.searchNhapMaNCC("CN0617897");

                textChung = "Auto_KM_" + khuyenMaiPage.getDateTimeNow();
                khuyenMaiPage.taoThongTinCoBanKM(kenhMuaHang, textChung, loaiKM, hinhThucKM, mucdoHienThi, loaiCT);

                khuyenMaiPage.hinhThucApDungGoiKM(i, excel);
                khuyenMaiPage.themDieuKien("Phương thức thanh toán", i, excel);
                khuyenMaiPage.themDieuKien("Danh sách sản phẩm", i, excel);
                khuyenMaiPage.themDieuKien(" Danh sách NPP", i, excel);
                httangKemSPTheoBoiSoPage.themDieuKien("Số lượng sản phẩm", i, excel);
                httangKemSPTheoBoiSoPage.themDieuKien("Giá trị sản phẩm", i, excel);

                httangKemSPTheoBoiSoPage.themNoiDung("Tặng kèm sản phẩm cùng loại", i, excel);
                httangKemSPTheoBoiSoPage.themNoiDung("Tặng kèm sản phẩm trong danh sách", i, excel);

                khuyenMaiPage.themDoiTuong("Đối tượng thuộc số điện thoại", i, excel);

                khuyenMaiPage.clickToBtnByText("Tiếp tục");
                khuyenMaiPage.unCheckThietLapMa();

                khuyenMaiPage.clickToBtnByText("Đồng ý");
                threadSecond(1);

                khuyenMaiPage.searchKhuyenMai(textChung, "Tìm kiếm CTKM");
                maCTKM_TaoScheme = khuyenMaiPage.getMaCTKM();
                System.out.println("Mã CTKM đã duyệt: " + maCTKM_TaoScheme);
                step("Mã CTKM đã duyệt: " + maCTKM_TaoScheme);
                khuyenMaiPage.guiXetDuyet(maCTKM_TaoScheme);
                excel.setCellData(maCTKM_TaoScheme, i, "SchemaID");
                excel.setCellData(textChung, i, "SchemaName");
                if (i + 1 == soDongExcelCoData) {
                    khuyenMaiPage.waitDangDienRa(maCTKM_TaoScheme);
                }
            } catch (Exception e) {
                excel.setCellData("FAIL TẠO SCHEMA", i, "SchemaID");
                excel.setCellData("FAIL TẠO SCHEMA", i, "SchemaName");
                verifyFalse(true, "Run row " + i + ": " + e.getMessage());
            }
        }
    }

    @Test(priority = 2)
    public void TC002_CallApi_Valid_Data() {
        excel=new ExcelHelper();
        apiHelper=new ApiHelper();
        excel.setExcelFile(excelPath, sheetName_TangKemSPTheoBoiSo);
        List<GetSchemaIDAndSTT> listSchemIDAndSTT = apiHelper.getGetSchemaIDAndSTTS(excel);
        
        excel.setExcelFile(excelPath, sheetName_ValidData);
        excel.deleteColumnData(GlobalConstants.COL_SCHEMAID_MONGDOI);
        excel.deleteColumnData(GlobalConstants.COL_SCHEMAID_THUCTE);
        excel.deleteColumnData(GlobalConstants.COL_QUANTITY_THUCTE);

        for (int j = 0; j < listSchemIDAndSTT.size(); j++) {
            String schemeID = listSchemIDAndSTT.get(j).getSchemaID();
            int targetValue = listSchemIDAndSTT.get(j).getStt();
            String nameSchemaInSheet=listSchemIDAndSTT.get(j).getName();

            System.out.println("SCHEMA DÒNG " + (j+1) + ": " + schemeID);
            step("SCHEMA DÒNG  " + (j +1)+ ": " + schemeID);
            List<Integer> positions = excel.findPositions(excel.indexColumByText("STT"), targetValue);
            if (!positions.isEmpty()) {
                //Call api theo từng data test sheet valid_data
                for (int i = positions.get(0); i <= positions.get(1); i++) {
                    if (schemeID.equals("FAIL TẠO SCHEMA")) {
                        excel.setCellData("FAIL TẠO SCHEMA", i, GlobalConstants.COL_KETQUA);
                    } else {
                        try {
                            String rspFromAPI = apiHelper.getReponseKMFromAPI(excel, apiHelper, i, schemeID);
                            JSONObject jsonObjectRspFromAPI= new JSONObject(rspFromAPI);
                            JSONArray jsonArrayPromotionApply=  jsonObjectRspFromAPI.getJSONArray("promotions_allow_apply");
                            System.out.println("List schema from API: " + jsonArrayPromotionApply.getJSONObject(0).get("id"));
                            JSONArray jsonArrayPromotion=  jsonObjectRspFromAPI.getJSONArray("orders").getJSONObject(0).getJSONArray("products").getJSONObject(0).getJSONArray("promotions");
                            String SchemaIDAPI = "", schemaNameAPI = "",quantityAPI = "";
                            for(int k = 0 ; k < jsonArrayPromotionApply.length(); k++){
                                SchemaIDAPI = jsonArrayPromotionApply.getJSONObject(k).getString("id");
                                if(SchemaIDAPI.equals(schemeID)){
                                    schemaNameAPI = jsonArrayPromotionApply.getJSONObject(k).getString("name");
                                    for(int l = 0 ; l < jsonArrayPromotion.length(); l++) {
                                        String idPro = jsonArrayPromotion.getJSONObject(l).getString("id");
                                        if (idPro.equals(schemeID)) {
                                            String discount_action = jsonArrayPromotion.getJSONObject(l).getString("discount_action");
                                            JsonElement jsonEl = JsonParser.parseString(discount_action);
                                            quantityAPI = jsonEl.getAsJsonObject().get("any").getAsJsonArray().get(0).getAsJsonObject().get("discountAction").getAsJsonObject().get("quantity").toString();
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            if(SchemaIDAPI.equals(schemeID)){
                                excel.setCellData(schemeID, i, GlobalConstants.COL_SCHEMAID_MONGDOI);
                                excel.setCellData(schemeID, i, GlobalConstants.COL_SCHEMAID_THUCTE);
                                excel.setCellData(nameSchemaInSheet, i, GlobalConstants.COL_NAME_MONGDOI);
                                excel.setCellData(schemaNameAPI, i, GlobalConstants.COL_NAME_THUCTE);
                                excel.setCellData(quantityAPI, i, GlobalConstants.COL_QUANTITY_THUCTE);
                                String quantityMongDoi=excel.getCellData(GlobalConstants.COL_QUANTITY_MONGDOI,i).toString();
                                verifyTrue(quantityAPI.equals(quantityMongDoi),"Check discount thực tế: "+quantityAPI+" || mong đợi: "+quantityMongDoi);
                                verifyTrue(nameSchemaInSheet.equals(schemaNameAPI),"Check name thực tế: "+schemaNameAPI+" || mong đợi: "+nameSchemaInSheet);
                                if(quantityAPI.equals(quantityMongDoi) && nameSchemaInSheet.equals(schemaNameAPI)){
                                    excel.setCellData("PASS", i, GlobalConstants.COL_KETQUA);
                                }else{
                                    excel.setCellData("FAIL", i, GlobalConstants.COL_KETQUA);
                                }
                            } else {
                                excel.setCellData(schemeID, i, GlobalConstants.COL_SCHEMAID_MONGDOI);
                                excel.setCellData("FAIL", i, GlobalConstants.COL_KETQUA);
                            }
                        } catch (Exception e) {
                            verifyFalse(true, "Run row " + i + ": " + e.getMessage());
                            System.err.println("Run row: " + i + " " + e.getMessage());
                        }
                    }
                }
            }else{
                verifyFalse(true, "Result get index sheet valid fail ");
            }
            System.out.println("");
        }
    }


//    @Test(priority = 3)
    public void TC003_CallApi_InValid_Data() {
        excel=new ExcelHelper();
        apiHelper=new ApiHelper();
        excel.setExcelFile(excelPath, sheetName_TangKemSPTheoBoiSo);
        List<GetSchemaIDAndSTT> listSchemIDAndSTT = apiHelper.getGetSchemaIDAndSTTS(excel);

        excel.setExcelFile(excelPath, sheetName_InvalidData);
        excel.deleteColumnData(GlobalConstants.COL_SCHEMAID_MONGDOI);
        excel.deleteColumnData(GlobalConstants.COL_SCHEMAID_THUCTE);

        for (int j = 0; j < listSchemIDAndSTT.size(); j++) {
            String schemeID = listSchemIDAndSTT.get(j).getSchemaID();
            int targetValue = listSchemIDAndSTT.get(j).getStt();

            System.out.println("SCHEMA DÒNG " + (j+1) + ": " + schemeID);
            step("SCHEMA DÒNG  " + (j+1) + ": " + schemeID);
            List<Integer> positions = excel.findPositions(excel.indexColumByText("STT"), targetValue);
            if (!positions.isEmpty()) {
                //Call api theo từng data test sheet valid_data
                for (int i = positions.get(0); i <= positions.get(1); i++) {
                    if (schemeID.equals("FAIL TẠO SCHEMA")) {
                        excel.setCellData("FAIL TẠO SCHEMA", i, GlobalConstants.COL_KETQUA);
                    } else {
                        try {
                            String listIdMaKMFromAPI = apiHelper.getReponseKMFromAPI(excel, apiHelper, i, schemeID);
                            System.out.println("List schema from API: " + listIdMaKMFromAPI);
                            step("List schema from API: " + listIdMaKMFromAPI);
                            verifyTrue(!listIdMaKMFromAPI.contains(schemeID), "Check schema vừa tạo không nằm trong list api");
                            if (!listIdMaKMFromAPI.contains(schemeID)) {
                                excel.setCellData("", i, GlobalConstants.COL_SCHEMAID_THUCTE);
                                excel.setCellData("", i, GlobalConstants.COL_SCHEMAID_MONGDOI);
                                excel.setCellData("PASS", i, GlobalConstants.COL_KETQUA);
                            } else {
                                excel.setCellData(schemeID, i, GlobalConstants.COL_SCHEMAID_THUCTE);
                                excel.setCellData("FAIL", i, GlobalConstants.COL_KETQUA);
                            }
                        } catch (Exception e) {
                            verifyFalse(true, "Run row " + i + ": " + e.getMessage());
                        }
                    }
                }
            }else{
                verifyFalse(true, "Result get index sheet valid fail ");
            }
            System.out.println("");
        }
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }
}