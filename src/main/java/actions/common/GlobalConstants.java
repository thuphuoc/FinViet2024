package actions.common;

import java.util.concurrent.ThreadLocalRandom;

public class GlobalConstants {
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String URL = "https://promotion-test.finviet.com.vn/auth/login";
    public static final String URL_PROMTION = "https://promotion-test.finviet.com.vn/promo/manager";
    public static final String USERNAME = "0902855998";
    public static final String PASSWORD = "Trien1000!";
    public static final String URL_API = "https://promotion-test.finviet.com.vn:6868/v1/combines/promotions:list-promotions";

    //Quản lý các tên trong excel
    //sheet valid data
    public static final String NAME_HTTANGKEMSPTHEOBOISO = "HTTangKemSPTheoBoiSo.xlsx";
    public static final String COL_QUANTITY_THUCTE = "Quantity_Thực tế";
    public static final String COL_QUANTITY_MONGDOI = "Quantity_Mong đợi";
    public static final String COL_SCHEMAID_THUCTE = "SchemaID_Thực tế";
    public static final String COL_SCHEMAID_MONGDOI = "SchemaID_Mong đợi";
    public static final String COL_NAME_THUCTE = "Name_Thực tế";
    public static final String COL_NAME_MONGDOI = "Name_Mong đợi";
    public static final String COL_KETQUA = "Kết quả";
    //sheet invalid data
}