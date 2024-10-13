import java.util.Scanner;
import java.util.Random;

public class CSC320_Mod5_Option1 {
    public static void main(String[] args)
    {
        //Instantiate imported objects
        Scanner userInput = new Scanner(System.in);
        Random random = new Random();

        //Instantiate arrays
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int[] dailyTemps = GenerateTemperatures(random); //Calls method to randomly generate temperatures and returns as an array

        //Main program loop
        boolean programRunning = true;
        while (programRunning)
        {
            System.out.println("\nEnter a day of the week to see the daily average temperature, or week to see the weekly temperature average.");
            System.out.println("To exit the program, enter Q\n");

            //Create input loop to ensure valid input
            boolean isInputValid = false;
            while (!isInputValid)
            {
                String userPrompt = userInput.next();
                
                int temperatureOutput = GetDailyTemperature(days, dailyTemps, userPrompt);

                //If temperatureOutput does not equal 0, the user entered a day of the week.
                //Prints average temperature for entered day of the week
                if (temperatureOutput != 0)
                {
                    String day = GetDay(days, userPrompt);
                    System.out.println("\nThe average daily temperature for " + day + " is " + temperatureOutput + " degrees.");            
                    isInputValid = true;
                }

                //Calls function to print average temperature for each day plus the weekly average
                else if (userPrompt.equalsIgnoreCase("week"))
                {
                    GetWeeklyTemperatures(dailyTemps, days);
                    isInputValid = true;
                }

                //Exit program loop if user enters Q
                else if (userPrompt.equalsIgnoreCase("Q"))
                {
                    programRunning = false;
                    System.out.println("\nProgram closing...");
                    return;
                }
                
                //If previous conditions aren't met, input is invalid and the input loop repeats.
                else
                {
                    System.out.println("\nPlease enter a valid input (Ex: Monday or Week) to see an average temperature.");
                    
                }
            }

            //Calls a function that asks user if they wish to repeat program, updates while loop condition based on returned boolean.
            programRunning = TestRepeatProgram(userInput);
        }

        userInput.close();

    }

    public static int[] GenerateTemperatures(Random random)
    {
        //Randomly generate average temperatures for each day.

        //Set-up numbers to get temperatures between baseTemp and (baseTemp + maxTemp)
        int[] temperatures = new int[7];
        int baseTemp = 60;
        int maxTemp = 30;

        for (int i=0; i < temperatures.length; i++)
        {
            int randomTemp = random.nextInt(maxTemp) + baseTemp;
            temperatures[i] = randomTemp;
        }

        return temperatures;
    }


    public static int GetDailyTemperature(String[] days, int[] dailyTemps, String userPrompt)
    {
        //Get temperature based on string of user input and return to main program loop

        int temperature = 0;

        for (int i=0; i<days.length; i++)
        {
            if (days[i].equalsIgnoreCase(userPrompt))
            {
                temperature = dailyTemps[i];
                break;
            }
        }

        return temperature;
    }

    public static String GetDay(String[] days, String userPrompt)
    {
        //Get day of the week user entered and return string of that day.
        //This just exists for asthetic purposes to force capitalization of the day when printing information.

        String day = "n/a";

        for (int i=0; i<days.length; i++)
        {
            if (userPrompt.equalsIgnoreCase(days[i]))
            {
                day = days[i];
                break;
            }
        }

        return day;
    }

    public static void GetWeeklyTemperatures(int[] dailyTemps, String[] days)
    {
        System.out.println(" "); //Empty for spacing in terminal

        int tempTotal = 0;

        //Loops through arrays and prints average daily temperature for each day.
        //Adds the daily temperature to temperature total.
        for (int i=0; i<days.length; i++)
        {
            System.out.println("The average daily temperature for " + days[i] + " is " + dailyTemps[i] + " degrees.");
            tempTotal += dailyTemps[i];
        }

        //Calculates and prints the average weekly temperature.
        float averageTemp = tempTotal/days.length;
        System.out.println("\nThe average temperature for the week is " + averageTemp + " degrees.");
    }

    public static boolean TestRepeatProgram(Scanner userInput)
    {
        //Asks user to enter y or n to repeat the program. Returns boolean to update program while loop condition.
        System.out.println("\nWould you like to enter a new parameter?\nY = Yes   N = No");
        
        //While loop to ensure user input is valid. Repeats if invalid.
        boolean isInputValid = false;
        while (!isInputValid)
        {

            String userPrompt = userInput.next();

            if (userPrompt.equalsIgnoreCase("Y"))
            {
                isInputValid = true;
                return true;
            }
            else if (userPrompt.equalsIgnoreCase("N"))
            {
                isInputValid = false;
                System.out.println("\nProgram closing...");
                return false;
            }
            else
            {
                System.out.println("\nPlease enter either Y to repeat the program, or N to exit the program.");
            }

        }

        return false;
    }
}