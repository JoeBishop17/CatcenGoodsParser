package product;

/**
 * Created by User on 19.06.2017.
 */
public class Product {
    private Category category;
    private String name;
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
}
