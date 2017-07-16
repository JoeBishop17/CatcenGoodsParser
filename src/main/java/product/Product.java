package product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 19.06.2017.
 */

@Entity
@Table(name = "GoodsForDanya")
public class Product implements Serializable{
    @Id @GeneratedValue
    private int id;

    private Category category;
    @Column(name = "name", length = 1024)
    private String name;
    @Column(name = "consist", length = 8192)
    private String consist;
    private Barcode barcode;

    public Product(Category category, String name, String consist, Barcode barcode) {
        this.category = category;
        this.consist = consist;
        this.name = name;
        this.barcode = barcode;
    }

    public String getConsist() { return consist; }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }
}
