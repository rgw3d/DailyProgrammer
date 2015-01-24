package dailyProgrammer;

public class BaseNegativeNumbers {

    public static void main(String[] args) {//two numbers as input, the base and the value in that base
        int base = Integer.parseInt(args[0]);

        if(base<0){
            int value = 0;
            for(int i = 0; i<args[1].length(); i++)
                value+=Integer.parseInt(args[1].charAt(args[1].length()-1-i)+"") * Math.pow(base,i);//number always comes out in base 10
            System.out.println(Integer.toString(value,base * -1));
        }
        else{
            String value = "";
            //value = x+value;
            int remainder = Integer.parseInt(args[1]);

            while(remainder!=0){
                value = remainder%base + value;
                remainder = (int)remainder/base;
            }
            System.out.println(value);
        }


    }
}
