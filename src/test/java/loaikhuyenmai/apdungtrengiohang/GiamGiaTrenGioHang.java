package loaikhuyenmai.apdungtrengiohang;

import actions.common.BaseTest;
import actions.helpers.ExcelHelper;
import actions.pageobject.GeneratorManager;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GiamGiaTrenGioHang extends BaseTest {
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;
    String[] kenhMuaHang = {"CONSUMER", "MERCHANT", "SALESMAN"};
    String textChung;
    String loaiKM="Áp dụng trên giỏ hàng";
    String hinhThucKM;
    String mucdoHienThi="1";
    String loaiCT="Chương trình bình thường";


//    @Test(priority = 1)
//    public void TC001_TaoKM_GiamGiaTrenGioHang(){
//        khuyenMaiPage.clickTaoMoiKhuyenMai("NPP trực thuộc NCC");
//        khuyenMaiPage.searchNhapMaNCC("CN0616399");
//        hinhThucKM="Giảm giá trên giỏ hàng";
//        khuyenMaiPage.taoThongTinCoBanKM(kenhMuaHang,textChung, loaiKM,hinhThucKM, mucdoHienThi,loaiCT,textChung);
//        khuyenMaiPage.taoNoiDungChuongTrinh(hinhThucKM);
//        khuyenMaiPage.thietLapMa("Mã cá nhân");
//    }
//    @Test(priority = 2)
//    public void TC002_TaoKM_GiamGiaTrenGioHang_NhieuDK(){
//        khuyenMaiPage.clickTaoMoiKhuyenMai("NPP trực thuộc NCC");
//        khuyenMaiPage.searchNhapMaNCC("CN0616399");
//        hinhThucKM="Giảm giá trên giỏ hàng";
//        khuyenMaiPage.taoThongTinCoBanKM(kenhMuaHang,textChung, loaiKM,hinhThucKM, mucdoHienThi,loaiCT);
//        khuyenMaiPage.themDieuKien("Tổng giá trị giỏ hàng");
//        khuyenMaiPage.themDieuKien("Thời gian đặt hàng");
//        khuyenMaiPage.themDieuKien("Phương thức thanh toán");
//        khuyenMaiPage.themNoiDung("Giảm giá theo phần trăm với số tiền tối đa");
//        khuyenMaiPage.thietLapMa("Mã công khai");
//    }


//    @Test(priority = 2)
//    public void TC002_TaoKM_HoanTienTrenGioHang() {
//        khuyenMaiPage.clickTaoMoiKhuyenMai("NPP trực thuộc NCC");
//        khuyenMaiPage.searchNhapMaNCC("CN0616399");
//        hinhThucKM="Hoàn tiền trên giỏ hàng";
//        khuyenMaiPage.taoThongTinCoBanKM(kenhMuaHang,textChung, loaiKM,hinhThucKM, mucdoHienThi,loaiCT,textChung);
//        khuyenMaiPage.taoNoiDungChuongTrinh(hinhThucKM);
//        khuyenMaiPage.thietLapMa("Mã công khai");
//    }

    @AfterClass(alwaysRun = true)
    public void quitDriver(){
        driver.quit();
    }
}
