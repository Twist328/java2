package ru.progwards.java2.lessons.calculator;


public class Calculator {

    public static void main(String[] args) {
        System.out.println("\n************************************");
        System.out.println("результат вычислений:  "+ calculate("9/3*2+8/2-5")); //5
        System.out.println("результат вычислений:  "+ calculate("1+3*2-6/3*9")); //-11
        System.out.println("************************************");
    }

        /*Реализуйте метод public static int calculate(String expression), который вычисляет арифметическое выражение,
         заданное в виде символьной строки. Выражение содержит только целые цифры и знаки арифметических операций +-
         При вычислении должны учитываться приоритеты операций, например, результат вычисления выражения "2+3*2"
         должен быть равен 8. По оригинальному условию задачи все числа содержат не более одной цифры, пробелов в строке нет.*/

    public static int calculate(String expression) { //не получилось со скобками

        String str = expression; // создание символьной строки
        int i = Integer.valueOf(str.substring(0, 1)); //первый элемент ( первый множитель или делитель)
        //if (i == Integer.valueOf(str.substring(0), Integer.parseInt("-"))) return Integer.parseInt("-" +i); нельзя отрицательные значения
        if (str.length()== 1) return i;

        String s = str.substring(1, 2); // операнд (символ + или -, * или /, для манипуляций calculate)

        while (s.equals("*") || s.equals("/")) {   //логика

            if (s.equals("*")) {
                i *= Integer.valueOf(str.substring(2, 3)); //множитель умножается на множитель подстроки с index 2, при этом индекс 0 = i;
            } else if (s.equals("/"))
                i /= Integer.valueOf(str.substring(2, 3)); //процедура calculate деления: i делится на делитель с index 2, при этом индекс 0=i;
            str = str.substring(2);                        //Строка начинается с Подстроки  с символа по указанному индексу 2 до конца строки

            if (str.length() == 1) return i;               //указатель на index i
            s = str.substring(1, 2);                      //указатель на index операнда( первое слагаемое или уменьшаемое число)
        }
        if (s.equals("+")) {
            i += calculate(str.substring(2));            //calculate процедура сложения
        } else if (s.equals("-"))
            i -= calculate(str.substring(2));            //calculate процедура вычитания
        return i;                                        // результат

    }
}
//*********************************************************************************************************************

class Calculator1 {
    String expression; // символьная строка
    int pos;

    public static void main(String[] args) {
        System.out.println("\n***********************************");
        System.out.println("результат вычисления:    " + calculate1("2+(2+3)*(6/3)-(2*5*3)"));//-18
        System.out.println("***********************************");
    }
    public Calculator1(String expression) {
        this.expression = expression;
        pos = 0;
    }

    public static int calculate1(String expression) {
        return new Calculator1(expression).calculate();
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
