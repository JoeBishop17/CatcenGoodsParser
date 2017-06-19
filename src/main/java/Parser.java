import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import product.Barcode;
import product.Category;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private static String url = "http://www.goodsmatrix.ru/GMMap.aspx";

    private Elements links;
    private ArrayList<String> categorieslinks;
    private ArrayList<Category> categoryArrayList;

    Parser() throws IOException {
        links = parseCategoriesLinks();
        categorieslinks = getLinksAsList(links);

        for(String s : categorieslinks) {
            categoryArrayList =getAllBarcodesOnPage(s);
        }
    }

    private ArrayList<Category> getAllBarcodesOnPage(String link) throws IOException {
        ArrayList<Category> categoriesList = new ArrayList<Category>();
        Connection.Response bardcodeResponse = Jsoup.connect(link)
                .method(Connection.Method.GET)
                .userAgent("Mozilla")
                .execute();

        Document doc = bardcodeResponse.parse();

        String title = doc.title();

        Elements barcodeTable = doc.select("table.Grd");

        Elements barcodes = barcodeTable.select("a[href]");


        ArrayList<Barcode> barcodesList = new ArrayList<Barcode>();

        for(Element e : barcodes) {
            if(e.attr("id").endsWith("A2")) {
                barcodesList.add(new Barcode(Long.valueOf(e.childNode(0).outerHtml().trim())));
            }
        }

        System.out.println("Добавлена новая категория\n"
                +
                "Имя: "
                + title
                + "\n"
                + "Штрихкодов: " +
                barcodesList.size());

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
}
