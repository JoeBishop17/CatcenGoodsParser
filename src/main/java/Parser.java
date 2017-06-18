import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser {

    private static String url = "http://www.goodsmatrix.ru/GMMap.aspx";


    public static void main(String[] args) throws IOException {
        Connection.Response goodsResponse = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .userAgent("Mozilla")
                .execute();

        Document doc = goodsResponse.parse();

        Elements catalogueTable = doc.select("table[cellspacing=10]");

        Elements links = catalogueTable.select("a[href]");

        for(Element e : links) {
            System.out.println("Link: " + e.attr("href") + "\n" + "Name: " + e.html());
        }
    }
}
