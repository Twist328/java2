package ru.progwards.java2.lessons.ExpCalc;

public class Calculator {
    String expression;
    int pos;

    public static void main(String[] args) {
        System.out.println(calculate("(2+3)*(6/3)"));
    }

    public Calculator(String expression) {
        this.expression = expression;
        pos = 0;
    }

    // получить символ выражения (expression)
    String getSym() {
        if (pos >= expression.length())
            try {
                throw new IndexOutOfBoundsException();
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        return expression.substring(pos++, pos);
    }

    // просматриваем что за символ впереди
    String checkSym() {
        if (pos >= expression.length())
            return "";
        return expression.substring(pos, pos + 1);
    }

    // получить из символа число
    int getNum() {
        int num = 0;
        try {
            num = Integer.parseInt(getSym());
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
    int term2() {
        String symbol = checkSym();
        if (symbol.equals("(")) {
            getSym();
            int result = expression();
            String symbolNext = getSym();
            if (symbolNext.equals(")")) {
                return result;
            } else {
                try {
                    throw new Exception("Ожидалась \")\"");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return getNum();
    }

    int term() {
        int result = term2();
        while (hasNext()) {
            String symbol = checkSym();
            if ("*/".contains(symbol)) {
                getSym();
                int num = term2();
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

    int expression() {
        int result = term();
        while (hasNext()) {
            String symbol = checkSym();
            if ("+-".contains(symbol)) {
                getSym();
                int num = term();
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

    public static int calculate(String expression) {
        return new Calculator(expression).expression();
    }
}


