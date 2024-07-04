package actions.pageobject;

import actions.pageobject.loaikhuyenmai.ApDungSanPhamPO;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import org.openqa.selenium.WebDriver;

public class GeneratorManager {
    private WebDriver driver;

    public static KhuyenMaiPO getKhuyenMaiPage(WebDriver driver) {
    return new KhuyenMaiPO(driver);
    }

    public static ApDungSanPhamPO getHTTangKemSPTheoBoiSoPage(WebDriver driver) {
    return new ApDungSanPhamPO(driver);
    }
}
