package actions.pageobject.loaikhuyenmai;

import actions.common.BasePage;
import actions.helpers.ExcelHelper;
import interfaces.khuyenmai.KhuyenMaiUI;
import org.openqa.selenium.WebDriver;

public class KhuyenMaiPO extends BasePage {
    private WebDriver driver;
    ExcelHelper excel;

    public KhuyenMaiPO(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTaoMoiKhuyenMai(String tenKhuyenMai) {
        clickToElement(driver, KhuyenMaiUI.BTN_DSCTKM_DYM, "Tạo mới");
        clickToElement(driver, KhuyenMaiUI.TAOMOI_KM_DYM, tenKhuyenMai);
    }

    public void searchForm(String textSearch, String labelSearch) {
        senkeyToElement(driver, KhuyenMaiUI.SEARCH_DYM, textSearch, labelSearch);
        clickToElement(driver, KhuyenMaiUI.TIMKIEM_NCC_DMY, "Tìm kiếm");
        threadSecond(1);
        int sizeRadio=getElementSize(driver, KhuyenMaiUI.CHONNCC_CHECKBOX);
        int demClickTimKiem = 0;
        while (sizeRadio != 1 && demClickTimKiem < 5) {
            demClickTimKiem++;
            clickToElement(driver, KhuyenMaiUI.TIMKIEM_NCC_DMY, "Tìm kiếm");
            threadSecond(5);
            sizeRadio=getElementSize(driver, KhuyenMaiUI.CHONNCC_CHECKBOX);
        }
        clickToElement(driver, KhuyenMaiUI.CHONNCC_CHECKBOX);
        clickToElement(driver, KhuyenMaiUI.BTN_DSCTKM_DYM, "Đồng ý");
    }

    public void taoThongTinCoBanKM(String[] kenhMuaHang, String textChung, String loaiKM, String hinhThucKM, String mucdoHienThi, String loaiCT) {
        for (int i = 0; i < kenhMuaHang.length; i++) {
            clickToElement(driver, KhuyenMaiUI.KENHMUAHANG_CHECKBOX_DYM, kenhMuaHang[i]);
        }
        senkeyToElement(driver, KhuyenMaiUI.AREA_DYM, textChung, "Nhập tên hiển thị trên portal");
        senkeyToElement(driver, KhuyenMaiUI.AREA_DYM, textChung, "Nhập tên hiển thị trên app");

        clickToElement(driver, KhuyenMaiUI.NGAYBATDAU);
        removeAttributeInDOM(driver, KhuyenMaiUI.NGAYBATDAU, "readonly");
        senkeyToElement(driver, KhuyenMaiUI.NGAYBATDAU, getDateTimeNow());
        removeAttributeInDOM(driver, KhuyenMaiUI.NGAYKETTHUC, "readonly");
        senkeyToElement(driver, KhuyenMaiUI.NGAYKETTHUC, getDateTomorrow());
        threadSecond(1);
        pressEnter(driver);

        selectItemCustomDropDown(driver, KhuyenMaiUI.LOAIKM_DYM, KhuyenMaiUI.CHILD_LOAIKM, loaiKM, "Loại khuyến mãi");
        selectItemCustomDropDown(driver, KhuyenMaiUI.LOAIKM_DYM, KhuyenMaiUI.CHILD_HTKM, hinhThucKM, "Hình thức khuyến mãi");
        senkeyToElement(driver, KhuyenMaiUI.MUCDOUUTIENCUACT_TXT, mucdoHienThi);
        selectItemCustomDropDown(driver, KhuyenMaiUI.LOAICT, KhuyenMaiUI.CHILD_LOAICT, loaiCT);
        senkeyToElement(driver, KhuyenMaiUI.AREA_DYM, textChung, "Nhập nội dung");
        senkeyToElement(driver, KhuyenMaiUI.AREA_DYM, textChung, "Nhập mô tả");
        senkeyToElement(driver, KhuyenMaiUI.THELECHUONGTRINH_TXT, textChung);

        String valueNgayBatDau=getElementAtribute(driver,KhuyenMaiUI.NGAYBATDAU,"value");
        int error=getElementSize(driver,KhuyenMaiUI.ERROR);
        while(valueNgayBatDau.isEmpty() || error!=0){
            System.out.println("valueNgayBatDau: "+valueNgayBatDau);
            removeAttributeInDOM(driver, KhuyenMaiUI.NGAYBATDAU, "readonly");
            senkeyToElement(driver, KhuyenMaiUI.NGAYBATDAU, getDateTimeNow());
            threadSecond(1);
            removeAttributeInDOM(driver, KhuyenMaiUI.NGAYKETTHUC, "readonly");
            senkeyToElement(driver, KhuyenMaiUI.NGAYKETTHUC, getDateTomorrow());
            threadSecond(1);
            pressEnter(driver);
            valueNgayBatDau=getElementAtribute(driver,KhuyenMaiUI.NGAYBATDAU,"value");
            getElementSize(driver,KhuyenMaiUI.ERROR);
        }
        threadSecond(1);
        clickToElement(driver, KhuyenMaiUI.TIEPTUC_BTN);
    }

    public void themMoiDieuKienBangForm(String loaiDieuKien, String textSearch, String typeSearch, String formSearch) {
        clickToElement(driver, KhuyenMaiUI.BTN_DK_DYM, loaiDieuKien, "Thêm mới");
        senkeyToElement(driver, KhuyenMaiUI.SEARCH_LABEL_DYM, textSearch, typeSearch);
        threadSecond(1);
        clickToElement(driver, KhuyenMaiUI.BTN_FORM_DYM, formSearch, "Tìm kiếm");
        threadSecond(2);
        int sizeRadio = getListElement(driver, KhuyenMaiUI.CHECKBOX_FORM_DYM, formSearch).size();
        int demClickTimKiem = 0;
        while (sizeRadio != 1 && demClickTimKiem < 5) {
            demClickTimKiem++;
            clickToElement(driver, KhuyenMaiUI.BTN_FORM_DYM, formSearch, "Tìm kiếm");
            threadSecond(5);
            sizeRadio = getListElement(driver, KhuyenMaiUI.CHECKBOX_FORM_DYM, formSearch).size();
        }
        checkToDefaultCheckBox(driver, KhuyenMaiUI.CHECKBOX_FORM_DYM, formSearch);
        threadSecond(1);
        clickToElement(driver, KhuyenMaiUI.BTN_FORM_DYM, formSearch, "Đồng ý");
    }

    public void themMoiNoiDungBangForm(String textSearch, String typeSearch, String formSearch) {
        clickToElement(driver, KhuyenMaiUI.THEMMOI_NOIDUNG_BTN);
        senkeyToElement(driver, KhuyenMaiUI.SEARCH_LABEL_DYM, textSearch, typeSearch);
        threadSecond(1);
        clickToElement(driver, KhuyenMaiUI.BTN_FORM_DYM, formSearch, "Tìm kiếm");
        threadSecond(2);
        int sizeRadio = getListElement(driver, KhuyenMaiUI.CHECKBOX_FORM_DYM, formSearch).size();
        int demClickTimKiem = 0;
        while (sizeRadio != 1 && demClickTimKiem < 5) {
            demClickTimKiem++;
            clickToElement(driver, KhuyenMaiUI.BTN_FORM_DYM, formSearch, "Tìm kiếm");
            threadSecond(5);
            sizeRadio = getListElement(driver, KhuyenMaiUI.CHECKBOX_FORM_DYM, formSearch).size();
        }
        checkToDefaultCheckBox(driver, KhuyenMaiUI.CHECKBOX_FORM_DYM, formSearch);
        threadSecond(1);
        clickToElement(driver, KhuyenMaiUI.BTN_FORM_DYM, formSearch, "Đồng ý");
    }

    public void clickToBtnByText(String nameButton) {
        clickToElement(driver, KhuyenMaiUI.BTN_NAME_DYM, nameButton);
    }

    public void chonDieuKien(String loaiDieuKien) {
        selectItemCustomDropDown(driver, KhuyenMaiUI.PLACEHOlDER_PARENT, KhuyenMaiUI.CHILD_LOAIDK, loaiDieuKien, "Vui lòng chọn điều kiện");
    }

    public void chonDoiTuong(String loaiDoiTuong) {
        selectItemCustomDropDown(driver, KhuyenMaiUI.PLACEHOlDER_PARENT, KhuyenMaiUI.CHILD_LOAIDOITUONG, loaiDoiTuong, "Vui lòng chọn đối tượng");
    }

    public void chonPhuongThuc(String loaiPhuongThuc) {
        selectItemCustomDropDown(driver, KhuyenMaiUI.PLACEHOlDER_PARENT, KhuyenMaiUI.CHILD_PHUONGTHUC, loaiPhuongThuc, "Phương thức");
    }

    public void chonNoiDung(String loaiNoiDung) {
        selectItemCustomDropDown(driver, KhuyenMaiUI.DROPDOWN_PARENT_DYM, KhuyenMaiUI.CHILD_NOIDUNG, loaiNoiDung, "Nội dung khuyến mãi");
    }

    public void chonKieuDinhDangMa(String LoaiDinhDangMa) {
        selectItemCustomDropDown(driver, KhuyenMaiUI.DROPDOWN_PARENT_DYM, KhuyenMaiUI.CHILD_KIEUDDMA, LoaiDinhDangMa, "Kiểu định dạng của mã");
    }

    public void sendKeyByNameLabel(String textSendkey, String nameLabel) {
        senkeyToElement(driver, KhuyenMaiUI.INPUT_THEO_LABEL_DYM, textSendkey, nameLabel);
    }


    public void hinhThucApDungGoiKM(int dong, ExcelHelper excel) {
        threadSecond(2);
        if (excel.isCellHasData("Hình thức áp dụng gói khuyến mãi", dong)) {
            String hinhThucADGoiKM = excel.getCellData("Hình thức áp dụng gói khuyến mãi", dong);
            selectItemCustomDropDown(driver, KhuyenMaiUI.DROPDOWN_PARENT_DYM, KhuyenMaiUI.CHILD_HTADCTKM_DYM, hinhThucADGoiKM, "Hình thức áp dụng gói khuyến mãi");
        }
    }

    public void themDieuKien(String loaiDieuKien, int dong, ExcelHelper excel) {
        if (excel.isCellHasData(loaiDieuKien, dong)) {
            clickToElement(driver, KhuyenMaiUI.THEMDK_THEONHOM_BTN, "Điều kiện áp dụng", "Thêm điều kiện");
            threadSecond(1);
            chonDieuKien(loaiDieuKien);
            switch (loaiDieuKien) {
                case "Tổng số lượng nhóm sản phẩm":
                    String phuongThuc_TongSLSP = excel.getCellData("", dong);
                    chonPhuongThuc("Lớn hơn");
                    sendKeyByNameLabel("2", "Tổng số lượng nhóm sản phẩm");
                    break;
                case "Phương thức thanh toán":
                    String phuongThuc_PTTT = excel.getCellData("Phương thức_PTTT", dong);
                    String phuongThucThanhToan_PTTT = excel.getCellData("Phương thức thanh toán_PTTT", dong);
                    chonPhuongThuc(phuongThuc_PTTT);
                    selectItemCustomDropDown(driver, KhuyenMaiUI.PLACEHOlDER_PARENT, KhuyenMaiUI.CHILD_PTTT, phuongThucThanhToan_PTTT, "Vui lòng chọn");
                    break;
                case "Danh sách sản phẩm":
                    String phuongThuc_DSSP = excel.getCellData("Phương thức_DSSP", dong);
                    String sku = excel.getCellData("SKU_DSSP", dong);
                    chonPhuongThuc(phuongThuc_DSSP);
                    for(int i =0 ; i< getArrayAfterPhanTachDauPhay(sku).length; i++){
                        String skuItem=getArrayAfterPhanTachDauPhay(sku)[i];
                        themMoiDieuKienBangForm(loaiDieuKien, skuItem, "SKU", "Tìm kiếm sản phẩm");
                    }
                    break;
                case " Danh sách NPP":
                    String phuongThuc_DSNPP = excel.getCellData("Phương thức_DSNPP", dong);
                    chonPhuongThuc(phuongThuc_DSNPP);
                    String MaNPP_DSNPP = excel.getCellData("MaNPP_DSNPP", dong);
                    for(int i =0 ; i< getArrayAfterPhanTachDauPhay(MaNPP_DSNPP).length; i++){
                        String maNPPItem=getArrayAfterPhanTachDauPhay(MaNPP_DSNPP)[i];
                        themMoiDieuKienBangForm(loaiDieuKien, maNPPItem, "Mã NPP", "Tìm kiếm Nhà phân phối");
                    }
                    break;
                case "Số lần đã mua hàng":
                    String phuongThuc_SoLanMH = excel.getCellData("Phương thức_SoLanMH", dong);
                    String soLanDaMuaHang = excel.getCellData("Số lần đã mua hàng_SoLanMH", dong);
                    chonPhuongThuc(phuongThuc_SoLanMH);
                    sendKeyByNameLabel(soLanDaMuaHang, "Số lần đã mua hàng");
                    break;
                case "Danh sách sản phẩm của NPP":
                    String sku_DSSP_NPP=excel.getCellData("SKU_DSSP_NPP", dong);
                    String phuongThuc=excel.getCellData("Phương thức_DSSP_NPP", dong);
                    chonPhuongThuc(phuongThuc);
                    for(int i =0 ; i< getArrayAfterPhanTachDauPhay(sku_DSSP_NPP).length; i++){
                        String skuItem=getArrayAfterPhanTachDauPhay(sku_DSSP_NPP)[i];
                        themMoiDieuKienBangForm(loaiDieuKien, skuItem, "SKU", "Tìm kiếm sản phẩm");
                    }
            }
            threadSecond(2);
        }
    }

    public void themNoiDung(String loaiNoiDung) {
        checkToDefaultCheckBox(driver, KhuyenMaiUI.RADIO_ND_DYM, "Giảm giá trên tổng giá trị giỏ");
        switch (loaiNoiDung) {
            case "Giảm giá theo phần trăm với số tiền tối đa":
                chonNoiDung("Giảm giá theo phần trăm với số tiền tối đa");
                threadSecond(2);
                sendKeyByNameLabel("2", "Giá trị giảm (%)");
                threadSecond(2);
                sendKeyByNameLabel("5000", "Tối đa (đ)");
                break;
            case "Giảm số tiền cố định":
                chonNoiDung("Giảm số tiền cố định");
                threadSecond(2);
                sendKeyByNameLabel("6000", "Giá trị giảm (đ)");
                break;
            case "Giảm giá theo phần trăm":
                chonNoiDung("Giảm giá theo phần trăm");
                threadSecond(2);
                sendKeyByNameLabel("3", "Giá trị giảm (%)");
                break;
        }
        threadSecond(3);
    }

    public void themDoiTuong(String loaiDoiTuong, int dong, ExcelHelper excel) {
        if (excel.isCellHasData(loaiDoiTuong, dong)) {
            clickToBtnByText("Thêm đối tượng");
            switch (loaiDoiTuong) {
                case "Đối tượng thuộc số điện thoại":
                    chonDoiTuong(loaiDoiTuong);
                    String phuongThuc_DTThuocSoDT = excel.getCellData("Phương thức_DTThuocSoDT", dong);
                    chonPhuongThuc(phuongThuc_DTThuocSoDT);
                    String soDT = excel.getCellData("Số điện thoại_DTThuocSoDT", dong);
                    for(int i =0 ; i< getArrayAfterPhanTachDauPhay(soDT).length; i++){
                        String soDTItem=getArrayAfterPhanTachDauPhay(soDT)[i];
                        sendKeyByNameLabel(soDTItem, "Số điện thoại");
                        clickToBtnByText("Thêm số");
                    }
                    break;
            }
            threadSecond(2);
        }
    }

    public void themDieuKienSuDung(String soLanSuDungToiDaCuaCT, String soLanSuDungToiDaChoMoiNguoi,
                                   String soLanSuDungToiDaTrenMoiNguoiTrenMoiChuKi, String soLanSuDungToiDaTrenCTKMTrenChuKi) {
        if (!soLanSuDungToiDaCuaCT.isEmpty()) {
            sendKeyByNameLabel(soLanSuDungToiDaCuaCT, "Số lần sử dụng tối đa của chương trình");
        }
        if (!soLanSuDungToiDaChoMoiNguoi.isEmpty()) {
            sendKeyByNameLabel(soLanSuDungToiDaCuaCT, "Số lần sử dụng tối đa cho mỗi người");
        }
        if (!soLanSuDungToiDaTrenMoiNguoiTrenMoiChuKi.isEmpty()) {
            sendKeyByNameLabel(soLanSuDungToiDaCuaCT, "Số lần sử dụng tối đa/người/chu kì");
        }
        if (!soLanSuDungToiDaTrenCTKMTrenChuKi.isEmpty()) {
            sendKeyByNameLabel(soLanSuDungToiDaCuaCT, "Số lần sử dụng tối đa/CTKM/chu kì");
        }
    }

    public void thietLapMa(String loaiMa) {
        checkToDefaultCheckBox(driver, KhuyenMaiUI.RADIO_ND_DYM, loaiMa);
        switch (loaiMa) {
            case "Mã công khai":
                sendKeyByNameLabel("3", "Số lượng sử dụng tối đa của một mã");
                sendKeyByNameLabel("1", "Số lần sử dụng tối đa của một người/mã");
                sendKeyByNameLabel("Auto" + ranDomNumber(), "Mã Khuyến mãi");
                break;
            case "Mã cá nhân":
                sendKeyByNameLabel("5", "Số lượng mã giảm giá cho chương trình");
                sendKeyByNameLabel("3", "Số lượng sử dụng tối đa của một mã");
                sendKeyByNameLabel("1", "Số lần sử dụng tối đa của một người/mã");
                chonKieuDinhDangMa("Ký tự");
                sendKeyByNameLabel("Auto" + ranDomNumber(), "Ký tự bắt đầu");
                break;
        }
        clickToElement(driver, KhuyenMaiUI.BTN_DSCTKM_DYM, "Tạo mã khuyến mãi");
        threadSecond(2);
        clickToElement(driver, KhuyenMaiUI.BTN_DSCTKM_DYM, "Đóng");

    }

    public void unCheckThietLapMa() {
        int sizeRadio = getElementSize(driver,KhuyenMaiUI.SUDUNGMA_RADIO);
        int demClickTimKiem = 0;
        while (sizeRadio==0 && demClickTimKiem < 5) {
            threadSecond(1);
            demClickTimKiem++;
            sizeRadio = getElementSize(driver,KhuyenMaiUI.SUDUNGMA_RADIO);
        }
        unCheckToDefaultCheckBox(driver, KhuyenMaiUI.SUDUNGMA_RADIO);
        clickToElement(driver, KhuyenMaiUI.BTN_DSCTKM_DYM, "Lưu");
        threadSecond(1);
    }

    public void searchKhuyenMai(String textSendKey, String searchTheoField) {
        senkeyToElement(driver, KhuyenMaiUI.SEARCH_DYM, textSendKey, searchTheoField);
        clickToBtnByText("Tìm kiếm");
        int sizeRadio = getListElement(driver, KhuyenMaiUI.KM_RADIO).size();
        int demClickTimKiem = 0;
        while (sizeRadio != 1 && demClickTimKiem < 5) {
            clickToBtnByText("Tìm kiếm");
            demClickTimKiem++;
            threadSecond(5);
            sizeRadio = getListElement(driver, KhuyenMaiUI.KM_RADIO).size();
        }
    }

    public String getMaCTKM() {
        return getElementAtribute(driver, KhuyenMaiUI.MA_CTKH, "aria-label");
    }

    public void guiXetDuyet(String maKhuyenMai) {
        scrollToBottomPage(driver);
        threadSecond(1);
        clickToElement(driver, KhuyenMaiUI.ICON_ACTION_DYM, "anticon anticon-carry-out");
        threadSecond(1);
        clickToBtnByText("Gửi xét duyệt");
        threadSecond(1);
        clickToBtnByText("OK");

        threadSecond(3);
        clickToElement(driver, KhuyenMaiUI.ICON_ACTION_DYM, "anticon anticon-tool");
        clickToBtnByText("Phê duyệt");
        clickToBtnByText("OK");
        threadSecond(2);
    }

    public void waitDangDienRa(String maKhuyenMai) {
        clickToElement(driver, KhuyenMaiUI.ICON_CLEARTEXT);
        searchKhuyenMai(maKhuyenMai, "Tìm kiếm CTKM");
        String textDangDienRa = getTrangThaiKM();
        while (!(textDangDienRa.equals("Đang diễn ra"))) {
            clickToBtnByText("Tìm kiếm");
            textDangDienRa = getTrangThaiKM();
            threadSecond(7);
        }
    }

    public String getTrangThaiKM() {
        return getElementText(driver, KhuyenMaiUI.TRANGTHAI_CTKH);
    }


}
