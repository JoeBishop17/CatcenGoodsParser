import hibernate.ManageProduct;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import product.Barcode;
import product.Category;
import product.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GoodsParser implements Runnable{

    private String url = "http://www.goodsmatrix.ru/GMMap.aspx";

    private Elements links;
    private ArrayList<String> categorieslinks;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<Product> productArrayList;

    public GoodsParser() throws IOException {
        System.out.println("Начало работы");
        links = parseCategoriesLinks();
        System.out.println("Загрузка категорий...");
        categorieslinks = getLinksAsList(links);
        System.out.println("Загрузка категорий - завершена");
        productArrayList = new ArrayList<Product>();
        run();
    }

    private Product getProductInfo(Category category, Barcode barcode) throws IOException {
        Product product = null;

        String url = "http://goodsmatrix.ru/goods/" + barcode.getBarcode() + ".html".trim();

        try {
            Connection.Response response = Jsoup
                    .connect(url)
                    .timeout(300000000)
                    .execute();

            int statusCode = response.statusCode();

            Document doc;

            if(statusCode == 200) {
                doc = response.parse();

                String name = doc.select("title").text();

                String composition = doc.select("#ctl00_ContentPH_Composition").text();

                product = new Product(category, name, composition, barcode);

                return product;
            }

            else {
                System.out.println("Ошибка чтения URL. Status code error = " + statusCode);
            }
        } catch (HttpStatusException e) {
            e.printStackTrace();
        }

        throw new IOException("Ошибка при попытке пропарсить ссылку " + url);
    }

    private ArrayList<Category> getAllBarcodesOnPage(String link) throws IOException {
        ArrayList<Category> categoriesList = new ArrayList<Category>();
        Connection.Response bardcodeResponse = Jsoup.connect(link.replaceAll(" ", ""))
                .method(Connection.Method.GET)
                .timeout(15000)
                .userAgent("Mozilla")
                .execute();

        Document doc = bardcodeResponse.parse();

        String title = doc.title();

        Elements barcodeTable = doc.select("table.Grd");

        Elements barcodes = barcodeTable.select("a[href]");


        ArrayList<Barcode> barcodesList = new ArrayList<Barcode>();

        for(Element e : barcodes) {
            if(e.attr("id").endsWith("A2")) {
                barcodesList.add(new Barcode(e.childNode(0).outerHtml().trim()));
            }
        }

        categoriesList.add(new Category(title, barcodesList));

        return categoriesList;
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    private ArrayList<String> getLinksAsList(Elements links) {
        ArrayList<String> linksAsList = new ArrayList<String>();
        for(Element e : links) {
            if(e.attr("href").contains("http")) {
                linksAsList.add(e.attr("href"));
            }

            else {
                String data = "http://www.goodmatrix.ru/" + e.attr("href");
                linksAsList.add(data);
            }
        }

        return linksAsList;
    }

    private Elements parseCategoriesLinks() throws IOException {
        Connection.Response goodsResponse = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .userAgent("Mozilla")
                .execute();

        Document doc = goodsResponse.parse();

        Elements catalogueTable = doc.select("table[cellspacing=10]");

        return catalogueTable.select("a[href]");
    }

    Elements getLinks() {
        return links;
    }

    ArrayList<String> getCategoriesLinks() {
        return categorieslinks;
    }

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    @Override
    public void run() {
        ManageProduct manageProduct = new ManageProduct();

        int totalProducts = 0;

        for(String s : categorieslinks) {
            System.out.println("Парсится ссылка " + s );
            String link = s.trim();
            try {
                categoryArrayList = getAllBarcodesOnPage(link);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int products = 0;

            for(Category c : categoryArrayList) {
                System.out.println("Парсится категория " + c.getName());
                for(Barcode b : c.getBarcodes()) {
                    if(!b.getBarcode().contains("&nbsp")) {
                        products++;
                        System.out.println("Парсится продукт. Штрихкод: " + b.getBarcode());
                        Product product = null;
                        try {
                            product = getProductInfo(c, b);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(product != null) {
                            productArrayList.add(product);
                            manageProduct.addProduct(c, product, b);
                        }
                    }

                    else {
                        System.out.println("Ссылка на продукт имеет недопустимыый символ, пропуск.");
                    }
                }
                System.out.println("Категория успешно пройдена.\nДобавлено продуктов: " + products);

                totalProducts += products;

                System.out.println("Всего добавлено продуктов:  " + totalProducts);
            }
        }
    }
}
