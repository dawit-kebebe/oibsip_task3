package com.dawit.oibsip_task3;

import android.util.Log;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Eval {
    private Stack<String> operator;
    private Stack<Double> value;
    private Map<String, Integer> priorityMap;

    public Eval() {
        priorityMap = new HashMap<String, Integer>();
        operator = new Stack<String>();
        value = new Stack<Double>();

        priorityMap.put("+", 1);
        priorityMap.put("-", 1);
        priorityMap.put("×", 2);
        priorityMap.put("÷", 3);
        priorityMap.put("√", 4);
        priorityMap.put("^", 4);
    }

    public static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '×' || token == '÷' || token == '^' || token == '√';
    }

    public static boolean isParenthesisOpen(String statement) {

        for (int i = statement.length() - 1; i >= 0; i--){
            if (statement.charAt(i) == '('){
                return true;
            }else if (statement.charAt(i) == ')'){
                return false;
            }
        }

        return false;
    }

    public Stack<Double> evaluate(Stack<String> statement) {
        Stack<String> original = new Stack<>();
        if (Eval.isOperator(statement.peek().toCharArray()[0])) {
            statement.pop();
        }

        while (!statement.isEmpty())
            original.push(statement.pop());

        while (!original.isEmpty()) {
            if (!isNumeric(original.peek().toString())){
                if (operator.size() == 0){
                    operator.push(original.pop());
                }else {
                    String originalOp = original.peek();
                    String currentOp = operator.peek();

                    if (currentOp != null && originalOp != null){
                        if (priorityMap.get(currentOp) >= priorityMap.get(originalOp)){
                            value = eval(value, operator.pop().charAt(0));
                        }else if (priorityMap.get(currentOp) < priorityMap.get(originalOp)){
                            operator.push(original.pop());
                        }
                    }

                }
            }else if (isNumeric(original.peek())) {
                value.push(Double.parseDouble(original.pop()));
            }
        }

        while (!operator.isEmpty()) {
            value = eval(value, operator.pop().toString().charAt(0));
        }

        return value;
    }

    private Stack<Double> eval(Stack<Double> values, char opr) {
        double right = values.pop();
        double left =  values.pop();
        switch (opr){
            case '+':
                values.push(add(left, right));
                break;
            case '-':
                values.push(sub(left, right));
                break;
            case '×':
                values.push(mul(left, right));
                break;
            case '÷':
                values.push(div(left, right));
                break;
            case '√':
                values.push(sqrt(left, right));
                break;
            case '^':
                values.push(sqr(left, right));
        }

        return values;
    }

    private double add(double left, double right) {
        return (left + right);
    }

    private double sub(double left, double right) {
        return (left - right);
    }

    private double mul(double left, double right) {
        return (left * right);
    }

    private double div(double left, double right) {
        return (left / right);
    }

    private double sqr(double left, double right) {
        return Math.pow(left, right);
    }

    private double sqrt(double left, double right) {
        return Math.sqrt(left);
    }

}
