package actions.pageobject.apdungtrensanpham;
import actions.common.BasePage;
import actions.helpers.ExcelHelper;
import actions.pageobject.GeneratorManager;
import actions.pageobject.loaikhuyenmai.KhuyenMaiPO;
import interfaces.khuyenmai.KhuyenMaiUI;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import pojo.HowToLoad;
import pojo.Order;
import pojo.Orders;
import pojo.Product;

import java.util.ArrayList;
import java.util.List;

public class HTTangKemSPTheoBoiSoPO extends BasePage {
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;
    public HTTangKemSPTheoBoiSoPO(WebDriver driver) {
        this.driver = driver;
    }

    public void themNoiDungHTTangKemSPTheoBoiSo(String loaiNoiDung, int dong, ExcelHelper excel){
        if (excel.isCellHasData(loaiNoiDung,dong)) {
            checkToDefaultCheckBox(driver,KhuyenMaiUI.RADIO_ND_DYM,loaiNoiDung);
            khuyenMaiPage= GeneratorManager.getKhuyenMaiPage(driver);
            switch (loaiNoiDung) {
            case "Tặng kèm sản phẩm cùng loại":
                String soLuong_TangKemSPCungLoai=excel.getCellData("Số Lượng_TangKemSPCungLoai", dong);
                khuyenMaiPage.sendKeyByNameLabel(soLuong_TangKemSPCungLoai,"Số lượng");
                break;
            case "Tặng kèm sản phẩm trong danh sách":
                String sku_NoiDung=excel.getCellData("SKU_SLKMTrenTungSP_TangKemSPTrongDS",dong);
                String sl_ToiThieu=excel.getCellData("SLToiThieu_TangKemSPTrongDS",dong);
                String sl_ToiDa=excel.getCellData("SLToiDa_TangKemSPTrongDS",dong);
                String tongSLKM=excel.getCellData("TongSLKM_TangKemSPTrongDS",dong);
                khuyenMaiPage.checkToDefaultCheckBox(driver,KhuyenMaiUI.RADIO_ND_DYM, "Tổng số lượng khuyến mãi");
                khuyenMaiPage.themMoiNoiDungBangForm(sku_NoiDung,"SKU","Tìm kiếm sản phẩm");
                khuyenMaiPage.senkeyToElement(driver,KhuyenMaiUI.THEM_SL_NOIDUNG,sl_ToiThieu,"Nhập vào số lượng tối thiếu");
                khuyenMaiPage.senkeyToElement(driver,KhuyenMaiUI.THEM_SL_NOIDUNG,sl_ToiDa,"Nhập vào số lượng tối đa");
                khuyenMaiPage.senkeyToElement(driver,KhuyenMaiUI.THEM_SL_NOIDUNG,tongSLKM,"Nhập vào tổng số lượng khuyến mãi");
                break;
        }
        threadSecond(2);
    }
    }

    public void inputFieldsTangKemSPCungLoai(String soLuong, String soLuongToiDaTrenDon, String soLuongToiDaTrenNguoi, String soLuongToiDaTrenCTKM){
        if(soLuong!=""){
            khuyenMaiPage.sendKeyByNameLabel(soLuong,"Số lượng");
        }
        if(soLuongToiDaTrenDon!=""){
            khuyenMaiPage.sendKeyByNameLabel(soLuongToiDaTrenDon,"Số lượng tối đa/đơn");
        }
        if(soLuongToiDaTrenNguoi!=""){
            khuyenMaiPage.sendKeyByNameLabel(soLuongToiDaTrenNguoi,"Số lượng tối đa/người");
        }
        if(soLuongToiDaTrenCTKM!=""){
            khuyenMaiPage.sendKeyByNameLabel(soLuongToiDaTrenCTKM,"Số lượng tối đa/CTKM");
        }
    }

    public Orders getOrders(String sku, int quantity, String company_id, String agent_phone) {
        HowToLoad howToLoadPojo = new HowToLoad(true, "VALID_PROMOTION", "SELF_PICK", false, 0);
        Product proDct = new Product(sku, 11111, quantity, howToLoadPojo);
        List<Product> products = new ArrayList<>();
        Order orderPojo = new Order(products, company_id, "CodChannel", 66666, howToLoadPojo);

        products.add(proDct);
        List<Order> orders = new ArrayList<>();
        orders.add(orderPojo);

        Orders orderList = new Orders(
                orders,
                agent_phone,
                null,
                "ecomv2",
                "CodChannel",
                66666,
                howToLoadPojo,
                "",
                "",
                false,
                false
        );
        return orderList;
    }

}
