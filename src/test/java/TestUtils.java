import com.dragovorn.mccw.utils.Calculator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    @Test
    public void testToRoman() {
        System.out.println(Calculator.toRoman(3));

        assertEquals(Calculator.toRoman(3), "III");

        System.out.println(Calculator.toRoman(7));

        assertEquals(Calculator.toRoman(7), "VII");
    }

    @Test
    public void testFormToLine() {
        System.out.println(Calculator.formToLine(9));

        assertEquals(Calculator.formToLine(9), 1);
        assertEquals(Calculator.formToLine(18), 2);
    }
}