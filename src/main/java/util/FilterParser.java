package util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterParser {

    public static void parseFilters(String filters, List<Integer> columnNumbers, List<String> operators,
                                    List<String> valuesToCompare,List<String> expressions){
        FilterParser.fillColumnNumbers(filters,columnNumbers);
        FilterParser.fillOperatorsAndValuesToCompare(filters,operators,valuesToCompare);
        FilterParser.fillExpressions(filters,expressions);
    }

    private static void fillColumnNumbers(String filters, List<Integer> columnNumbers){
        String[] numbersString = filters.split("[\\[\\]]");
        for (int i = 1; i < numbersString.length;i+=2){
            columnNumbers.add(Integer.parseInt(numbersString[i])-1);
        }
    }
    private static void fillOperatorsAndValuesToCompare(String filters,List<String> operators, List<String> valuesToCompare){
        Pattern pattern = Pattern.compile("(=|<>|<|>|<=|>=)([^&\\s]+)");
        Matcher matcher = pattern.matcher(filters);
        while (matcher.find()) {
            operators.add(matcher.group(1));
            valuesToCompare.add(matcher.group(2).toLowerCase());
        }
    }
    private static void fillExpressions(String filters, List<String> expressions){
        String[] expressionsString = filters.split("\\s+");
        for (int i = 1; i < expressionsString.length;i+=2){
            expressions.add(expressionsString[i]);
        }
    }
}
