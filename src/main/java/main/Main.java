package main;

import util.FilterParser;
import util.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final List<Integer> columnNumbers = new ArrayList<>();
    private static final List<String> operators = new ArrayList<>();
    private static final List<String> valuesToCompare = new ArrayList<>();
    private static final List<String> foundAirports = new ArrayList<>();

    private static final List<String> expressions = new ArrayList<>();
    private static int countLines = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader readerFromConsole = new BufferedReader(new InputStreamReader(System.in));

        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStreamCSV = classLoader.getResourceAsStream("airport.csv");

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

            try(BufferedReader readerFromCSV = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStreamCSV)))){
                while((csvString = readerFromCSV.readLine())!=null){
                    airportString = csvString.split(",");
                    if(validator.checkAirport(airportString, prefixAirportName)){
                        countLines++;
                        foundAirports.add(String.format("%s%s",airportString[3],Arrays.toString(airportString)));
                    }
                }

                long endSearch = System.currentTimeMillis();

                foundAirports.forEach(System.out::println);
                System.out.printf("Total number of lines %d, executed for %d ms%n",countLines,(endSearch-startSearch));
                resetVariables();
            }
            System.out.println("Enter your filters: ");
        }
    }
    private static void resetVariables(){
        foundAirports.clear();
        countLines=0;
        columnNumbers.clear();
        expressions.clear();
        operators.clear();
        valuesToCompare.clear();
    }
}