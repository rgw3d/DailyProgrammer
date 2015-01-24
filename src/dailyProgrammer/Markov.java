package dailyProgrammer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Markov  {
        /*
              a b c d
            a[][][][]
            b[][][][]
            c[][][][]

            left followed by top
            storage pattern for the occurence chain
        */
    public static double[][] occurrenceChain = new double[26][28];

    public static void main(String[] args) throws FileNotFoundException{//arguments should be a link to the wordlist, and then a word to compare
        ArrayList<String> lines = new ArrayList<String>();
        Scanner file = new Scanner(new File(args[0]));
        while(file.hasNext())//read all lines in.  im sure there is a more compact way of doing this.
            lines.add(file.next().toLowerCase());
        file.close();

        for(String word: lines){
            char[] chars = word.toCharArray();
            for(int i = 0; i<word.length()-2; i++)//add up all the occurrences
                occurrenceChain[chars[i]-97][chars[i+1]-97]+=1;//store all the occurrences
        }

        for(double[] x: occurrenceChain){//add them all up and store the count in the index[26] of the array
            int count =0;
            for(double y: x)
                count+=y;
            x[26]=count;
        }

        for(double[] row: occurrenceChain){//using the total count at the end of every row in occurrencechain[] to find the highest percent
            for(double indx: row){
                if(indx == row[26])//end once it reaches the total count index
                    continue;
                if(indx/row[26]/(1.0/26) > row[27]){//find the highest percent that one character set has in one row
                    row[27] = indx/row[26]/(1.0/26);//in other words, find the character occurrence that happens most in that row.
                }//for example, in the q row, u follows it the most and has the highest percent.
            }   //this is used to percents bellow.  its kinda arbitrary, but what the hell.
        }

        double[] result = new double[args[1].length()-1];//percents that I assign bellow
        String[] resultString = new String[args[1].length()-1];//this just stores what character combinations happen

        char[] singleWordChar = args[1].toCharArray();
        for(int i = 0; i<args[1].length()-1; i++) {
            result[i] = (occurrenceChain[singleWordChar[i]-97][singleWordChar[i+1]-97] /
                    occurrenceChain[singleWordChar[i]-97][26] /
                    (1.0/26))/occurrenceChain[singleWordChar[i]-97][27];
            resultString[i] = singleWordChar[i] + "" +singleWordChar[i+1];//store the character combinations
        }



        System.out.println(Arrays.toString(result));//print both.
        System.out.println(Arrays.toString(resultString));

        double count=0;//arithmetic mean
        for(double x: result){
            count+=x;
        }
        System.out.println(count/result.length);//print it out

        count = 1;//geometric mean.  multiply everything, and apply nth root
        for(double x: result){
            count*=x;
        }
        System.out.println(Math.pow(count,(1.0/result.length)));//print it out

        /*for(double[] x: occurrenceChain){
            System.out.println(Arrays.toString(x));
            //used to print out all of the characters in the large occurence array.
          */
        }


    }


