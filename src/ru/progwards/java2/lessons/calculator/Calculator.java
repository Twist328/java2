package ru.progwards.java2.lessons.calculator;


//import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Calculator {

//
//    public Calculator(String exspression) {
//        this.exspression = exspression;
//    }

    public static void main(String[] args) {
        System.out.println("\n************************************");
        System.out.println("результат вычислений:  " + calculate("8/2*8/2-5")); //11
        System.out.println("результат вычислений:  " + calculate("2+3*9-8")); //21
        System.out.println("************************************");
    }

        /*Реализуйте метод public static int calculate(String expression), который вычисляет арифметическое выражение,
         заданное в виде символьной строки. Выражение содержит только целые цифры и знаки арифметических операций +-
         При вычислении должны учитываться приоритеты операций, например, результат вычисления выражения "2+3*2"
         должен быть равен 8. По оригинальному условию задачи все числа содержат не более одной цифры, пробелов в строке нет.*/

    public static int calculate(String exspression) {
        String str=exspression;
        int res = Integer.valueOf(str.substring(0, 1));
        if (str.length() == 1) return res;
        String symbol = str.substring(1, 2);
        while (symbol.equals("*") || (symbol.equals("/"))) {
            if ("*".contains(symbol)) {
                res *= Integer.valueOf(str.substring(2, 3));
            } else if ("/".contains(symbol))
                res /= Integer.valueOf(str.substring(2, 3));
            str = str.substring(2);
            symbol = str.substring(1, 2);
        }
        if ("+".contains(symbol)) {
            res += calculate(str.substring(2));
        } else if ("-".contains(symbol))
            res -= calculate(str.substring(2));
        return res;
    }
}

//*********************************************************************************************************************
class Calculator1 {//со скобками
    String expression; // символьная строка
    int pos;

    public static void main(String[] args) {
        System.out.println("\n***********************************");
        System.out.println("результат вычисления:    " + calculate1("2+(2+3)*(6/3)-(2*5*3)"));//-18
        System.out.println("***********************************");
        System.out.println("результат вычисления:    " + calculate1("8/2*(2+2)"));//16
        System.out.println("***********************************");

    }

    public Calculator1(String expression) {
        this.expression = expression;
        //pos = 0;
    }

    public static int calculate1(String expression) {
        return new Calculator1(expression).addSubtract();
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
            int result = addSubtract();
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

    int multiDivide() {
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

    public int addSubtract() {

        int result = multiDivide();
        while (hasNext()) {
            String symbol = checkSymbol();
            if ("+-".contains(symbol)) {
                getSymbol();
                int num = multiDivide();
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

//*******************************************************************
class Calculator3 {
    int pos;
    String exspression;

    public Calculator3(String exspression) {
        this.exspression = exspression;
    }

    public static void main(String[] args) {

        System.out.println("\n**************************************" + Emoji.SMILING_FACE_WITH_OPEN_MOUTH_AND_SMILING_EYES + Emoji.GRINNING_FACE_WITH_SMILING_EYES);
        System.out.println("результат = " + calculate3("8/2*(2+2)*9") + (LocalDateTime.now().format(DateTimeFormatter.ofPattern
                (" СЕГОДНЯ " + "dd-MM-YYYY ")) + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm ")))) + Emoji.SMILING_FACE_WITH_OPEN_MOUTH_AND_COLD_SWEAT);//144
        System.out.println("**************************************" + Emoji.SMILING_FACE_WITH_OPEN_MOUTH_AND_SMILING_EYES + Emoji.GRINNING_FACE_WITH_SMILING_EYES);
    }

    public static int calculate3(String exspression) {
        return new Calculator3(exspression).addSubCalc();

    }

    String getSymbol() {
        if (pos >= exspression.length())
            try {
                throw new IndexOutOfBoundsException();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        return exspression.substring(pos++, pos);
    }

    String checkSymbol() {
        if (pos >= exspression.length())
            return "";
        return exspression.substring(pos, pos + 1);
    }

    int getNum() {
        int num;
        try {
            num = Integer.parseInt(getSymbol());

        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ошибка числа");
        }
        return num;
    }

    boolean hasNext() {
        return pos < exspression.length();
    }

    int bracketCalc() {
        String symbol = checkSymbol();
        if (symbol.equals("(")) {
            getSymbol();
            int result = addSubCalc();
            String symbolNext = getSymbol();
            if (symbolNext.equals(")")) {
                return result;

            } else {
                try {
                    throw new Exception("Ожидалось \"(\"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return getNum();
    }

    int multDiv() {
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
                            throw new Exception("ошибка операции");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            } else return result;
        }
        return result;
    }

    int addSubCalc(){
        int result=multDiv();
        while (hasNext()){
            String symbol=checkSymbol();
            if ("+-".contains(symbol)){
                getSymbol();
                int num=multDiv();
                switch (symbol){
                    case "+":
                        result+=num;
                        break;
                    case "-":
                        result-=num;
                        break;
                    default:
                        try {
                            throw new Exception("ошибка операции");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
            }else return result;
        }
        return result;
    }


    enum Emoji {

        GRINNING_FACE_WITH_SMILING_EYES('\uD83D', '\uDE01'),
        FACE_WITH_TEARS_OF_JOY('\uD83D', '\uDE02'),
        SMILING_FACE_WITH_OPEN_MOUTH('\uD83D', '\uDE03'),
        SMILING_FACE_WITH_OPEN_MOUTH_AND_SMILING_EYES('\uD83D', '\uDE04'),
        SMILING_FACE_WITH_OPEN_MOUTH_AND_COLD_SWEAT('\uD83D', '\uDE05'),
        SMILING_FACE_WITH_OPEN_MOUTH_AND_TIGHTLY_CLOSED_EYES('\uD83D', '\uDE06'),
        WINKING_FACE('\uD83D', '\uDE09');

        Character firstChar;
        Character secondChar;

        Emoji(Character firstChar, Character secondChar) {
            this.firstChar = firstChar;
            this.secondChar = secondChar;
        }


        Emoji() {

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (this.firstChar != null) {
                sb.append(this.firstChar);
            }
            if (this.secondChar != null) {
                sb.append(this.secondChar);
            }

            return sb.toString();
        }

    }
}