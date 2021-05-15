public class Calculator {
    private String expression;
    private int pos;
    private enum Operation {SUM, DIFF, MULT, DIV, NONE};

    private Calculator(String expression) {
        this.expression = expression;
    }

    private String getNext() throws Exception {
        if (hasNext()) {
            return expression.substring(pos++,pos);
        } else
            throw new Exception("unexpected end of expression");
    }
    private String checkNext() throws Exception {
        if (hasNext())
            return expression.substring(pos,pos+1);
        else
            return "";
    }
    private boolean hasNext() {
        return pos < expression.length();
    }

    int getNumber() throws Exception {
        String num = getNext();
        return Integer.valueOf(num);
    }

    Operation getOperation(String op) throws Exception {
        if (op.length()>0)
            switch (op.toCharArray()[0]) {
                case ')': return Operation.NONE;
                case '+': return Operation.SUM;
                case '-': return Operation.DIFF;
                case '*': return Operation.MULT;
                case '/': return Operation.DIV;
            }
        throw new Exception("unknown operation " + op);
    }

    Operation getOperation() throws Exception {
        String op = getNext();
        return getOperation(op);
    }

    Operation checkOperation() throws Exception {
        String op = checkNext();
        return getOperation(op);
    }

    private int getTerm2() throws Exception {
        String br = checkNext();
        if (br.equals("(")) {
            getNext();
            int expr = expression();
            if (!getNext().equals(")"))
                throw new Exception("\")\" expected");
            return expr;
        }
        return getNumber();
    }

    // parser
    private int getTerm() throws Exception {
        int res = getTerm2();
        while (hasNext()) {
            Operation op = checkOperation();
            if (op == Operation.MULT || op == Operation.DIV) {
                op = getOperation();
                int num = getTerm2();
                switch (op) {
                    case MULT:
                        res *= num;
                        break;
                    case DIV:
                        res /= num;
                        break;
                    default:
                        throw new Exception("internal error: invalid operation " + op);
                }
            }  else
                return res;
        }
        return res;
    }

    private int expression() throws Exception {
        int res = getTerm();
        while (hasNext()) {
            Operation op = checkOperation();
            if (op == Operation.SUM || op == Operation.DIFF) {
                op = getOperation();
                int num = getTerm();
                switch (op) {
                    case SUM:
                        res += num;
                        break;
                    case DIFF:
                        res -= num;
                        break;
                    default:
                        throw new Exception("internal error: invalid operation " + op);
                }
            } else
                return res;
        }
        return res;
    }

    private int calculate() throws Exception {
        return expression();
    }

    public static int calculate(String expression) throws Exception {
        return new Calculator(expression).calculate();
    }

   /* public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String s: args) {
            sb.append(s);
        }
        if(sb.length()<=0) throw new RuntimeException("Parameters are not provided. Example: (2+3)-1*5");
        System.out.println(Calculator.calculate(sb.toString()));
    }*/
}
