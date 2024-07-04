package actions.pageobject.loaikhuyenmai;
import actions.common.BasePage;
import actions.helpers.ExcelHelper;
import actions.pageobject.GeneratorManager;
import interfaces.khuyenmai.KhuyenMaiUI;

import org.openqa.selenium.WebDriver;

public class ApDungSanPhamPO extends BasePage {
    private WebDriver driver;
    KhuyenMaiPO khuyenMaiPage;


    public ApDungSanPhamPO(WebDriver driver) {
        this.driver = driver;
    }

    public void themNoiDung(String loaiNoiDung, int dong, ExcelHelper excel) {
        if (excel.isCellHasData(loaiNoiDung, dong)) {
            checkToDefaultCheckBox(driver, KhuyenMaiUI.RADIO_ND_DYM, loaiNoiDung);
            khuyenMaiPage = GeneratorManager.getKhuyenMaiPage(driver);
            switch (loaiNoiDung) {
                case "Tặng kèm sản phẩm cùng loại":
                    String soLuong_TangKemSPCungLoai = excel.getCellData("Số Lượng_TangKemSPCungLoai", dong);
                    khuyenMaiPage.sendKeyByNameLabel(soLuong_TangKemSPCungLoai, "Số lượng");
                    break;
                case "Tặng kèm sản phẩm trong danh sách":
                    String sku_NoiDung = excel.getCellData("SKU_SLKMTrenTungSP_TangKemSPTrongDS", dong);
                    String sl_ToiThieu = excel.getCellData("SLToiThieu_TangKemSPTrongDS", dong);
                    String sl_ToiDa = excel.getCellData("SLToiDa_TangKemSPTrongDS", dong);
                    String tongSLKM = excel.getCellData("TongSLKM_TangKemSPTrongDS", dong);
                    khuyenMaiPage.checkToDefaultCheckBox(driver, KhuyenMaiUI.RADIO_ND_DYM, "Tổng số lượng khuyến mãi");
                    khuyenMaiPage.themMoiNoiDungBangForm(sku_NoiDung, "SKU", "Tìm kiếm sản phẩm");
                    khuyenMaiPage.senkeyToElement(driver, KhuyenMaiUI.THEM_SL_NOIDUNG, sl_ToiThieu, "Nhập vào số lượng tối thiếu");
                    khuyenMaiPage.senkeyToElement(driver, KhuyenMaiUI.THEM_SL_NOIDUNG, sl_ToiDa, "Nhập vào số lượng tối đa");
                    khuyenMaiPage.senkeyToElement(driver, KhuyenMaiUI.THEM_SL_NOIDUNG, tongSLKM, "Nhập vào tổng số lượng khuyến mãi");
                    break;
                case "Giảm giá trên số lượng nhóm sản phẩm":
                    if(excel.isCellHasData("Nội dung_Giảm số tiền cố định",dong)){
                        String soTienGiam=excel.getCellData("Giá trị giảm_Giảm số tiền cố định",dong);
                        khuyenMaiPage.chonNoiDung("Giảm số tiền cố định");
                        khuyenMaiPage.sendKeyByNameLabel(soTienGiam, "Giá trị giảm (đ)");
                    }
                    if(excel.isCellHasData("Nội dung_Giảm giá sản phẩm theo hệ số",dong)){
                        String heSo=excel.getCellData("Giá trị giảm_Giảm số tiền cố định",dong);
                        khuyenMaiPage.chonNoiDung("Giảm giá sản phẩm theo hệ số");
                        khuyenMaiPage.sendKeyByNameLabel(heSo, "Hệ số");
                    }
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
                case "Tổng số lượng nhóm sản phẩm":
                    String phuongThuc_TongSLSP = excel.getCellData("", dong);
                    khuyenMaiPage.chonPhuongThuc("Lớn hơn");
                    khuyenMaiPage.sendKeyByNameLabel("2", "Tổng số lượng nhóm sản phẩm");
                    break;
                case "Số lượng sản phẩm":
                    String phuongThuc_SLSP = excel.getCellData("Phương thức_SLSP", dong);
                    String soLuongSP_SLSP = excel.getCellData("Số Lượng_SLSP", dong);
                    khuyenMaiPage.chonPhuongThuc(phuongThuc_SLSP);
                    khuyenMaiPage.sendKeyByNameLabel(soLuongSP_SLSP, "Số lượng sản phẩm");
                    break;
                case "Giá trị sản phẩm":
                    String phuongThuc_GTSP = excel.getCellData("Phương thức_GTSP", dong);
                    String giaTriSP_GTSP = excel.getCellData("Giá trị sản phẩm_GTSP", dong);
                    khuyenMaiPage.chonPhuongThuc(phuongThuc_GTSP);
                    khuyenMaiPage.sendKeyByNameLabel(giaTriSP_GTSP, "Giá trị sản phẩm");
                    break;

                case "Số lần đã mua hàng":
                    String phuongThuc_SoLanMH = excel.getCellData("Phương thức_SoLanMH", dong);
                    String soLanDaMuaHang = excel.getCellData("Số lần đã mua hàng_SoLanMH", dong);
                    khuyenMaiPage.chonPhuongThuc(phuongThuc_SoLanMH);
                    khuyenMaiPage.sendKeyByNameLabel(soLanDaMuaHang, "Số lần đã mua hàng");
                    break;
            }
            threadSecond(2);
        }
    }

    public void inputFieldsTangKemSPCungLoai(String soLuong, String soLuongToiDaTrenDon, String soLuongToiDaTrenNguoi, String soLuongToiDaTrenCTKM) {
        if (soLuong != "") {
            khuyenMaiPage.sendKeyByNameLabel(soLuong, "Số lượng");
        }
        if (soLuongToiDaTrenDon != "") {
            khuyenMaiPage.sendKeyByNameLabel(soLuongToiDaTrenDon, "Số lượng tối đa/đơn");
        }
        if (soLuongToiDaTrenNguoi != "") {
            khuyenMaiPage.sendKeyByNameLabel(soLuongToiDaTrenNguoi, "Số lượng tối đa/người");
        }
        if (soLuongToiDaTrenCTKM != "") {
            khuyenMaiPage.sendKeyByNameLabel(soLuongToiDaTrenCTKM, "Số lượng tối đa/CTKM");
        }
    }

}
