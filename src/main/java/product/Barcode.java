package product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 19.06.2017.
 */
@Embeddable
public class Barcode implements Serializable{
    @Column(name = "barcode")
    private String barcode;

    public Barcode() {}
    public Barcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }


}

