package actions.pageobject;

import actions.pageobject.apdungtrensanpham.HTTangKemSPTheoBoiSoPO;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import org.openqa.selenium.WebDriver;

public class GeneratorManager {
    private WebDriver driver;

    public static KhuyenMaiPO getKhuyenMaiPage(WebDriver driver) {
    return new KhuyenMaiPO(driver);
    }

    public static HTTangKemSPTheoBoiSoPO getHTTangKemSPTheoBoiSoPage(WebDriver driver) {
    return new HTTangKemSPTheoBoiSoPO(driver);
    }
}
