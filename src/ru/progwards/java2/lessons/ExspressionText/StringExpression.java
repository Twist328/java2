package ru.progwards.java2.lessons.ExspressionText;

import java.util.ArrayList;
import java.util.List;

public class StringExpression {
    /*------------------------------------------------------------------
     * PARSER RULES
     *------------------------------------------------------------------*/

//    expr : plusminus* EOF ;
//
//    plusminus: multdiv ( ( '+' | '-' ) multdiv )* ;
//
//    multdiv : factor ( ( '*' | '/' ) factor )* ;
//
//    factor : NUMBER | '(' expr ')' ;


    public static void main(String[] args) {
        String expression = "1+3*2-9/3*(9+7)-9";
        List<Symbol> symbols = symAnalyze(expression);
        SymbolBuffer symbolBuffer = new SymbolBuffer(symbols);

        String expression1 = "1+3*2-9/3*9";
        List<Symbol> symbols1 = symAnalyze(expression1);
        SymbolBuffer symbolBuffer1 = new SymbolBuffer(symbols1);

        System.out.println("****************************************");
        System.out.println("результат вычислений N1:    "+ expr(symbolBuffer));
        System.out.println("результат вычислений N2:    "+ expr(symbolBuffer1));
        System.out.println("****************************************");
    }

    public enum SymbolType {
        LEFT_BRACKET, RIGHT_BRACKET,
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIV,
        NUMBER, EOF;
    }

    public static class Symbol {
        SymbolType type;
        String value;

        public Symbol(SymbolType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Symbol(SymbolType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public static class SymbolBuffer {
        private int pos;

        public List<Symbol> symbols;

        public SymbolBuffer(List<Symbol> symbols) {
            this.symbols = symbols;
        }

        public Symbol next() {
            return symbols.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }

    public static List<Symbol> symAnalyze(String expText) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        int pos = 0;
        while (pos< expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    symbols.add(new Symbol(SymbolType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    symbols.add(new Symbol(SymbolType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    symbols.add(new Symbol(SymbolType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    symbols.add(new Symbol(SymbolType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    symbols.add(new Symbol(SymbolType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    symbols.add(new Symbol(SymbolType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        symbols.add(new Symbol(SymbolType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: " + c);
                        }
                        pos++;
                    }
            }
        }
        symbols.add(new Symbol(SymbolType.EOF, ""));
        return symbols;
    }

    public static int expr(SymbolBuffer symbols) {
        Symbol symbol = symbols.next();
        if (symbol.type == SymbolType.EOF) {
            return 0;
        } else {
            symbols.back();
            return plusminus(symbols);
        }
    }

    public static int plusminus(SymbolBuffer symbols) {
        int value = multdiv(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) {
                case OP_PLUS:
                    value += multdiv(symbols);
                    break;
                case OP_MINUS:
                    value -= multdiv(symbols);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                    symbols.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + symbol.value
                            + " at position: " + symbols.getPos());
            }
        }
    }

    public static int multdiv(SymbolBuffer symbols) {
        int value = factor(symbols);
        while (true) {
            Symbol symbol = symbols.next();
            switch (symbol.type) {
                case OP_MUL:
                    value *= factor(symbols);
                    break;
                case OP_DIV:
                    value /= factor(symbols);
                    break;
                case EOF:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    symbols.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + symbol.value
                            + " at position: " + symbols.getPos());
            }
        }
    }

    public static int factor(SymbolBuffer symbols) {
        Symbol symbol = symbols.next();
        switch (symbol.type) {
            case NUMBER:
                return Integer.parseInt(symbol.value);
            case LEFT_BRACKET:
                int value = plusminus(symbols);
                symbol = symbols.next();
                if (symbol.type != SymbolType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + symbol.value
                            + " at position: " + symbols.getPos());
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token: " + symbol.value
                        + " at position: " + symbols.getPos());
        }
    }

}
