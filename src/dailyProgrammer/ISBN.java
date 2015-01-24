package dailyProgrammer;

import static java.lang.Integer.parseInt;

public class ISBN {

    public static void main(String[] args) {
        System.out.println(validISBN(args[0].replace("-", "").replace(" ", "")));
    }
    public static boolean validISBN(String isbn){
        return ((parseInt(isbn.charAt(0)+"")*10)+(parseInt(isbn.charAt(1)+"")*9)+(parseInt(isbn.charAt(2)+"")*8)+(parseInt(isbn.charAt(3)+"")*7)+(parseInt(isbn.charAt(4)+"")*6)+(parseInt(isbn.charAt(5)+"")*5)+(parseInt(isbn.charAt(6)+"")*4)+(parseInt(isbn.charAt(7)+"")*3)+(parseInt(isbn.charAt(8)+"")*2)+(parseInt((isbn.charAt(9)+"").equals("X")?"10":isbn.charAt(9)+"")))%11==0;
    }
}
