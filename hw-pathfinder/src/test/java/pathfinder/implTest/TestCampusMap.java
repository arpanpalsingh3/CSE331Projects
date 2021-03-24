package pathfinder.implTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.CampusMap;
import pathfinder.parser.CampusBuilding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static pathfinder.parser.CampusPathsParser.parseCampusBuildings;

public class TestCampusMap {

    @Rule public Timeout GLOBAL_TIMEOUT = Timeout.seconds(10); // 10 seconds max per method tested

    private CampusMap uwCampusMap = new CampusMap();
    List<CampusBuilding> buildsSet = parseCampusBuildings("campus_buildings.tsv");

    @Test
    public void testShortNameExistsNull() {
        assertFalse(uwCampusMap.shortNameExists(null));
    }

    @Test
    public void testShortNameExistsNonExist() {
        assertFalse(uwCampusMap.shortNameExists("I don't exist"));
    }

    @Test
    public void testShortNameExistsProper() {
        assertTrue(uwCampusMap.shortNameExists("KNE (E)"));
    }

    @Test
    public void testShortNameExistsAll() {
        // all buildings exist in CampusMap ADT
        for (CampusBuilding b : buildsSet) {
            assertTrue(uwCampusMap.shortNameExists(b.getShortName()));
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongNameForShortNull() {
        uwCampusMap.longNameForShort(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLongNameForShortNonExist() {
        uwCampusMap.longNameForShort("I'm not in graph");
    }

    @Test
    public void testLongNameForShortProper() {
        String kne_ShortName = "KNE";
        String kne_LongName = "Kane Hall (North Entrance)";
        assertEquals(kne_LongName,uwCampusMap.longNameForShort(kne_ShortName));
    }

    @Test
    public void testLongNameforShortAll() {
        for (CampusBuilding b : buildsSet) {
            assertEquals(b.getLongName(),uwCampusMap.longNameForShort(b.getShortName()));
        }
    }

    @Test
    public void testBuildingNames() {
        Map<String,String> nameSet = new HashMap<>();

        for(CampusBuilding b : buildsSet) {
            nameSet.put(b.getShortName(),b.getLongName());
        }

        assertEquals(nameSet,uwCampusMap.buildingNames());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathNulls() {
        uwCampusMap.findShortestPath(null,null);
        uwCampusMap.findShortestPath(null,"KNE");
        uwCampusMap.findShortestPath("KNE",null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathBothNonExist() {
        String a = "I don't exist";
        uwCampusMap.findShortestPath(a,a);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathOneNonExist() {
        String a = "I don't exist";
        uwCampusMap.findShortestPath("KNE",a);
        uwCampusMap.findShortestPath(a,"KNE");
    }

    @Test
    public void testFindShortestPathProper() {
        // make sure proper building names don't throw exceptions
        List<CampusBuilding> bCopy = buildsSet;
        for(CampusBuilding x : buildsSet) {
            for(CampusBuilding y : bCopy) {
                assertNotNull(uwCampusMap.findShortestPath(x.getShortName(),y.getShortName()));
            }
        }
    }

}
