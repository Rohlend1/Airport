package util;

import java.util.List;

public class Validator {

    private final List<Integer> columnNumbers;
    private final List<String> operators;
    private final List<String> valuesToCompare;
    private final List<String> expressions;

    public Validator(List<Integer> columnNumbers, List<String> operators, List<String> valuesToCompare, List<String> expressions) {
        this.columnNumbers = columnNumbers;
        this.operators = operators;
        this.valuesToCompare = valuesToCompare;
        this.expressions = expressions;
        this.checkFilters();
    }

    public boolean checkAirport(String[] airportString, String prefixAirportName){
        if(checkName(airportString[2],prefixAirportName)) {
            return validateAirport(airportString);
        }
        return false;
    }
    private void checkFilters(){
        if(operators.size() != columnNumbers.size() || operators.size() != valuesToCompare.size() ||
                (operators.size() != expressions.size()*2 && expressions.size() != 0 && operators.size() < 2)||
        (operators.size()==0 && expressions.size() == 0 && columnNumbers.size()==0 && valuesToCompare.size()==0)){
            throw new IllegalArgumentException("Incorrect filters");
        }
    }
    private boolean validateAirport(String[] airportString) {
        if(operators.size()==1) return Executor.evaluateOperation(operators.get(0), airportString[columnNumbers.get(0)],valuesToCompare.get(0));

        int counter = 2;
        boolean operand1 = Executor.evaluateOperation(operators.get(0), airportString[columnNumbers.get(0)].toLowerCase(),valuesToCompare.get(0));
        boolean operand2 = Executor.evaluateOperation(operators.get(1), airportString[columnNumbers.get(1)].toLowerCase(),valuesToCompare.get(1));

        if(expressions.size()==1) return Executor.evaluateExpression(expressions.get(0),operand1,operand2);
        boolean satisfiesFilter = true;
        for(String expression: expressions){
            operand1 = satisfiesFilter && Executor.evaluateExpression(expression,operand1,operand2);
            if(counter==operators.size()) return operand1;
            operand2 = Executor.evaluateOperation(operators.get(counter),
                    airportString[columnNumbers.get(counter)].toLowerCase(),valuesToCompare.get(counter));
            satisfiesFilter = operand1;
            counter++;
        }
        return satisfiesFilter;
    }
    private boolean checkName(String airportName, String prefixAirportName){
        return airportName.toLowerCase().startsWith(prefixAirportName.toLowerCase());
    }
}
