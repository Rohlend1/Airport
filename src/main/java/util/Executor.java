package util;

public class Executor {
    public static boolean evaluateExpression(String expression, boolean value1, boolean value2){
        switch (expression){
            case "&":
                return value1 && value2;
            case "||":
                return value1 || value2;
            default:
                throw new IllegalArgumentException("Invalid expression: "+expression);
        }
    }
    public static boolean evaluateOperation(String operator, String valueFromAirport, String valueToCompare){
        switch (operator) {
            case "=":
                return valueFromAirport.equals(valueToCompare);
            case "<>":
                return !valueFromAirport.equals(valueToCompare);
            case ">":
                return valueFromAirport.compareTo(valueToCompare) > 0;
            case "<":
                return valueFromAirport.compareTo(valueToCompare) < 0;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
