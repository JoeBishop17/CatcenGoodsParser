package product;

/**
 * Created by User on 19.06.2017.
 */
public class Product {
    private Category category;
    private String name;
    private Barcode barcode;

    public Product(Category category, String name, Barcode barcode) {
        this.category = category;
        this.name = name;
        this.barcode = barcode;
    }

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
