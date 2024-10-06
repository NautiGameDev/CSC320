import java.util.InputMismatchException;
import java.util.Scanner;

public class CSC320_Mod4_Option1 {
 

    public static void main(String [] args)
    {
        Scanner input = new Scanner(System.in);

        int numInputs = 5; //Desired number of inputs the user will enter
        float interestValue = 0.2f; //Desired interest value for calculation
        float[] userInputs = new float[numInputs]; //Array to store input values

        //Primary program loop -> program will end when bool isRunning = false
        boolean isRunning = true;
        while (isRunning)
        {

            System.out.println("\nEnter a total of " + numInputs + " numbers to calculate the total, average, maximum, minimum, and interest.");

            //Loop through number of set inputs to obtain input values from user
            int i = 0;
            for (i=0; i < numInputs; i++)
            {
                GetInputs(userInputs, i, input);
            }
            
            //Perform calculations of total, average, max, min, and interest
            float total = CalcTotal(userInputs);
            float average = CalcAverage(total, userInputs);
            float maximum = FindMax(userInputs);
            float minimum = FindMin(userInputs, total);
            float interest = CalcInterest(total, interestValue);

            //Print values to console
            System.out.println("\nThe total value of your inputs is: " + total);
            System.out.println("The average value of your inputs is: " + average);
            System.out.println("The maximum value of your inputs is: " + maximum);
            System.out.println("The minimum value of your inputs is: " + minimum);
            System.out.println("The calculated interest at " + interestValue * 100 + "% is: " + interest);

            //Get user input to continue or exit the program
            isRunning = GetProgramEndInput(isRunning, input);

        }

        System.out.println("\nProgram closing. Thank you...");
        input.close();
    }

    public static void GetInputs(float[] userInputs, int i, Scanner input)
    {
        //While loop and try-catch blocks used to ensure user puts in a numerical value before continuing the program.
        //If user inputs incorrect value type the loop will repeat to prevent program crashing
        boolean isInputValid = false;
        while (!isInputValid)
        {
            try {
                System.out.println("\nEnter your desired number (input #" + (i+1) + ")");
                userInputs[i] = input.nextFloat();
                isInputValid = true;
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter a valid number.");
                input.nextLine();
            }
        }

    }

    public static float CalcTotal(float[] userInputs)
    {
        float total = 0f;
        int i = 0;

        for (i=0; i < userInputs.length; i++)
        {
            total += userInputs[i];
        }

        return total;
    }

    public static float CalcAverage(float total, float[] userInputs)
    {
        float average = total / userInputs.length;

        return average;
    }

    public static float FindMax(float[] userInputs)
    {
        float maxNum = 0f;
        int i = 0;

        for (i=0; i < userInputs.length; i++)
        {
            if (userInputs[i] > maxNum)
            {
                maxNum = userInputs[i];
            }
        }

        return maxNum;
    }

    public static float FindMin(float[] userInputs, float total)
    {
        float minNum = total;
        int i = 0;

        for (i=0; i < userInputs.length; i++)
        {
            if (userInputs[i] < minNum)
            {
                minNum = userInputs[i];
            }
        }

        return minNum;
    }

    public static float CalcInterest(float total, float interestValue)
    {
        float interest = total * interestValue;

        return interest;
    }
    
    public static boolean GetProgramEndInput(boolean isRunning, Scanner input)
    {
        //Obtain input from the user to continue or close the program.
        // Y = Continue, N = Close the program

        boolean isInputValid = false;
        while (!isInputValid)
        {
            System.out.println("\nWould you like to perform this program again? (Y=Yes, N=No)");

            String userInput = input.next();

            if (userInput.equals("N") || userInput.equals("n"))
            {
                isInputValid = true;
                return false;
            }
            else if (userInput.equals("Y") || userInput.equals("y"))
            {
                isInputValid = true;
                return true;
            }
            else
            {
                System.out.println("\nYour input (" + userInput + ") is invalid.\nPlease only enter y to continue the program, or N to exit.");
            }
        }

        return true;
    }
}
