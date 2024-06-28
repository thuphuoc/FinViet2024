package loaikhuyenmai.apdungtrensanpham;

import actions.common.GlobalConstants;
import actions.helpers.ApiHelper;
import actions.helpers.ExcelHelper;
import actions.common.BaseTest;
import actions.pageobject.GeneratorManager;
import actions.pageobject.apdungtrensanpham.HTTangKemSPTheoBoiSoPO;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.GetSchemaIDAndSTT;
import pojo.Orders;

import java.util.List;

public class HTTangKemSPTheoBoiSo extends BaseTest {
    ExcelHelper excel ;
    ApiHelper apiHelper;
    String excelPath = "src/main/resources/Data_KhuyenMai.xlsx";
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
        excel = new ExcelHelper();
        apiHelper= new ApiHelper();
        excel.setExcelFile(excelPath, "TangKemSPTheoBoiSo");
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
        excel.deleteColumnData("SchemaID");
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
                khuyenMaiPage.themDieuKien("Số lượng sản phẩm", i, excel);
                khuyenMaiPage.themDieuKien("Giá trị sản phẩm", i, excel);
                khuyenMaiPage.themDieuKien("Số lần đã mua hàng", i, excel);

                httangKemSPTheoBoiSoPage.themNoiDungHTTangKemSPTheoBoiSo("Tặng kèm sản phẩm cùng loại", i, excel);
                httangKemSPTheoBoiSoPage.themNoiDungHTTangKemSPTheoBoiSo("Tặng kèm sản phẩm trong danh sách", i, excel);

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
                if (i + 1 == soDongExcelCoData) {
                    khuyenMaiPage.waitDangDienRa(maCTKM_TaoScheme);
                }
            } catch (Exception e) {
                excel.setCellData("FAIL TẠO SCHEMA", i, "SchemaID");
                verifyFalse(true, "Run row " + i + ": " + e.getMessage());
                System.err.println("Run row " + i + ": " + e.getMessage());
            }
        }
    }

    @Test(priority = 2)
    public void TC002_CallApi_Valid_Data() {
        excel.setExcelFile(excelPath, sheetName_TangKemSPTheoBoiSo);
        List<GetSchemaIDAndSTT> listSchemIDAndSTT = httangKemSPTheoBoiSoPage.getGetSchemaIDAndSTTS(excel);
        
        excel.setExcelFile(excelPath, sheetName_ValidData);
        excel.deleteColumnData("Mong đợi");
        excel.deleteColumnData("Thực tế");

        for (int j = 0; j < listSchemIDAndSTT.size(); j++) {
            String schemeID = listSchemIDAndSTT.get(j).getSchemaID();
            int targetValue = listSchemIDAndSTT.get(j).getStt();

            System.out.println("SCHEMA DÒNG " + (j+1) + ": " + schemeID);
            step("SCHEMA DÒNG  " + (j +1)+ ": " + schemeID);
            List<Integer> positions = excel.findPositions(excel.indexColumByText("STT"), targetValue);
            if (!positions.isEmpty()) {
                //Call api theo từng data test sheet valid_data
                for (int i = positions.get(0); i <= positions.get(1); i++) {
                    if (schemeID.equals("FAIL TẠO SCHEMA")) {
                        excel.setCellData("FAIL TẠO SCHEMA", i, "Kết quả");
                    } else {
                        try {
                            String listIdMaKMFromAPI = httangKemSPTheoBoiSoPage.getIdKMFromAPI(excel, apiHelper, i, schemeID);
                            System.out.println("List schema from API: " + listIdMaKMFromAPI);
                            step("List schema from API: " + listIdMaKMFromAPI);
                            verifyTrue(listIdMaKMFromAPI.contains(schemeID), "Check schema vừa tạo có trong list API");
                            if (listIdMaKMFromAPI.contains(schemeID)) {
                                excel.setCellData(schemeID, i, "Thực tế");
                                excel.setCellData(schemeID, i, "Mong đợi");
                                excel.setCellData("PASS", i, "Kết quả");
                            } else {
                                excel.setCellData(schemeID, i, "Mong đợi");
                                excel.setCellData("FAIL", i, "Kết quả");
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

    @Test(priority = 3)
    public void TC003_CallApi_InValid_Data() {
        excel.setExcelFile(excelPath, sheetName_TangKemSPTheoBoiSo);
        List<GetSchemaIDAndSTT> listSchemIDAndSTT = httangKemSPTheoBoiSoPage.getGetSchemaIDAndSTTS(excel);

        excel.setExcelFile(excelPath, sheetName_InvalidData);
        excel.deleteColumnData("Mong đợi");
        excel.deleteColumnData("Thực tế");

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
                        excel.setCellData("FAIL TẠO SCHEMA", i, "Kết quả");
                    } else {
                        try {
                            String listIdMaKMFromAPI = httangKemSPTheoBoiSoPage.getIdKMFromAPI(excel, apiHelper, i, schemeID);
                            System.out.println("List schema from API: " + listIdMaKMFromAPI);
                            step("List schema from API: " + listIdMaKMFromAPI);
                            verifyTrue(!listIdMaKMFromAPI.contains(schemeID), "Check schema vừa tạo không nằm trong list api");
                            if (!listIdMaKMFromAPI.contains(schemeID)) {
                                excel.setCellData("", i, "Thực tế");
                                excel.setCellData("", i, "Mong đợi");
                                excel.setCellData("PASS", i, "Kết quả");
                            } else {
                                excel.setCellData(schemeID, i, "Thực tế");
                                excel.setCellData("FAIL", i, "Kết quả");
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

//    @Test(priority = 4)
    public void TC004_CallApi_Invalid_Data() {
        excel.setExcelFile(excelPath, sheetName_InvalidData);
        excel.deleteColumnData("Mong đợi");
        excel.deleteColumnData("Thực tế");
        excel.setExcelFile(excelPath, sheetName_TangKemSPTheoBoiSo);
        String schemeID = "";

        int soLuongDong = excel.countRowsHasData();
        for (int j = 1; j < soLuongDong; j++) {
            excel.setExcelFile(excelPath, sheetName_TangKemSPTheoBoiSo);
            schemeID = excel.getCellData("SchemaID", j);
            System.out.println("SCHEMA THỨ " + j + ": " + schemeID);
            String STT_Sheet_TangKemSPTheoBoiSo = excel.getCellData("STT", j);

            //Kiểm tra nếu có schemaID thì mới post data
//            if (excel.isCellHasData("SchemaID", j)) {
                //Sheet InValid data test
                excel.setExcelFile(excelPath, sheetName_InvalidData);
                int targetValue = Integer.parseInt(STT_Sheet_TangKemSPTheoBoiSo);

                //Lấy vị trí các STT mapping với bên sheet TangKemSPTheoBoiSo
                List<Integer> positions = excel.findPositions(excel.indexColumByText("STT"), targetValue);
                int start = 0, end = 0;
                if (!positions.isEmpty()) {
                    for (int i = 0; i < positions.size(); i += 2) {
                        start = positions.get(i);
                        end = positions.get(i + 1);
                    }
                }

                //Call api theo từng data test sheet valid_data
                for (int i = start; i <= end; i++) {
                    if (schemeID.equals("FAIL TẠO SCHEMA")) {
                        excel.setCellData("FAIL TẠO SCHEMA", i, "Kết quả");
                    }else {
                        try {
                            String sku = excel.getCellData("sku", i);
                            int quantity = Integer.parseInt(excel.getCellData("quantity", i));
                            String company_id = excel.getCellData("company_id", i);
                            String agent_phone = excel.getCellData("agent_phone", i);
                            String data = "Data invalid dòng " + i + " của schema: " + schemeID + "|| " + sku + "||" + quantity + "||" + company_id + "||" + agent_phone;
                            System.out.println(data);
                            step(data);
                            Orders orderList = httangKemSPTheoBoiSoPage.getOrders(sku, quantity, company_id, agent_phone);

                            Response rsp = apiHelper.postRequestJson(orderList, GlobalConstants.URL_API);
                            if (rsp.statusCode() == 200) {
                                String idMaKM = apiHelper.getReponse(rsp, "promotions_allow_apply.id");
                                System.out.println("List schema from API: " + idMaKM);
                                verifyTrue(!idMaKM.contains(schemeID), "Check schema vừa tạo không nằm trong list api");
                                if (!idMaKM.contains(schemeID)) {
                                    excel.setCellData("", i, "Thực tế");
                                    excel.setCellData("", i, "Mong đợi");
                                    excel.setCellData("PASS", i, "Kết quả");
                                } else {
                                    excel.setCellData(schemeID, i, "Thực tế");
                                    excel.setCellData("FAIL", i, "Kết quả");
                                }
                            }
                        } catch (Exception e) {
                            verifyFalse(true, "Run row " + i + ": " + e.getMessage());
                            System.err.println("Run row: " + i + " " + e.getMessage());
                        }
                    }
                }
            }
//        }
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }
}