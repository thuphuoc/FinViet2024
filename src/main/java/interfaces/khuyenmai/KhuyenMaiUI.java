package interfaces.khuyenmai;

public class KhuyenMaiUI {
    //Danh sach khuyen mai
    public static final String SEARCH_DYM = "xpath=//label[text()='%s']/parent::div/following-sibling::div//input";
    public static final String DSKM_ROW = "xpath=//div[@class='ant-table-body']//tbody/tr[2]/td[4]";
    public static final String MA_CTKH = "xpath=//div[@class='ant-table-body']//tbody/tr[2]/td[3]/div";
    public static final String TRANGTHAI_CTKH = "xpath=//div[@class='ant-table-body']//tbody/tr[2]/td[@class='ant-table-cell'][5]//span";
    public static final String TAOMOI_KM_DYM = "xpath=//span[text()='%s']";
    public static final String ICON_ACTION_DYM= "xpath=//span[@class='%s']";
    public static final String BTN_DSCTKM_DYM = "xpath=//span[text()='%s']/parent::button";
    public static final String ICON_CLEARTEXT = "xpath=//span[@aria-label='close-circle']";
    public static final String KM_RADIO = "xpath=//tbody//label/span[@class='ant-checkbox']/ input";

    //Chon chi nhanh truc thuoc
    public static final String TIMKIEM_NCC_DMY= "xpath=//div[@role='dialog']//form//span[text()='%s']/parent::button";
    public static final String CHONNCC_CHECKBOX= "xpath=//div[@role='dialog']//span[@class='ant-checkbox']";

    //thong tin khuyen mai
    public static final String KENHMUAHANG_CHECKBOX_DYM = "xpath=//label[@for='channels']/parent::div/following-sibling::div//span[text()='%s']//ancestor::label/span[@class='ant-checkbox']";
    public static final String AREA_DYM = "xpath=//textarea[@placeholder='%s']";
    public static final String NGAYBATDAU = "xpath=//input[@id='range_time']";
    public static final String NGAYKETTHUC = "xpath=//input[@placeholder='Chọn thời gian kết thúc']";
    public static final String ERROR = "xpath=//div[@class='ant-form-item-explain-error']";
    public static final String PLACEHOLDER_TXT_DYM = "xpath=//input[@placeholder='%s']";
    public static final String LOAIKM_DYM = "xpath=//div[text()='%s']/ancestor::label/parent::div//following-sibling::div//div[@class='ant-select-selector']";
    public static final String CHILD_LOAIKM = "xpath=//div[@id='type_list']/following-sibling::div//div[contains(@class,'content')]";
    public static final String CHILD_HTKM = "xpath=//div[@id='method_list']/following-sibling::div//div[contains(@class,'content')]";
    public static final String MUCDOUUTIENCUACT_TXT = "xpath=//input[@id='priority']";
    public static final String LOAICT = "xpath=//label[text()='Loại chương trình']/following::div[@class='ant-select-selector']";
    public static final String CHILD_LOAICT = "xpath=//div[@id='kind_list']/following-sibling::div//div[contains(@class,'content')]";
    public static final String THELECHUONGTRINH_TXT = "xpath=//div[@data-contents='true']";
    public static final String TIEPTUC_BTN = "xpath=//span[text()='Tiếp tục']//ancestor::button";
    public static final String BTN_NAME_DYM = "xpath=//span[text()='%s']//ancestor::button";

    // Thong tin chuong trinh khuyen mai
    public static final String DROPDOWN_PARENT_DYM = "xpath=//label[text()='%s']/parent::div/following-sibling::div//div[@class='ant-select-selector']";
    public static final String CHILD_HTADCTKM_DYM = "xpath=//div[@id='method_apply_content_list']/following-sibling::div//div[contains(@class,'content')]";

    public static final String CHILD_LOAIDK= "xpath=//label[text()='Loại điều kiện']/parent::div/following-sibling::div//div[@class='ant-select-selector']/following-sibling::div//div[contains(@class,'content')]";
    public static final String CHILD_LOAIDOITUONG= "xpath=//label[text()='Loại đối tượng']/parent::div/following-sibling::div//div[@class='ant-select-selector']/following-sibling::div//div[contains(@class,'content')]";
    public static final String CHILD_PHUONGTHUC = "xpath=//label[text()='Phương thức']/parent::div/following-sibling::div//div[@class='ant-select-selector']/following-sibling::div//div[contains(@class,'content')]";
    public static final String CHILD_NOIDUNG = "xpath=//label[text()='Nội dung khuyến mãi']/parent::div/following-sibling::div//div[@class='ant-select-selector']/following-sibling::div//div[contains(@class,'content')]";
    public static final String CHILD_TGDAT = "xpath=//label[text()='Thời gian đặt hàng']/parent::div/following-sibling::div//div[@class='ant-select-selector']/following-sibling::div//div[contains(@class,'content')]";
    public static final String CHILD_PTTT = "xpath=//label[text()='Phương thức thanh toán']/parent::div/following-sibling::div//div[@class='ant-select-selector']/following-sibling::div//div[contains(@class,'content')]";

    public static final String PLACEHOlDER_PARENT = "xpath=//span[text()='%s']/ancestor::div[@class='ant-select-selector']";
    public static final String THEMDK_THEONHOM_BTN = "xpath=//div[text()='%s']/ancestor::div[@class='ant-card-head']/following-sibling::div//span[text()='%s']";
    public static final String INPUT_THEO_LABEL_DYM = "xpath=//label[text()='%s']/parent::div/following-sibling::div//input";
    public static final String RADIO_ND_DYM = "xpath=//span[text()='%s']//ancestor::label/span[@class='ant-radio']/input";
    public static final String CHILD_KIEUDDMA = "xpath=//div[@id='type_list']/following-sibling::div//div[contains(@class,'content')]"; //Loại trừ, Cộng dồn, Bậc thang
    public static final String RADIO_TIME_DYM = "xpath=//span[text()='%s']//ancestor::label/span[@class='ant-checkbox']/input";

    public static final String BTN_DK_DYM= "xpath=//span[text()='%s']/ancestor::div[@class='ant-row']//span[text()='%s']";
    public static final String SEARCH_LABEL_DYM = "xpath=//label[text()='%s']/parent::div/following-sibling::div//input";
    public static final String BTN_FORM_DYM = "xpath=//div[text()='%s']/ancestor::div[@class='ant-modal-content']//span[text()='%s']";
    public static final String CHECKBOX_FORM_DYM = "xpath=//div[text()='%s']//ancestor::div//tbody//label/span[@class='ant-checkbox']/input";
    public static final String SUDUNGMA_RADIO = "xpath=//input[@id='is_use_code']";
    public static final String SUDUNGMA_RADIO_ATTRIBUTE = "xpath=//input[@id='is_use_code']/parent::span";

    public static final String THEMMOI_NOIDUNG_BTN = "xpath=//div[text()='Nội dung khuyến mãi']//ancestor::div[@class='ant-card-head']/following-sibling::div//span[text()='Thêm mới ']";
    public static final String THEM_SL_NOIDUNG = "xpath=//div[text()='Nội dung khuyến mãi']//ancestor::div[@class='ant-card-head']/following-sibling::div//input[@placeholder='%s']";


}
