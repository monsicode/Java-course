package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CompetitionTest {

    private final Athlete athlete = new Athlete("f", "f", "s");

    @Test
    void testCreatingCompetitionWithNullName() {
        assertThrows(IllegalArgumentException.class,
            () -> new Competition(null, "swimming", Set.of(athlete)), "Should throw and exception");
    }

    @Test
    void testCreatingCompetitionWithBlankName() {
        assertThrows(IllegalArgumentException.class,
            () -> new Competition("", "swimming", Set.of(athlete)), "Should throw and exception");
    }

    @Test
    void testCreatingCompetitionWithNullDiscipline() {
        assertThrows(IllegalArgumentException.class,
            () -> new Competition("null", null, Set.of(athlete)), "Should throw and exception");
    }

    @Test
    void testCreatingCompetitionWithBlankDiscipline() {
        assertThrows(IllegalArgumentException.class,
            () -> new Competition("sadf", "", Set.of(athlete)), "Should throw and exception");
    }

    @Test
    void testCreatingCompetitionWithNullCompetitors() {
        assertThrows(IllegalArgumentException.class,
            () -> new Competition("null", "swimming", null), "Should throw and exception");
    }

    @Test
    void testCreatingCompetitionWithEmptyCompetitors() {
        assertThrows(IllegalArgumentException.class,
            () -> new Competition("sadf", "", Set.of()), "Should throw and exception");
    }

    @Test
    void testEqualsWithTwoIdenticalCompetitions() {
        Competition com1 = new Competition("a", "swimming", Set.of(athlete));
        Competition com2 = new Competition("a", "swimming", Set.of(athlete));

        assertTrue(com1.equals(com2), "This two competitions are equals");

    }

    @Test
    void testEqualsWithTwoDifferentCompetitions() {
        Competition com1 = new Competition("a", "swimming", Set.of(athlete));
        Competition com2 = new Competition("b", "swimming", Set.of(athlete));

        assertFalse(com1.equals(com2), "This two competitions are not equals");
    }

    @Test
    void testHashCodeWithTwoIdenticalCompetitions() {
        Competition com1 = new Competition("a", "swimming", Set.of(athlete));
        Competition com2 = new Competition("a", "swimming", Set.of(athlete));

        assertEquals(com1.hashCode(), com2.hashCode(), "This two competitors should have identical hash code");
    }

    @Test
    void testHashCodeWithTwoDifferentCompetitions() {
        Competition com1 = new Competition("a", "swimming", Set.of(athlete));
        Competition com2 = new Competition("b", "swimming", Set.of(athlete));

        assertNotEquals(com1.hashCode(), com2.hashCode(), "This two competitors should have different hash code");
    }


}
