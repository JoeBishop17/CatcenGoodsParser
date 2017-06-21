import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import product.Category;
import product.Product;

import java.io.IOException;

/**
 * Created by User on 19.06.2017.
 */
public class GoodsParserTest {
    private GoodsParser goodsParser;

    public GoodsParserTest() throws IOException {
        goodsParser = new GoodsParser();
    }

    @Test
    public void testGoodsParser() {
        for(final Element expected : goodsParser.getLinks()) {
            Assert.assertNotNull(expected);
        }

        for(final String expected : goodsParser.getCategoriesLinks()) {
            Assert.assertTrue(expected.startsWith("http"));
        }
    }
}
