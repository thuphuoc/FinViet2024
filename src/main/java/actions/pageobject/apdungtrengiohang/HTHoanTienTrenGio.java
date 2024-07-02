package actions.pageobject.apdungtrengiohang;

import actions.common.BasePage;
import actions.helpers.ExcelHelper;
import actions.pageobject.GeneratorManager;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import interfaces.khuyenmai.KhuyenMaiUI;
import org.openqa.selenium.WebDriver;

public class HTHoanTienTrenGio extends BasePage {
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;


    public HTHoanTienTrenGio(WebDriver driver) {
        this.driver = driver;
    }

    public void themNoiDung(String loaiNoiDung, int dong, ExcelHelper excel) {
        if (excel.isCellHasData(loaiNoiDung, dong)) {
            checkToDefaultCheckBox(driver, KhuyenMaiUI.RADIO_ND_DYM, loaiNoiDung);
            khuyenMaiPage = GeneratorManager.getKhuyenMaiPage(driver);
            switch (loaiNoiDung) {
                case "Hoàn tiền theo phần trăm":
                    String giamGiaPhanTram="";
                    khuyenMaiPage.sendKeyByNameLabel(giamGiaPhanTram,"Giá trị giảm (đ)");
                    break;
                case "Hoàn tiền số tiền cố định":
                    String giamGiaCoDinh="";
                    khuyenMaiPage.sendKeyByNameLabel(giamGiaCoDinh,"Giá trị giảm (%)");
                    break;
                case "Hoàn tiền theo phần trăm với số tiền tối đa":
                    String giamGiaPhanTramToiDa="";
                    String giamGiaToiDa="";
                    khuyenMaiPage.sendKeyByNameLabel(giamGiaPhanTramToiDa,"Giá trị giảm (%)");
                    khuyenMaiPage.sendKeyByNameLabel(giamGiaToiDa,"Tối đa (đ)");
                    break;
            }

            threadSecond(2);
        }
    }

    public void themDieuKien(String loaiDieuKien, int dong, ExcelHelper excel) {
        khuyenMaiPage = GeneratorManager.getKhuyenMaiPage(driver);
        if (excel.isCellHasData(loaiDieuKien, dong)) {
            clickToElement(driver, KhuyenMaiUI.THEMDK_THEONHOM_BTN, "Điều kiện áp dụng", "Thêm điều kiện");
            threadSecond(1);
            khuyenMaiPage.chonDieuKien(loaiDieuKien);
            switch (loaiDieuKien) {
                case "Thời gian đặt hàng":
                    khuyenMaiPage.chonPhuongThuc("Bao gồm");
                    selectItemCustomDropDown(driver, KhuyenMaiUI.PLACEHOlDER_PARENT, KhuyenMaiUI.CHILD_TGDAT, "Ngày trong tuần", "Vui lòng chọn");
                    checkToDefaultCheckBox(driver, KhuyenMaiUI.RADIO_TIME_DYM, "Thứ hai");
                    break;
                case "Tổng giá trị giỏ hàng":
                    khuyenMaiPage.chonPhuongThuc("Lớn hơn hoặc bằng");
                    khuyenMaiPage.sendKeyByNameLabel("10000", "Tổng số tiền trên giỏ");
                    break;
            }
            threadSecond(2);
        }
    }


}
