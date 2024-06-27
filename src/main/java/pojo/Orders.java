package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class Orders {
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
