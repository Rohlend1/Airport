package main;

import util.FilterParser;
import util.Validator;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    private static final List<Integer> columnNumbers = new ArrayList<>();
    private static final List<String> operators = new ArrayList<>();
    private static final List<String> valuesToCompare = new ArrayList<>();
    private static final List<String> expressions = new ArrayList<>();
    private static final List<String> foundAirport = new ArrayList<>();
    private static int countLines = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader readerFromConsole = new BufferedReader(new InputStreamReader(System.in));

        ClassLoader classLoader = Main.class.getClassLoader();
        String pathToCSV = URLDecoder.decode(Objects.requireNonNull(classLoader.getResource("airport.csv")).getPath(), StandardCharsets.UTF_8);

        System.out.println("Enter your filters: ");
        String filters;
        while (!(filters = readerFromConsole.readLine()).equals("!quit")){
            FilterParser.parseFilters(filters,columnNumbers,operators,valuesToCompare,expressions);
            Validator validator = new Validator(columnNumbers,operators,valuesToCompare,expressions);

            System.out.println("Enter name of the airport: ");

            String prefixAirportName = readerFromConsole.readLine();
            String csvString;
            String[] airportString;
            long startSearch = System.currentTimeMillis();

            try(BufferedReader readerFromCSV = new BufferedReader(new FileReader(pathToCSV))){
                while((csvString = readerFromCSV.readLine())!=null){
                    airportString = csvString.split(",");
                    if(validator.checkAirport(airportString, prefixAirportName)){
                        System.out.printf("%s%s%n",airportString[2],Arrays.toString(airportString));
                        countLines++;
                    }
                }

                long endSearch = System.currentTimeMillis();
                Collections.sort(foundAirport);
                foundAirport.forEach(System.out::println);
                System.out.printf("Total number of lines %d, executed for %d ms%n",countLines,(endSearch-startSearch));
                resetVariables();
            }
            System.out.println("Enter your filters: ");
        }
    }
    private static void resetVariables(){
        countLines=0;
        foundAirport.clear();
        columnNumbers.clear();
        expressions.clear();
        operators.clear();
        valuesToCompare.clear();
    }
}