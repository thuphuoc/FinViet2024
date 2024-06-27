package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HowToLoad {
    private boolean is_load;
    private String type_load ;
    private String how_to_choose;
    private boolean is_suggest_show ;
    private int number_purchased ;
}
