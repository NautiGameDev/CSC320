import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    enum States {MAIN, ADD, UPDATE, REMOVE, VIEW, SAVE, QUIT}
    
    public static void main(String[] args)
    {
        /* Program set-up */
        States currentState = States.MAIN;
        Inventory inventory = new Inventory();
        String[] vehicleDataTypes = {"make", "model", "color", "year", "mileage"};
        Scanner userInput = new Scanner(System.in);
        

        boolean isRunning = true;

        /* Core program loop */
        while (isRunning)
        {
            //Execute code based on state of program
            switch(currentState)
            {
                case MAIN: //Main menu handling
                    PrintMainMenuText();
                    String userChoice = GetUserInput(userInput);
                    currentState = ProcessMainInput(userChoice);
                    break;

                case ADD: //Add vehicle handling
                    PrintAddMenuText();
                    //Get string array of new vehicle data
                    String[] vehicleInformation = ProcessAddMenu(userInput, vehicleDataTypes);

                    if (vehicleInformation != null) //If vehicle is null, user has cancelled vehicle addition process
                    {
                        inventory.AddVehicle(vehicleInformation); //Pass string array of vehicle information to inventory class
                    }
                    
                    //Return to main menu state
                    currentState = States.MAIN;
                    break;          

                case UPDATE:
                    PrintUpdateMenuText();

                    //Get vehicle chose by user to update
                    Automobile chosenVehicle = GetVehicleChoice(userInput, inventory);

                    if (chosenVehicle == null) //If chosen vehicle returns null, user cancelled process
                    {
                        currentState = States.MAIN;
                        break;
                    }

                    //Return string with data type the user wants to update
                    String chosenDataType = GetDataToUpdate(userInput, vehicleDataTypes, chosenVehicle);

                    if (chosenDataType == null)
                    {
                        currentState = States.MAIN;
                        break;
                    }

                    //Return string with new input data
                    String newData = GetNewDataInput(userInput, chosenDataType, chosenVehicle);

                    if (newData == null)
                    {
                        currentState = States.MAIN;
                        break;
                    }

                    //Pass new data and chosen data type to automobile object to update defined variables
                    chosenVehicle.UpdateVehicleData(chosenDataType, newData);

                    //Return to main menu state
                    currentState = States.MAIN;
                    break;

                case REMOVE:
                    PrintRemoveVehicleText();

                    //Return int of inventory index the user wishes to remove
                    int vehicleIndex = ProcessRemoveVehicleMenu(userInput, inventory);

                    //Call inventory method to remove vehicle at user chosen index
                    inventory.RemoveVehicle(vehicleIndex);

                    //Return to main menu state
                    currentState = States.MAIN;
                    break;

                case VIEW:
                    PrintInventory(inventory);

                    boolean isInputValid = false;

                    //While loop exists to provide user time to view inventory. Return to main menu when user confirms.
                    while (!isInputValid)
                    {
                        System.out.println("\nReturn to main menu?");
                        if (GetUserConfirmation(userInput))
                        {
                            currentState= States.MAIN;
                            isInputValid = true;
                        }
                    }
                    
                    break;

                case SAVE:
                    SaveToFile(userInput, inventory);

                    isInputValid = false;

                    //While loop exists to give user time to view saving process. Return to main menu when user confirms.
                    while (!isInputValid)
                    {
                        System.out.println("\nReturn to main menu?");
                        if (GetUserConfirmation(userInput))
                        {
                            currentState= States.MAIN;
                            isInputValid = true;
                        }
                    }
                    break;

                case QUIT:
                //Exit out of program loop and close scanner object.
                    isRunning = false;
                    System.out.println("\nClosing program...\nThank you.");
                    userInput.close();
                    break;                
            }
        }

    }

//#region Input Handling Methods
    static String GetUserInput(Scanner userInput)
    {
        String userChoice = userInput.next();
        return userChoice;
    }

    static boolean GetUserConfirmation(Scanner userInput)
    {
        // Retrieve confirmation from user based on input of y or n

        boolean validInput = false;
        
        System.out.println("Enter Y for yes, or N for no.");

        while (!validInput)
        {
            
            String userChoice = userInput.next();

            if (userChoice.equalsIgnoreCase("y"))
            {
                validInput = true;
                return true;
            }
            else if (userChoice.equalsIgnoreCase("n") || userChoice.equalsIgnoreCase("q"))
            {
                validInput = true;
                return false;
            }
            else
            {
                System.out.println("\nPlease enter Y for yes, or N for no.");
            }
        }

        return false;
    }

    static String RemovePunctuationFromInput(String userChoice)
    {
        //Remove punctuation from user input to prevent errors in object instantiation

        StringBuilder newString = new StringBuilder();
        for (char c : userChoice.toCharArray())
        {
            if (Character.isLetterOrDigit(c))
            {
                newString.append(c);
            }
        }

        return newString.toString();

    }

    static public int ConvertStringToInt(String userChoice)
    {
        // If try-catch method fails, integer returns back as -1 for further processing

        int convertedString = -1;
        try
        {
            convertedString = Integer.parseInt(userChoice);
        }
        catch (Exception e)
        {
            System.out.println("\nError: Input isn't a valid number.");
        }

        return convertedString;

    }

    public static Automobile GetAutomobileFromIndex(Inventory inventory, int chosenNumber)
    {
        //Returns automobile object based on index number
        //Tests if index number is within range of inventory size

        Automobile chosenAutomobile = null;

        try
        {
            chosenAutomobile = inventory.inventoryData.get(chosenNumber);
        }
        catch (Exception e)
        {
            System.out.println("\nInput number is out of range. Please enter a valid number between 1 and " + inventory.inventoryData.size());
        }

        return chosenAutomobile;
    }
//#endregion

//#region Main Menu Methods
    static void PrintMainMenuText()
    {
        //Called at start of switch case
        System.out.println("\n## Main Menu ##");
        System.out.println("Enter a number to perform an action.\n");
        System.out.println("# 1. Add a vehicle");
        System.out.println("# 2. Update a vehicle");
        System.out.println("# 3. Remove a vehicle");
        System.out.println("# 4. View inventory");
        System.out.println("# 5. Save inventory");
        System.out.println("# 6. Quit Program\n");
    }

    static States ProcessMainInput(String userChoice)
    {
        // Switch program state based on user input.

        if (userChoice.equals("1"))
        {
            return States.ADD;
        }
        else if (userChoice.equals("2"))
        {
            return States.UPDATE;
        }
        else if (userChoice.equals("3"))
        {
            return States.REMOVE;
        }
        else if (userChoice.equals("4"))
        {
            return States.VIEW;
        }
        else if (userChoice.equals("5"))
        {
            return States.SAVE;
        }
        else if (userChoice.equals("6"))
        {
            return States.QUIT;
        }
        else
        {
            System.out.println("\nError: Input is invalid.");
        }

        return States.MAIN;
    }
//#endregion

//#region Add Vehicle Methods
    static void PrintAddMenuText()
    {
        //Called at strt of switch case
        System.out.println("\n## Add a vehicle ##");
        System.out.println("Enter vehicle information for each each step.");
        System.out.println("Enter \"Q\" at any time to return to main menu.\n");
    }

    static String[] ProcessAddMenu(Scanner userInput, String[] vehicleDataTypes)
    {
        //Get user input required for vehicle data

        String[] vehicleInformation = new String[5];

        for (int i=0; i<vehicleDataTypes.length; i++)
        {
            System.out.println("\nEnter vehicle " + vehicleDataTypes[i] + ": ");
            String userChoice = GetUserInput(userInput);


            if (userChoice.equalsIgnoreCase("q"))
            {
                System.out.println("\nAdd vehicle cancelled. Returning to main menu...");
                vehicleInformation = null;
                return vehicleInformation;
            }
            else
            {
                userChoice = RemovePunctuationFromInput(userChoice); //Remove punctuation to prevent exceptions
                vehicleInformation[i] = userChoice;
            }
        }

        // Test for confirmation before returning String array
        System.out.println("\nYou entered:");

        for (int j=0; j<vehicleInformation.length; j++)
        {
            System.out.println(vehicleDataTypes[j].toUpperCase() + ": " + vehicleInformation[j]);
        }

        System.out.println("\nIs this correct?");
        if (GetUserConfirmation(userInput))
        {
            return vehicleInformation;
        }
        else
        {
            System.out.println("\n Add vehicle cancelled. Returning to main menu..");
            vehicleInformation = null;
            return vehicleInformation;
        }
    }
//#endregion

//#region Update Vehicle Methods

    static void PrintUpdateMenuText()
    {
        //Called at start of switch case
        System.out.println("\n## Update a vehicle ##");
        System.out.println("Enter the number of the vehicle you wish to update.");
        System.out.println("Enter \"Q\" at any time to return to main menu.");
    }

    static Automobile GetVehicleChoice(Scanner userInput, Inventory inventory)
    {
        //Print inventory for user and allow user to choose vehicle based on numerical input
        System.out.println("\nCurrent inventory:");

        for (int i=0; i<inventory.inventoryData.size(); i++)
        {
            int index = i+1;
            System.out.println("# " + index + ": " + inventory.inventoryData.get(i).GetVehicleInfo());
        }

        boolean isInputValid = false;
        Automobile chosenVehicle = null;

        while(!isInputValid)
        {
            String userChoice = GetUserInput(userInput);

            if (userChoice.equalsIgnoreCase("q"))
            {
                isInputValid = true;
                return chosenVehicle;
            }
            else
            {
                int chosenNumber = ConvertStringToInt(userChoice) - 1;

                if (chosenNumber == -1) { continue; }

                chosenVehicle = GetAutomobileFromIndex(inventory, chosenNumber);

                if (chosenVehicle == null) { continue; }

                isInputValid = true;
            }
        }

        return chosenVehicle;
    }

    static String GetDataToUpdate(Scanner userInput, String[] vehicleDataTypes, Automobile chosenVehicle)
    {
        //Displays chosen vehicle data and chooses data to update based on user input
        System.out.println("\nSelected Vehicle:");
        
        for (int t=0; t<vehicleDataTypes.length; t++)
        {
            int index = t+1;
            System.out.println("# " + index + ": " + vehicleDataTypes[t] + ": " + chosenVehicle.GetVehicleInfo(vehicleDataTypes[t]));
        }

        System.out.println("\nEnter a number to select the information you wish to update.");

        boolean isInputValid = false;
        String chosenDataType = null;

        while(!isInputValid)
        {
            String userChoice = GetUserInput(userInput);

            if (userChoice.equalsIgnoreCase("q"))
            {
                isInputValid = true;
                return chosenDataType;
            }

            int chosenNumber = ConvertStringToInt(userChoice) - 1;

            if (chosenNumber == -1) { continue; }

            if (chosenNumber <= vehicleDataTypes.length-1 && chosenNumber >= 0)
            {
                chosenDataType = vehicleDataTypes[chosenNumber];
                isInputValid = true;
            }
            else
            {

                System.out.println("\nNumber input is out of range. Please enter a number between 1 and " + vehicleDataTypes.length);
            }
        }

        return chosenDataType;
    }

    static String GetNewDataInput(Scanner userInput, String dataType, Automobile chosenVehicle)
    {
        //Retrieves new data from user for updating chosen vehicle
        System.out.println("\nEnter new value to update " + dataType + " of " + chosenVehicle.year + " " + chosenVehicle.make + " " + chosenVehicle.model);
        String userChoice = userInput.next();

        if (userChoice.equalsIgnoreCase("q"))
        {
            return null;
        }

        System.out.println("\nUpdating " + dataType + " to " + userChoice + ".");
        System.out.println("Previous value: " + chosenVehicle.GetVehicleInfo(dataType));
        System.out.println("Is this correct?");

        if (GetUserConfirmation(userInput))
        {
            return RemovePunctuationFromInput(userChoice);
        }
        else
        {
            return null;
        }
    }

//#endregion

//#region Remove Vehicle Methods

    static void PrintRemoveVehicleText()
    {
        //Called at start of switch case
        System.out.println("\n## Remove a vehicle ##");
        System.out.println("Enter a numer to select the vehicle you want to remove.");
        System.out.println("Enter \"Q\" at any time to return to the main menu.\n");
    }

    static int ProcessRemoveVehicleMenu(Scanner userInput, Inventory inventory)
    {
        //Print inventry and get chosen vehicle based on user input
        System.out.println("\nCurrent inventory:");

        for (int i=0; i<inventory.inventoryData.size(); i++)
        {
            int index = i+1;
            System.out.println("# " + index + ": " + inventory.inventoryData.get(i).GetVehicleInfo());
        }

        boolean isInputValid = false;
        int convertedChoice = -1;

        while (!isInputValid)
        {
            String userChoice = userInput.next();

            if (userChoice.equalsIgnoreCase("q")) 
            {
                return -1;
            }

            convertedChoice = ConvertStringToInt(userChoice);

            if (convertedChoice == -1)
            {
                continue;
            }
            else
            {
                isInputValid = true;
            }
        }
        
        //Get user confirmation
        System.out.println("\nRemove " + inventory.inventoryData.get(convertedChoice - 1).GetVehicleInfo());
        System.out.println("Is this correct?");

        if (GetUserConfirmation(userInput))
        {
            return convertedChoice - 1;
        }
        else{
            return -1;
        }
    }

//#endregion

//#region View Inventory Methods

    static void PrintInventory(Inventory inventory)
    {
        //Prints inventory to console for user viewing
        System.out.println("\n## Current Inventory ##\n");

        if (inventory.inventoryData.size()>0)
        {
            for (int i=0; i<inventory.inventoryData.size(); i++)
            {
                System.out.println("# " + inventory.inventoryData.get(i).GetVehicleInfo());
            }
        }
        else
        {
            System.out.println("Inventory is empty.");
        }
    }

//#endregion

//#region Save File Methods

    static void SaveToFile(Scanner userInput, Inventory inventory)
    {
        //Saves inventory to file after user confirmation. If file exists, ask user to confirm overwriting file data.
        System.out.println("\n## Saving Inventory Data ##");
        String filePath = "vehicleInventory.txt";
        
        PrintInventory(inventory);

        System.out.println("\nSave inventory to " + filePath + " ?");
        
        if (!GetUserConfirmation(userInput))
        {
            return;
        }

        try 
        {
            File file = new File(filePath);
            if(file.createNewFile())
            {
                //Creates new file and writes inventory to text document
                System.out.println("\nFile created: " + filePath);

                FileWriter fileWriter = new FileWriter(filePath);

                for (Automobile vehicle : inventory.inventoryData)
                {
                    System.out.println("Saving: " + vehicle.GetVehicleInfo());
                    fileWriter.write(vehicle.GetVehicleInfo() + "\n");
                }

                fileWriter.close();
                System.out.println("\nData saved successfully");
            }
            else
            {
                //Asks user if they wish to overwrite text document with new data.

                System.out.println("\nA file with that name already exists. Overwrite file with current inventory data?");

                if (!GetUserConfirmation(userInput))
                {
                    return;
                }

                FileWriter fileWriter = new FileWriter(filePath);

                for (Automobile vehicle : inventory.inventoryData)
                {
                    System.out.println("Saving: " + vehicle.GetVehicleInfo());
                    fileWriter.write(vehicle.GetVehicleInfo() + "\n");
                }

                fileWriter.close();
                System.out.println("\nData saved successfully");
            }
        }
        catch (IOException e)
        {
            System.out.println("\nAn error has occurred while creating " + filePath);
        }
    }

//#endregion

}