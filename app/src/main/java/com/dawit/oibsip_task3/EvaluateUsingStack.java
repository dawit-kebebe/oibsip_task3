package com.dawit.oibsip_task3;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class EvaluateUsingStack {

    public static double eval(String expression) {
        Stack<Double> valueStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        String[] tokens = expression.split(" ");

        for (String token : tokens) {

            if (isNumeric(token)) {
                valueStack.push(Double.parseDouble(token));
            }

            else if (isOperator(token.charAt(0))) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    double operand2 = valueStack.pop();
                    double operand1 = valueStack.pop();
                    char operator = operatorStack.pop();
                    valueStack.push(applyOperator(operand1, operator, operand2));
                }

                operatorStack.push(token.charAt(0));
            }

            else if (token.equals("(")) {
                operatorStack.push('(');
            }

            else if (token.equals(")")) {
                while (operatorStack.peek() != '(') {
                    double operand2 = valueStack.pop();
                    double operand1 = valueStack.pop();
                    char operator = operatorStack.pop();
                    valueStack.push(applyOperator(operand1, operator, operand2));
                }

                operatorStack.pop();
            }
        }

        while (!operatorStack.isEmpty()) {
            double operand2 = valueStack.pop();
            double operand1 = valueStack.pop();
            char operator = operatorStack.pop();
            valueStack.push(applyOperator(operand1, operator, operand2));
        }

        return valueStack.peek();
    }

    private static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isOperator(char token) {
        return token == '+' || token == '-' || token == '*' || token == '/' || token == '^' || token == '√';
    }

    private static double applyOperator(double operand1, char operator, double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            case '^':
                return Math.pow(operand1, operand2);
            case '√':
                return Math.sqrt(operand1);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

}
