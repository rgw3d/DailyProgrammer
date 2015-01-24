    package dailyProgrammer;

    import java.util.Arrays;

    public class CrazyProfessor {

        public static void main(String[] args) {
            SieveOfAtkin.main();

            SieveOfAtkin.sieve = null;//free memory on my laptop

            boolean[] x = new boolean[SieveOfAtkin.limit];//index 0 is number 1
            Arrays.fill(x,true);

            for(int i = 0;i< SieveOfAtkin.maxValueIndex; i++){
                int value = SieveOfAtkin.listOfPrimes[i];
                for(;value<SieveOfAtkin.maxValue;value+=SieveOfAtkin.listOfPrimes[i]){
                    x[value-1] = false;
                }
            }
            int count = 0;
            for(int i = 0; i<SieveOfAtkin.limit;i++){
                if(x[i])
                    count++;
                if(count==1000000){
                    System.out.println("1 million at this index: " +i);
                    break;
                }
            }
            System.out.println(count);

        }

        public static class SieveOfAtkin {
            public static int limit = 900000000;
            public static boolean[] sieve = new boolean[limit+1];
            private static int limitSqrt = (int)Math.sqrt((double)limit);
            public static int listOfPrimes[] = new int[limit/10];
            public static int maxValue = 0;
            public static int maxValueIndex = 0;

            public static void main() {

                //listOfPrimes = new int[limit];
                Arrays.fill(sieve, false);

                for (int x = 1; x <= limitSqrt; x++) {
                    for (int y = 1; y <= limitSqrt; y++) {
                        // first quadratic using m = 12 and r in R1 = {r : 1, 5}
                        int n = (4 * x * x) + (y * y);
                        if (n <= limit && (n % 12 == 1 || n % 12 == 5)) {
                            sieve[n] = !sieve[n];
                        }
                        // second quadratic using m = 12 and r in R2 = {r : 7}
                        n = (3 * x * x) + (y * y);
                        if (n <= limit && (n % 12 == 7)) {
                            sieve[n] = !sieve[n];
                        }
                        // third quadratic using m = 12 and r in R3 = {r : 11}
                        n = (3 * x * x) - (y * y);
                        if (x > y && n <= limit && (n % 12 == 11)) {
                            sieve[n] = !sieve[n];
                        }
                    }
                }
                for (int n = 5; n <= limitSqrt; n++) {
                    if (sieve[n]) {
                        int x = n * n;
                        for (int i = x; i <= limit; i += x) {
                            sieve[i] = false;
                        }
                    }
                }
                int j = 0;
                for (int i = 0; i <= limit; i++) {
                    if (sieve[i]) {
                        if (i>20)
                            listOfPrimes[j++] = i;
                    }
                }
                maxValue = listOfPrimes[j-1];
                maxValueIndex = j;

            }
        }








    }


    /*



    He's at it again, the professor at the department of Computer Science has posed a question to all his students knowing that they can't brute-force it. He wants them all to think about the efficiency of their algorithms and how they could possibly reduce the execution time.
    He posed the problem to his students and then smugly left the room in the mindset that none of his students would complete the task on time (maybe because the program would still be running!).
    The problem
    What is the 1,000,000th number that is not divisble by any prime greater than 20?


    NOTE
    counting will start from 1. Meaning that the 1000000th number is the 1000000th number and not the 999999th number.





     */