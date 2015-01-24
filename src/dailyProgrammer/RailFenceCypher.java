package dailyProgrammer;

import java.util.Scanner;

public class RailFenceCypher {

    public static Scanner kb = new Scanner(System.in);
    public static void main(String[] args) {
        String line;
        while(!(line = kb.nextLine()).equals("")){
            if(line.startsWith("enc")){
                int num = Integer.parseInt(line.charAt(4)+"");
                String word = line.substring(5).trim();
                String enc ="";
                for(int i = 0; i<word.length(); i+=(num-1+num-1))
                    enc+=word.charAt(i);
                for(int i = 1; i<word.length(); i+=2)
                    enc+=word.charAt(i);
                for(int i = 2; i<word.length(); i+=(num-1+num-1))
                    enc+=word.charAt(i);
                System.out.println(enc);


                for(int x = 0; x<word.length()-1; x++){
                    if(x==0 || x == word.length()-1){
                        for(int i=x; i<word.length(); i+=2*num-2)
                            enc+=word.charAt(i);
                    }
                    else{
                        for(int i = x ; i<word.length(); i+=(i%2==0)?2*x-4:3 ){

                        }
                    }


                }
            }
        }

    }
}


/*


R   I   M   I   R   A   R   7
 E D T O R A L P O R M E   12
  D   C   D   Y   G   M    6

R       I         M  3          I   R   A   R
 E     D T      A   4           O R A L P O R M E
  D  A    C   A    5             D   Y   G   M
   A        A       2


 */