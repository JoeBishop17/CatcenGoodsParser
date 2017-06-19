import com.sun.deploy.util.StringUtils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;


import org.jsoup.nodes.Element;
import product.Barcode;
import product.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 19.06.2017.
 */
public class ParserTest {
    private Parser parser;

    public ParserTest() throws IOException {
        parser = new Parser();
    }

    @Test
    public void testLinksNotEmpty() {
        for (final Element expected : parser.getLinks()) {
            Assert.assertTrue(expected != null);
        }
    }

    @Test
    public void testLinksIsCorrect() {
        for (final String expected : parser.getCategoriesLinks()) {
            Assert.assertTrue(expected.startsWith("http"));
        }
    }

    @Test
    public void testBarcodesNotEmpty() {
        for(final Category expected : parser.getCategoryArrayList()) {
            Assert.assertNotNull(expected.getBarcodes());
            Assert.assertNotNull(expected.getName());
        }
    }

    @Test
    public void testBarcodesIsCorrect() {

    }
}
