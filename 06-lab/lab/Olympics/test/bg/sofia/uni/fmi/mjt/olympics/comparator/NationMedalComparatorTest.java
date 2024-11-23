package bg.sofia.uni.fmi.mjt.olympics.comparator;

import bg.sofia.uni.fmi.mjt.olympics.MJTOlympics;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class NationMedalComparatorTest {

    private final MJTOlympics olympicsMock = Mockito.mock(MJTOlympics.class);
    private final NationMedalComparator nationMedalComparator = new NationMedalComparator(olympicsMock);

    @Test
    void testCompareTwoIdenticalNations() {
        when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(20);

        assertEquals(0, nationMedalComparator.compare("Bulgaria", "Bulgaria"),
            "Comparing from the same nation should return that they are equal");
    }

    @Test
    void testCompareTwoDifferentNations() {
        when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(20);
        when(olympicsMock.getTotalMedals("North Macedonia")).thenReturn(0);

        assertEquals(-1, nationMedalComparator.compare("Bulgaria", "North Macedonia"),
            "Bulgaria has more medals than North Macedonia");
    }

    @Test
    void testCompareTwoDifferentNationsBackwards() {
        when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(20);
        when(olympicsMock.getTotalMedals("North Macedonia")).thenReturn(0);

        assertEquals(1, nationMedalComparator.compare("North Macedonia", "Bulgaria"),
            "Bulgaria has more medals than North Macedonia");
    }

    @Test
    void testCompareTwoDifferentNationsWithEqualMedalsCount() {
        when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(20);
        when(olympicsMock.getTotalMedals("USA")).thenReturn(20);

        assertTrue(nationMedalComparator.compare("USA", "Bulgaria") > 0,
            "Bulgaria and USA have equal medals, Bulgaria before USA");
    }

    @Test
    void testCompareTwoDifferentNationsWithEqualMedalsCountBackwards() {
        when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(20);
        when(olympicsMock.getTotalMedals("USA")).thenReturn(20);

        assertTrue(nationMedalComparator.compare("Bulgaria", "USA") < 0,
            "Bulgaria and USA have equal medals, Bulgaria before USA");
    }


}
