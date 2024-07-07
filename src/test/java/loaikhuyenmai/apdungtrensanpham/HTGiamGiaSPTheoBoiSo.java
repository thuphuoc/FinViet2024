package loaikhuyenmai.apdungtrensanpham;

import actions.common.BasePage;
import actions.common.BaseTest;
import actions.common.GlobalConstants;
import actions.helpers.ApiHelper;
import actions.helpers.ExcelHelper;
import actions.pageobject.GeneratorManager;
import actions.pageobject.loaikhuyenmai.ApDungSanPhamPO;
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

public class HTGiamGiaSPTheoBoiSo extends BaseTest {
    BasePage basePage = new BasePage();
    ExcelHelper excel;
    ;
    ApiHelper apiHelper;
    String excelPath = "src/main/resources/"+GlobalConstants.FILE_HTGiamGiaSPTHEOBOISO;
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;
    ApDungSanPhamPO apDungTrenSanPhamPage;
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
        apDungTrenSanPhamPage = GeneratorManager.getHTTangKemSPTheoBoiSoPage(driver);
        step("Mở trang: " + GlobalConstants.URL);
        khuyenMaiPage.openPageUrl(driver, GlobalConstants.URL);
        step("Login với username: " + GlobalConstants.USERNAME + "|| password: " + GlobalConstants.PASSWORD);
        khuyenMaiPage.loginPromotion(driver, GlobalConstants.USERNAME, GlobalConstants.PASSWORD);
    }

    @Test(priority = 1)
    public void TC001_GiamGiaSPTheoBoiSo_TaoKM() {
        excel = new ExcelHelper();
        excel.setExcelFile(excelPath, GlobalConstants.SHEET_HTGiamGiaSPTHEOBOISO);
        excel.deleteColumnData("SchemaID");
        excel.deleteColumnData("SchemaName");
        int soDongExcelCoData = excel.countRowsHasData();
        for (int i = 1; i < soDongExcelCoData; i++) {
            try {
                khuyenMaiPage.openPageUrl(driver, GlobalConstants.URL_PROMTION);
                threadSecond(3);
                System.out.println("Run row: " + i);
                step("Tạo khuyến mãi thứ " + i);
                hinhThucKM = "Giảm giá sản phẩm theo bội số";
                khuyenMaiPage.clickTaoMoiKhuyenMai("NPP");
                khuyenMaiPage.searchForm("CN0617895","Mã NPP");

                textChung = "Auto_KM_GiamGiaSPTheoBoiSo_" + khuyenMaiPage.getDateTimeNow();
                khuyenMaiPage.taoThongTinCoBanKM(kenhMuaHang, textChung, loaiKM, hinhThucKM, mucdoHienThi, loaiCT);

                khuyenMaiPage.hinhThucApDungGoiKM(i, excel);
                khuyenMaiPage.themDieuKien("Danh sách sản phẩm của NPP", i, excel);
                apDungTrenSanPhamPage.themDieuKien("Số lượng sản phẩm", i, excel);

                apDungTrenSanPhamPage.themNoiDung("Giảm giá trên số lượng nhóm sản phẩm", i, excel);

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
        excel = new ExcelHelper();
        apiHelper = new ApiHelper();
        excel.setExcelFile(excelPath, GlobalConstants.SHEET_HTGiamGiaSPTHEOBOISO);
        List<GetSchemaIDAndSTT> listSchemIDAndSTT = apiHelper.getGetSchemaIDAndSTTS(excel);

        excel.setExcelFile(excelPath, GlobalConstants.SHEET_VALID_DATA);
        excel.deleteColumnData(GlobalConstants.COL_SCHEMAID_MONGDOI);
        excel.deleteColumnData(GlobalConstants.COL_SCHEMAID_THUCTE);
        excel.deleteColumnData(GlobalConstants.COL_GIASAUKM_THUCTE);

        for (int j = 0; j < listSchemIDAndSTT.size(); j++) {
            String schemeID = listSchemIDAndSTT.get(j).getSchemaID();
            int targetValue = listSchemIDAndSTT.get(j).getStt();
            String nameSchemaInSheet = listSchemIDAndSTT.get(j).getName();

            System.out.println("SCHEMA DÒNG " + (j + 1) + ": " + schemeID);
            step("SCHEMA DÒNG  " + (j + 1) + ": " + schemeID);
            List<Integer> positions = excel.findPositions(excel.indexColumByText("STT"), targetValue);
            if (!positions.isEmpty()) {
                //Call api theo từng data test sheet valid_data
                for (int i = positions.get(0); i <= positions.get(1); i++) {
                    if (schemeID.equals("FAIL TẠO SCHEMA")) {
                        excel.setCellData("FAIL TẠO SCHEMA", i, GlobalConstants.COL_KETQUA);
                    } else {
                        try {
                            String rspFromAPI = apiHelper.getReponseFromAPIEcom(excel, apiHelper, i);
                            JSONObject jsonObjectRsp = new JSONObject(rspFromAPI);
                            String quantity_purchase = jsonObjectRsp.getJSONObject("data").get("quantity_purchase").toString();
                            String total_amount_cart = jsonObjectRsp.getJSONObject("data").get("total_amount").toString();
                            String special_price = apiHelper.gotoProduct_variant(jsonObjectRsp).get("special_price").toString();
                            Integer base_price = Integer.parseInt (apiHelper.gotoProduct_variant(jsonObjectRsp).get("base_price").toString());

                            System.out.println("Tổng SL sp: " + quantity_purchase);
                            System.out.println("Tổng tiền hàng: " + total_amount_cart);
                            System.out.println("Giá sp sau KM: " + special_price);
                            int sizePromtionList = apiHelper.gotoPromotionList(jsonObjectRsp).length();

                            if (sizePromtionList != 0) {
                                System.out.println("Có "+sizePromtionList +" promotion cho sp này");
                                String schemaNameAPI = "";
                                for (int k = 0; k < sizePromtionList; k++) {
                                    String operator= apiHelper.gotoPromotionList(jsonObjectRsp).getJSONObject(k).get("operator").toString();
                                    if(operator.equals("discountFixedAmount")) {
                                        schemaNameAPI = apiHelper.gotoPromotionList(jsonObjectRsp).getJSONObject(k).get("promotion_id").toString();
                                        String promtion_list_name = apiHelper.gotoPromotionList(jsonObjectRsp).getJSONObject(k).get("promotion_name").toString();
                                        Integer from_quantity = Integer.parseInt(apiHelper.gotoDiscountAction(jsonObjectRsp, k).get("from_quantity").toString());
                                        Integer discount_value =  Integer.parseInt(apiHelper.gotoDiscountAction(jsonObjectRsp, k).get("discount_value").toString());

                                        System.out.println("Thông tin KM " + k + ": " + schemaNameAPI + " || " + promtion_list_name + " || Mua SL " + from_quantity + "|| discount_value " + discount_value);
                                        break;
                                        //  Giá = (Giá * SL mua - $discount_value) / SL mua;
//                                        int GiaSauKMCongThuc=(base_price*quanity-discount_value)/quanity;
//                                        verifyTrue(GiaSauKMCongThuc==special_price,"Check giá KM CT "+GiaSauKMCongThuc+" || Thực tế: "+special_price);
                                    }
                                }

                                if (schemaNameAPI.equals(schemeID)) {
                                    excel.setCellData(schemeID, i, GlobalConstants.COL_SCHEMAID_MONGDOI);
                                    excel.setCellData(schemeID, i, GlobalConstants.COL_SCHEMAID_THUCTE);
                                    excel.setCellData(nameSchemaInSheet, i, GlobalConstants.COL_NAME_MONGDOI);
//                                    excel.setCellData(schemaNameAPI, i, GlobalConstants.COL_NAME_THUCTE);
                                    excel.setCellData(special_price, i, GlobalConstants.COL_GIASAUKM_THUCTE);
                                    String giaSauKMMongDoi = excel.getCellData(GlobalConstants.COL_GIASAUKM_MONGDOI, i).toString();
                                    verifyTrue(special_price.equals(giaSauKMMongDoi), "Check giá sau km thực tế: " + special_price + " || mong đợi: " + giaSauKMMongDoi);
                                    verifyTrue(nameSchemaInSheet.equals(schemaNameAPI), "Check name thực tế: " + schemaNameAPI + " || mong đợi: " + nameSchemaInSheet);
                                    if (special_price.equals(giaSauKMMongDoi) && nameSchemaInSheet.equals(schemaNameAPI)) {
                                        excel.setCellData("PASS", i, GlobalConstants.COL_KETQUA);
                                    } else {
                                        excel.setCellData("FAIL", i, GlobalConstants.COL_KETQUA);
                                    }
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
            } else {
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
