package product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 19.06.2017.
 */
@Embeddable
public class Category implements Serializable {

    @Column(name = "category")
    private String name;
    @Transient
    private ArrayList<Barcode> barcodes;

    public Category() {}
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
