package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String sku ;
    private int price ;
    private int quantity ;
    private HowToLoad how_to_load;

}
