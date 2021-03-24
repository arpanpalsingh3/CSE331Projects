package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pathfinder.parser.CampusPathsParser.parseCampusBuildings;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        CampusMap uwCampusMap = new CampusMap(); // my uw campus map
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.
        Spark.get("/campuspath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startPath = request.queryParams("start");
                String endPath = request.queryParams("end");
                Path<Point> path = uwCampusMap.findShortestPath(startPath, endPath);
                Gson gson = new Gson();
                return gson.toJson(path);
            }
        });

    }

}
