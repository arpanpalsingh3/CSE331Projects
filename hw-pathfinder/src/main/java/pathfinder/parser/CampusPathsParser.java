/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.parser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class capable of parsing data in campus buildings and
 * campus paths files in their expected formats.
 */
public class CampusPathsParser {

    /**
     * Parses the campus buildings file (in src/main/resources/data/) and
     * returns a list of objects containing all the data in that file.
     *
     * @param file The simple filename of a campus buildings file to parse.
     * @return A {@link List} of {@link CampusBuilding} objects, one for each
     * line in the parsed file, containing the data contained within that line.
     * @throws ParserException if the file cannot be found or parsed as expected
     */
    public static List<CampusBuilding> parseCampusBuildings(String file) {
        List<CampusBuilding> buildings = new ArrayList<>();
        CSVReader reader = initializeReader(file);
        //
        for(String[] line : reader) {
            if(line.length != 4) {
                throw new ParserException("Wrong number of fields in line.");
            }
            String shortName = line[0];
            String longName = line[1];
            double x, y;
            try {
                x = Double.parseDouble(line[2]);
                y = Double.parseDouble(line[3]);
            } catch(NumberFormatException e) {
                throw new ParserException("Cannot parse x/y coordinates as numbers.", e);
            }
            //
            buildings.add(new CampusBuilding(shortName, longName, x, y));
        }
        try {
            reader.close();
        } catch(IOException e) {
            throw new ParserException("Exception when closing parser.", e);
        }
        //
        return buildings;
    }

    /**
     * Parses the campus paths file (in src/main/resources/data/) and
     * returns a list of objects containing all the data in that file.
     *
     * @param file The simple filename of a campus paths file to parse.
     * @return A {@link List} of {@link CampusPath} objects, one for each
     * line in the parsed file, containing the data contained within that line.
     * @throws ParserException if the file cannot be found or parsed as expected
     */
    public static List<CampusPath> parseCampusPaths(String file) {
        List<CampusPath> paths = new ArrayList<>();
        CSVReader reader = initializeReader(file);
        //
        for(String[] line : reader) {
            if(line.length != 5) {
                throw new ParserException("Wrong number of fields in line.");
            }
            double x1, x2, y1, y2, distance;
            try {
                x1 = Double.parseDouble(line[0]);
                y1 = Double.parseDouble(line[1]);
                x2 = Double.parseDouble(line[2]);
                y2 = Double.parseDouble(line[3]);
                distance = Double.parseDouble(line[4]);
            } catch(NumberFormatException e) {
                throw new ParserException("Cannot parse x/y coordinates as numbers.", e);
            }
            //
            paths.add(new CampusPath(x1, y1, x2, y2, distance));
        }
        try {
            reader.close();
        } catch(IOException e) {
            throw new ParserException("Exception when closing parser.", e);
        }
        //
        return paths;
    }

    /**
     * Initializes a CSV reader with the provided filename, relative to the
     * classpath of this parser class. The returned reader is configured
     * to parse tab-character separated fields per line, and skips the first
     * line in the file.
     *
     * @param filename The file to initialize a parser for.
     * @return A new {@link CSVReader} prepared to begin reading from that file.
     */
    private static CSVReader initializeReader(String filename) {
        Reader fileReader;
        try {
            InputStream stream = CampusPathsParser.class.getResourceAsStream("/data/" + filename);
            if(stream == null) {
                throw new FileNotFoundException("No such file: " + filename);
            }
            fileReader = new BufferedReader(new InputStreamReader(stream));
        } catch(IOException e) {
            throw new ParserException("Cannot create parser.", e);
        }
        //
        CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
        return new CSVReaderBuilder(fileReader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();
    }

    /**
     * An Exception class representing an error during parsing.
     */
    public static class ParserException extends RuntimeException {

        /**
         * Creates a new ParserException with the provided message.
         *
         * @param message A message to include in the exception.
         */
        public ParserException(String message) {
            super(message);
        }

        /**
         * Creates a new ParserException with the provided message and cause.
         *
         * @param message A message to include in the exception.
         * @param cause   The exception (or other {@link Throwable}) that
         *                caused this exception to be thrown.
         */
        public ParserException(String message, Throwable cause) {
            super(message, cause);
        }

    }
}
