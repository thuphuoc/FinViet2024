package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class Order {
    private List<Product> products;
    private String company_id;
    private String payment_method;
    private int total_amount;
    private HowToLoad how_to_load;
}
