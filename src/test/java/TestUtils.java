import com.dragovorn.mccw.utils.RomanNumerals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    @Test
    public void testToRoman() {
        System.out.println(RomanNumerals.toRoman(3));

        assertEquals(RomanNumerals.toRoman(3), "III");

        System.out.println(RomanNumerals.toRoman(7));

        assertEquals(RomanNumerals.toRoman(7), "VII");
    }
}