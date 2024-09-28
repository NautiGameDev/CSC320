import java.util.Scanner;

public class CSC320_Mod3_Option1 {
    public static void main(String [] args)
    {
        // Init variables
        Scanner input = new Scanner(System.in);
        float weeklyIncome;
        float taxes;

        //Obtain user input
        System.out.println("\nEnter weekly income:");
        weeklyIncome = input.nextFloat();

        System.out.println("\nYou entered $" + weeklyIncome + " for your weekly income.\n");

        // Conditional statements
        if (weeklyIncome < 500)
        {
            taxes = weeklyIncome * .1f;
        }
        else if (weeklyIncome < 1500)
        {
            taxes = weeklyIncome * .15f;
        }
        else if (weeklyIncome < 2500)
        {
            taxes = weeklyIncome * .2f;
        }
        else
        {
            //Calculation if weekly income is greater than or equal to $2500
            taxes = weeklyIncome * .3f;
        }

        //Output and program closing
        float roundedTaxes = (float)Math.round(taxes * 100) / 100;
        System.out.println("Your tax witholding for the week is: $" + roundedTaxes + "\n");

        input.close();

    }
}