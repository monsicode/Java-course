package bg.sofia.uni.fmi.mjt.olympics.competitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AthleteTest {

    private Athlete athlete;

    @BeforeEach
    void setUp() {
        athlete = new Athlete("id1", "Monkata", "Bulgaria");
    }

    @Test
    void testAddMedalWithNull() {
        assertThrows(IllegalArgumentException.class, () -> athlete.addMedal(null));
    }

    @Test
    void testAddBronzeMedal() {
        athlete.addMedal(Medal.BRONZE);
        assertTrue(athlete.getMedals().contains(Medal.BRONZE), "Atlete should have bronze medal");
    }

    @Test
    void testAddBronzeMedalButChecksForSilver() {
        athlete.addMedal(Medal.BRONZE);
        assertFalse(athlete.getMedals().contains(Medal.SILVER), "Atlete should have bronze medal, not silver");
    }

    @Test
    void testAddTwiceBronzeMedal() {
        athlete.addMedal(Medal.BRONZE);
        athlete.addMedal(Medal.BRONZE);

        assertIterableEquals(List.of(Medal.BRONZE, Medal.BRONZE), athlete.getMedals(),
            "Atlete should have two bronze medal");

    }

    @Test
    void testCompareToIdenticalAthletes() {
        Athlete athlete2 = new Athlete("id1", "Monkata", "Bulgaria");
        assertTrue(athlete.equals(athlete2), "This two athletes should be equal");
    }

    @Test
    void testCompareToDifferentAthletes() {
        Athlete athlete2 = new Athlete("id3", "Petkan", "Bulgaria");

        assertFalse(athlete.equals(athlete2), "This two athletes should be different");
    }


    @Test
    void testHashCodeForIdenticalAthletes() {
        Athlete athlete2 = new Athlete("id1", "Monkata", "Bulgaria");

        assertEquals(athlete.hashCode(), athlete2.hashCode(), "The two hash codes should be equal");
    }

    @Test
    void testHashCodeForDifferentAthletes() {
        Athlete athlete2 = new Athlete("id3", "Monkata", "Bulgaria");

        assertNotEquals(athlete.hashCode(), athlete2.hashCode(), "The two hash codes should be different");
    }

}

