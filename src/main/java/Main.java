import hibernate.ManageProduct;
import product.Product;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 06.07.2017.
 */
public class Main {
    public static void main(String[] args) {
        GoodsParser goodsParser = null;

        try {
            goodsParser = new GoodsParser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
