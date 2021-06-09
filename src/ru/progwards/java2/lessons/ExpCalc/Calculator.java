package ru.progwards.java2.lessons.ExpCalc;

public class Calculator {
    String expression; // символьная строка
    int pos;

    public static void main(String[] args) {
        System.out.println("\n***********************************");
        System.out.println("результат вычисления:    " + calculate1("2+(2+3)*(6/3)-(2*5*3)"));//-18
        System.out.println("***********************************");
    }
    public Calculator(String expression) {
        this.expression = expression;
        pos = 0;
    }

    public static int calculate1(String expression) {
        return new Calculator(expression).calculate();
    }

    // получить символ выражения (expression)
    String getSymbol() {
        if (pos >= expression.length())
            try {
                throw new IndexOutOfBoundsException();
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        return expression.substring(pos++, pos);
    }

    // проверка следующего символа
    String checkSymbol() {
        if (pos >= expression.length())
            return "";
        return expression.substring(pos, pos + 1);
    }

    // получить число из символа
    int getNum() {
        int num = 0;
        try {
            num = Integer.parseInt(getSymbol());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Неверное число");
        }
        return num;
    }

    // проверяет осталисть ли вообще символы
    boolean hasNext() {
        return pos < expression.length();
    }

    // метод вычисляет результат выражения в скобках, если они есть
    int bracketCalc() {
        String symbol = checkSymbol();
        if (symbol.equals("(")) {
            getSymbol();
            int result = calculate();
            String symbolNext = getSymbol();
            if (symbolNext.equals(")")) {
                return result;
            } else {
                try {
                    throw new Exception("Ожидалoсь \")\"");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return getNum();
    }

    int bracketСalc1() {
        int result = bracketCalc();
        while (hasNext()) {
            String symbol = checkSymbol();
            if ("*/".contains(symbol)) {
                getSymbol();
                int num = bracketCalc();
                switch (symbol) {
                    case "*":
                        result *= num;
                        break;
                    case "/":
                        result /= num;
                        break;
                    default:
                        try {
                            throw new Exception("Неизвестная операция");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
            } else
                return result;
        }
        return result;
    }

    public int calculate() {
        int result = bracketСalc1();
        while (hasNext()) {
            String symbol = checkSymbol();
            if ("+-".contains(symbol)) {
                getSymbol();
                int num = bracketСalc1();
                switch (symbol) {
                    case "+":
                        result += num;
                        break;
                    case "-":
                        result -= num;
                        break;
                    default:
                        try {
                            throw new Exception("Неизвестная операция");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
            } else
                return result;
        }
        return result;
    }
}


