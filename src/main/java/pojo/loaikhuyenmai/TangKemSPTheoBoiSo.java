package pojo.loaikhuyenmai;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

public class TangKemSPTheoBoiSo {
    @AllArgsConstructor
    @Data
    public  class Orders {
        private List<Order> orders;
        private String agent_phone ;
        private String order_time ;
        private String channel ;
        private String payment_method ;
        private int total_amount ;
        private HowToLoad how_to_load;
        private String partner_code ;
        private String current_sale_phone ;
        private boolean is_buy_eco ;
        private boolean is_buy_ecom ;


    }
    @Data
    @AllArgsConstructor
    public class Product {
        private String sku ;
        private int price ;
        private int quantity ;
        private HowToLoad how_to_load;

    }

    @AllArgsConstructor
    @Data
    public class Order {
        private List<pojo.Product> products;
        private String company_id;
        private String payment_method;
        private int total_amount;
        private HowToLoad how_to_load;
    }

    @Data
    @AllArgsConstructor
    public class HowToLoad {
        private boolean is_load;
        private String type_load ;
        private String how_to_choose;
        private boolean is_suggest_show ;
        private int number_purchased ;
    }


}
