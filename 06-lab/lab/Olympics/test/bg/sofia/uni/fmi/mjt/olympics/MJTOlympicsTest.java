package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.comparator.CompetitorComparator;
import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competition.CompetitionResultFetcher;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


public class MJTOlympicsTest {

    private final CompetitionResultFetcher compResultFetchMock = Mockito.mock(CompetitionResultFetcher.class);

    private final Competitor athlete1 = new Athlete("id1", "Ana", "BG");
    private final Competitor athlete2 = new Athlete("id2", "Bob", "US");
//    private final Competitor athlete4 = new Athlete("id3", "Bob", "ENG");
//    private final Competitor athlete3 = new Athlete("id4", "Bob", "US");

    private final MJTOlympics mjtOlympics = new MJTOlympics(Set.of(athlete1, athlete2), compResultFetchMock);

    private final Competition competition = new Competition("swimming", "butterfly", Set.of(athlete1, athlete2));

    @Test
    void testUpdateMedalStatisticsIfMedalsAreGivenCorrectly() {
        TreeSet<Competitor> ranking = new TreeSet<>(new CompetitorComparator());

        ranking.add(athlete1);
        ranking.add(athlete2);

        when(compResultFetchMock.getResult(competition)).thenReturn(ranking);

        mjtOlympics.updateMedalStatistics(competition);

        assertTrue(((athlete1.getMedals().contains(Medal.GOLD)) && (athlete2.getMedals().contains(Medal.SILVER))),
            "First competitor will have gold, the second silver");
    }

    @Test
    void testUpdateMedalStatisticsForCorrectlySavingIntoTheMapFirstPlace() {
        TreeSet<Competitor> ranking = new TreeSet<>(new CompetitorComparator());

        ranking.add(athlete1);
        ranking.add(athlete2);

        when(compResultFetchMock.getResult(competition)).thenReturn(ranking);

        mjtOlympics.updateMedalStatistics(competition);

        EnumMap<Medal, Integer> res = new EnumMap<>(Medal.class);
        res.put(Medal.GOLD, 1);

        assertEquals(res, mjtOlympics.getNationsMedalTable().get("BG"), "Bg should have one gold medal");

    }

    @Test
    void testUpdateMedalStatisticsForCorrectlySavingIntoTheMapSecondPlace() {
        TreeSet<Competitor> ranking = new TreeSet<>(new CompetitorComparator());

        ranking.add(athlete1);
        ranking.add(athlete2);

        when(compResultFetchMock.getResult(competition)).thenReturn(ranking);

        mjtOlympics.updateMedalStatistics(competition);

        EnumMap<Medal, Integer> res = new EnumMap<>(Medal.class);
        res.put(Medal.SILVER, 1);

        assertEquals(res, mjtOlympics.getNationsMedalTable().get("US"), "US should have one silver medal");

    }

    @Test
    void testUpdateMedalStatisticsIfCompetitionIsNull() {
        assertThrows(IllegalArgumentException.class, () -> mjtOlympics.updateMedalStatistics(null),
            "Competition cannot be null");
    }

    @Test
    void testUpdateMedalStatisticsIfCompetitorIsRegistered() {
        Competitor athlete3 = new Athlete("id7", "dfg", "BG");

        Competition competition = new Competition("swimming", "butterfly", Set.of(athlete3));

        assertThrows(IllegalArgumentException.class, () -> mjtOlympics.updateMedalStatistics(competition),
            "Competitors have to be registered");
    }

    @Test
    void testGetTotalMedalsWithNullNationality() {
        assertThrows(IllegalArgumentException.class, () -> mjtOlympics.getTotalMedals(null),
            "The nationality cannot be null");
    }

    @Test
    void testGetTotalMedalsWithInvalidNationality() {
        TreeSet<Competitor> ranking = new TreeSet<>(new CompetitorComparator());

        ranking.add(athlete1);
        ranking.add(athlete2);
        when(compResultFetchMock.getResult(competition)).thenReturn(ranking);

        mjtOlympics.updateMedalStatistics(competition);

        assertThrows(IllegalArgumentException.class, () -> mjtOlympics.getTotalMedals("ENG"),
            "The nationality is not in the medal table");
    }

    @Test
    void testGetTotalMedalsCorrectly() {
        TreeSet<Competitor> ranking = new TreeSet<>(new CompetitorComparator());

        ranking.add(athlete1);
        ranking.add(athlete2);

        when(compResultFetchMock.getResult(competition)).thenReturn(ranking);

        mjtOlympics.updateMedalStatistics(competition);

        assertEquals(1, mjtOlympics.getTotalMedals("US"), "Total medals should be 1");
    }

    @Test
    void testGetNationsRankListAreOrderedCorrectly() {
        TreeSet<Competitor> ranking = new TreeSet<>(new CompetitorComparator());

        ranking.add(athlete1);
        ranking.add(athlete2);

        when(compResultFetchMock.getResult(competition)).thenReturn(ranking);

        mjtOlympics.updateMedalStatistics(competition);

        TreeSet<String> nationsRankList = mjtOlympics.getNationsRankList();
        List<String> expectedOrder = List.of("BG", "US");

        assertIterableEquals(expectedOrder, nationsRankList, "The nations should be ranked as expected.");
    }

}
