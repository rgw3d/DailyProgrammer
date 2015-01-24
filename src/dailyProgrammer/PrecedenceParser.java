package dailyProgrammer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PrecedenceParser {
    public static String eqt;
    public static ArrayList<OperatorInput> opList = new ArrayList<OperatorInput>();

    public static void main(String[] args) {
        input();
        Collections.reverse(opList);
        System.out.println(parseEquation(eqt).toString());
    }

    private static void input() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Number of operators: ");
        int num = kb.nextInt()+1;
        while(--num>0){
            System.out.print("Operator:Associativity ");
            String in = kb.next();
            opList.add(new OperatorInput(in.charAt(0)+"", in.substring(2).equals("right")));
        }
        System.out.print("Equation: ");
        eqt = new Scanner(System.in).nextLine().trim().replace(" ","");
    }

    public static class OperatorInput {
        public OperatorInput(String op, boolean right){
            this.op = op;
            this.right = right;
        }
        public String op;
        public boolean right;
    }

    public static class Operator implements OperatorInterface{
        public Operator(String value){
            this.value = value;
        }
        String value;
        ArrayList<OperatorInterface> operatorArrayList = new ArrayList<OperatorInterface>();
        public void addTerm(OperatorInterface oi){
            operatorArrayList.add(oi);
        }

        public String toString(){
            return "("+operatorArrayList.get(0).toString()+value+operatorArrayList.get(1).toString()+")";

        }
    }

    public static class Number implements  OperatorInterface{
        int value;
        public Number(int value){
            this.value = value;
        }
        public String toString(){
            return value+"";
        }
    }

    public static interface OperatorInterface{
        public String toString();
    }


    public static OperatorInterface parseEquation(String input) {

        for (OperatorInput operatorInput : opList) {
            boolean hasParen = false;
            if (operatorInput.right) {//right leaning
                for (int indx = input.length() - 1; indx > 0; indx--) {
                    String eqtIndx = "" + input.charAt(indx);
                    if (eqtIndx.equals(")")) {
                        hasParen = true;
                        indx = skipParenRight(input, indx);//skips paren and sets indx to its proper "skipped" value
                        continue;
                    }
                    if (eqtIndx.equals(operatorInput.op)) {//a hit
                        Operator operator = new Operator(operatorInput.op);
                        operator.addTerm(parseEquation(input.substring(0, indx)));//left side
                        operator.addTerm(parseEquation(input.substring(indx + 1)));//right side
                        return operator;//everything is recursive
                    }
                }

                if (hasParen && operatorInput == opList.get(opList.size() - 1)) {//loop inside the parenthesis because otherwise it would have returned an operator
                    return parseEquation(input.substring(1, input.length() - 1));//trim the parenthesis

                } else if (!hasParen && operatorInput == opList.get(opList.size() - 1)) {
                    return new Number(Integer.parseInt(input));
                }
            } else {//for the left
                for (int indx = 0; indx < input.length() - 1; indx++) {
                    String eqtIndx = "" + input.charAt(indx);
                    if (eqtIndx.equals("{")) {
                        hasParen = true;
                        indx = skipParenLeft(input, indx);//skips paren and sets indx to its proper "skipped" value
                        continue;
                    }
                    if (eqtIndx.equals(operatorInput.op)) {//a hit
                        Operator operator = new Operator(operatorInput.op);
                        operator.addTerm(parseEquation(input.substring(0, indx)));//left side
                        operator.addTerm(parseEquation(input.substring(indx + 1)));//right side
                        return operator;//everything is recursive
                    }
                }

                if (hasParen && operatorInput == opList.get(opList.size() - 1)) {//loop inside the parenthesis because otherwise it would have returned an operator
                    return parseEquation(input.substring(1, input.length() - 1));//trim the parenthesis

                } else if (!hasParen && operatorInput == opList.get(opList.size() - 1)) {
                    if(input.startsWith("(")){
                        input = input.substring(1);
                    }
                    if(input.endsWith(")")){
                        input = input.substring(0,input.length()-1);
                    }
                    return new Number(Integer.parseInt(input));
                }
            }
        }

        throw new UnsupportedOperationException("Something went horribly wrong");
    }

    public static int skipParenRight(String input, int indx) {
        int openCount = 0;
        int closedCount = 1;
        while (indx > 0) {
            indx--;
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count
            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count
            if (openCount == closedCount)
                return indx;
        }

        throw new UnsupportedOperationException("Missing Parenthesis Pair");
    }

    public static int skipParenLeft(String input, int indx) {
        int openCount = 1;
        int closedCount = 0;
        while (indx < input.length()-1) {
            indx++;
            if ((input.charAt(indx) + "").equals(")"))
                closedCount++;//increment closed count
            if ((input.charAt(indx) + "").equals("("))
                openCount++;//increment open count
            if (openCount == closedCount)
                return indx;
        }

        throw new UnsupportedOperationException("Missing Parenthesis Pair");
    }

}