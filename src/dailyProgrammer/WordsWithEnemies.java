package dailyProgrammer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Easy challenge from daily programmer -- making the string checker method
 * Also the hard challenge from daily programmer. -- making the full game
 * Created by rgw3d on 1/23/2015.
 */
public class WordsWithEnemies {

    private final static String inputPrefix = "--> ";
    private final static String bar = "=============================================";

    private static int aiLevel = 0;
    private static int winNumber = 0;
    private static int playerScore = 0;
    private static int computerScore =0;
    private static int gameRound = 1;

    private static ArrayList<String> library = new ArrayList<String>();

    private static char[] playerChars = new char[10];
    private static char[] computerChars = new char[10];


    public static void main(String args[]) throws FileNotFoundException{

        initLibrary("C:\\Users\\rgw3d\\Desktop\\enable1.txt");
        System.out.println(bar);
        System.out.println("Welcome to Words with Enemies!");
        System.out.println(bar);
        System.out.println();

        selectWinNumber();
        System.out.println(bar);
        selectAiDifficulty();
        System.out.println(bar);



        gameLoop();
    }

    private static void gameLoop() {
        while(computerScore<winNumber && playerScore<winNumber){
            initLetters(playerChars);
            initLetters(computerChars);
            System.out.println(bar);
            System.out.println("\tRound-"+gameRound+" | You-"+playerScore+" | Computer-"+computerScore);
            System.out.println(bar);
            gameRound++;
            System.out.println("Your letters: "+ Arrays.toString(playerChars).replace("[","").replace("]",""));

            String validInput = "";
            while(validInput.equals("")){
                System.out.print("Your word" + inputPrefix);
                String input = new Scanner(System.in).nextLine().toLowerCase().trim();
                if(containedInLetterList(playerChars,input)) {
                    if (Collections.binarySearch(library, input, null) >= 0) {
                        validInput = input;
                        System.out.println("Valid Word!");
                        String computerWord = getComputerWord();
                        System.out.println(input+ " Against "+computerWord);
                        Result result = compareWords(input,computerWord);
                        if(result!= Result.tie){
                            if(result==Result.leftWin){
                                System.out.println("Player wins this round and scores: "+result.getMargin());
                                playerScore+=result.getMargin();
                            }
                            else{
                                System.out.println("Computer wins this round and scores: "+result.getMargin());
                                computerScore+=result.getMargin();
                            }
                        }
                        else{
                            System.out.println("Tie!");
                        }
                    }
                    else
                        System.out.println("Word not in dictionary!");
                }
                else
                    System.out.println("Word cannot be made with your letters");


            }

        }

        if(computerScore>playerScore)
            System.out.println("Darn! You lost: "+computerScore+" to "+ playerScore);
        else
            System.out.println("Yay! You won: "+playerScore+" to "+ computerScore);

        System.out.println("Hope you try again!");
    }

    private static String getComputerWord(){
        ArrayList<String> foundWords = new ArrayList<String>();
        char[] lettersUnused;

        for (String dictWord : library) {
            String createdWord = "";
            lettersUnused = computerChars.clone();
            for (char c : dictWord.toCharArray()) {
                if (binarySearchSucks(lettersUnused,c) >= 0) {
                    lettersUnused[binarySearchSucks(lettersUnused,c)] = ' ';
                    createdWord += c;
                }
                else
                    break;
            }
            if (createdWord.equals(dictWord)) //we made a word!
                foundWords.add(createdWord);
        }
        Collections.sort(foundWords);
            switch (aiLevel) {
                case 1:
                    return foundWords.get(foundWords.size() / 3);
                case 2:
                    return foundWords.get(foundWords.size() / 2);
                case 3:
                    return foundWords.get(foundWords.size() - 1);
                default:
                    return "wow";
            }

    }

    private static boolean containedInLetterList(char[] inputChars,String word){
        char[] chars = inputChars.clone();
        for(char i: word.toCharArray()){
            int indx = binarySearchSucks(chars,i);
            if(indx>=0)
                chars[indx] = ' ';
            else
                return false;
        }
        return true;
    }

    private static int binarySearchSucks(char[] chars,char c){
        for(int i = 0; i<chars.length; i++){
            if(chars[i] == c){
                return i;
            }
        }
        return -1;
    }

    private static void initLetters(char[] chars) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzaaeeiioorstlnrstln";
        Random rnd = new Random();
        for(int i = 0; i<chars.length; i++){


            chars[i] = alphabet.charAt( (int)(rnd.nextDouble() * 40));
        }
    }

    private static void initLibrary(String location) throws FileNotFoundException{
        Scanner file = new Scanner(new File(location));
        while(file.hasNext()) {//read all lines in.  im sure there is a more compact way of doing this.
            library.add(file.next().toLowerCase());
        }
        System.out.println();
        file.close();

    }

    private static void selectWinNumber() {
        System.out.println("Select the win number (any number from 1 to 100)");
        System.out.println("This will determine how many letters it takes to win.");
        System.out.println("\t20 is a good starting number");
        while(winNumber == 0) {
            try {
                System.out.print(inputPrefix);
                int count = new Scanner(System.in).nextInt();
                if(count>0&& count<100)
                    winNumber = count;
                else
                    System.out.println("Please Enter number in range");
            } catch (InputMismatchException ime) {
                System.out.println("Input an integer from 1 to 100");
            }
        }
        System.out.println("You have selected "+winNumber+" as the number needed to win");
    }

    private static void selectAiDifficulty() {
        System.out.println("Select AI Level: 1,2 or 3");
        while(aiLevel == 0 ) {
            System.out.print(inputPrefix);
            try {
                int level = new Scanner(System.in).nextInt();
                if (level>=1 && level<=3)
                    aiLevel = level;
                else
                    System.out.println("Please select number in range");
            }
            catch (InputMismatchException ime) {
                System.out.println("Please enter number 1,2 or 3");
            }
        }
        System.out.println("You have selected level "+aiLevel+ ". GoodLuck!");
    }

    public static Result compareWords(String left, String right){
        for(int i = 0; i<left.length(); i++){
            if(right.contains(left.charAt(i)+"")){
                right = right.replaceFirst(left.charAt(i)+"","");
                left = left.replaceFirst(left.charAt(i)+"","");
                i=0;
            }
        }
        if(left.length()>right.length())
            return Result.leftWin.setMargin(left.length()-right.length());
        else if(right.length()>left.length())
            return Result.rightWin.setMargin(right.length()-left.length());
        else
            return Result.tie;
    }

    public enum Result {
        leftWin,
        rightWin,
        tie;
        private int margin = 0;

        public int getMargin(){
            return margin;
        }
        public Result setMargin(int i){
            margin = i;
            return this;
        }
    }
}