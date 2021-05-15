public class Calculator {
    private static String INFO;
    private int pos;
    private enum Action {SUM, DIFF, MULT, DIV, NOTHING};

    private Calculator(String info) {
        this.INFO = info;
    }
    private int getExpir() throws Exception {
        int res = getOperation();
        while (hasNext()) {
            Action op = checkAction();
            if (op == Action.MULT || op == Action.DIV) {
                op = getAction();
                int num = getOperation();
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
    private String getNext() throws Exception {
        if (hasNext()) {
            return INFO.substring(pos++,pos);
        } else
            throw new Exception("unexpected end of expression");
    }
    private String checkNext() throws Exception {
        if (hasNext())
            return INFO.substring(pos,pos+1);
        else
            return "";
    }
    private boolean hasNext() {
        return pos < INFO.length();
    }

    int getNumber() throws Exception {
        String num = getNext();
        return Integer.valueOf(num);
    }

    Action getAction(String oper) throws Exception {
        if (oper.length()>0)
            switch (oper.toCharArray()[0]) {
                case ')': return Action.NOTHING;
                case '+': return Action.SUM;
                case '-': return Action.DIFF;
                case '*': return Action.MULT;
                case '/': return Action.DIV;
            }
        throw new Exception("unknown operation " + oper);
    }

    Action getAction() throws Exception {
        String op = getNext();
        return getAction(op);
    }

    Action checkAction() throws Exception {
        String op = checkNext();
        return getAction(op);
    }

    private int getOperation() throws Exception {
        String br = checkNext();
        if (br.equals("(")) {
            getNext();
            int norm = information();
            if (!getNext().equals(")"))
                throw new Exception("\")\" expected");
            return norm;
        }
        return getNumber();
    }

    private int information() throws Exception {
        int res = getExpir();
        while (hasNext()) {
            Action op = checkAction();
            if (op == Action.SUM || op == Action.DIFF) {
                op = getAction();
                int num = getExpir();
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

    public int calculation() throws Exception {
        return information();
    }

    public static int calc(String data) throws Exception {
        return new Calculator(data).calculation();
    }

}
