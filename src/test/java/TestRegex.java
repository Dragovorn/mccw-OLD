import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRegex {

    @Test
    public void testRegex() {
        String regex = " \\(Place to Build\\)";

        assertEquals("Town Hall", "Town Hall (Place to Build)".replaceAll(regex, ""));
    }
}