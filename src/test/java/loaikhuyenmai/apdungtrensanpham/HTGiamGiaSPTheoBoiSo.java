package loaikhuyenmai.apdungtrensanpham;

import actions.common.BasePage;
import actions.common.BaseTest;
import actions.common.GlobalConstants;
import actions.helpers.ApiHelper;
import actions.helpers.ExcelHelper;
import actions.pageobject.GeneratorManager;
import actions.pageobject.loaikhuyenmai.ApDungSanPhamPO;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HTGiamGiaSPTheoBoiSo extends BaseTest {
    BasePage basePage = new BasePage();
    ExcelHelper excel;
    ;
    ApiHelper apiHelper;
    String excelPath = "src/main/resources/"+GlobalConstants.FILE_HTGiamGiaSPTHEOBOISO;
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;
    ApDungSanPhamPO httangKemSPTheoBoiSoPage;
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

                textChung = "Auto_KM_GiamGiaSPTheoBoiSo" + khuyenMaiPage.getDateTimeNow();
                khuyenMaiPage.taoThongTinCoBanKM(kenhMuaHang, textChung, loaiKM, hinhThucKM, mucdoHienThi, loaiCT);

                khuyenMaiPage.hinhThucApDungGoiKM(i, excel);
                khuyenMaiPage.themDieuKien("Danh sách sản phẩm của NPP", i, excel);
                httangKemSPTheoBoiSoPage.themDieuKien("Số lượng sản phẩm", i, excel);

                httangKemSPTheoBoiSoPage.themNoiDung("Giảm giá trên số lượng nhóm sản phẩm", i, excel);

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

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        driver.quit();
    }

}
