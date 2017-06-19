package product;

import java.util.ArrayList;

/**
 * Created by User on 19.06.2017.
 */
public class Category {
    private String name;
    private ArrayList<Barcode> barcodes;
    public Category(String name, ArrayList<Barcode> barcodes) {
        this.barcodes = barcodes;
        this.name = name;
    }

    public ArrayList<Barcode> getBarcodes() {
        return barcodes;
    }

    public String getName() {
        return name;
    }
}
