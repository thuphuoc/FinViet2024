package pojo.loaikhuyenmai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GiamGiaSPTheoBoiSo {

    @Data
    @AllArgsConstructor
    public class Order {
        private String session_id;
        private List<Cart> carts;
        private Info info;
        private String agent_ref;
        private String channel;
        private boolean is_payment;
    }

    @Data
    @AllArgsConstructor
    public class Cart {
        private int org_id;
        private String brand_type;
        private List<Object> vouchers;
        private List<Product> products;
    }

    @Data
    @AllArgsConstructor
    public class Product {
        private int product_id;
        private List<ProductVariant> product_variants;
    }

    @Data
    @AllArgsConstructor
    public class ProductVariant {
        private int org_id;
        private String sku;
        private int quantity_purchase;
        private int base_price;
        private int special_price;
        private List<Object> gifts;
    }

    @Data
    @AllArgsConstructor
    public class Info {
        private Customer customer;
        private int payment_channel;
        private int payment_id;
        private String shipping_type;
        private String channel;
    }

    @Data
    @AllArgsConstructor
    public class Customer {
        private Province province;
        private District district;
        private Ward ward;
    }

    @Data
    @AllArgsConstructor
    public class Province {
        private int id;
    }

    @Data
    @AllArgsConstructor
    public class District {
        private int id;
    }

    @Data
    @AllArgsConstructor
    public class Ward {
        private int id;
    }
}
