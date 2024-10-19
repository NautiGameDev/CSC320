public class Car {
    public String make;
    public String model;
    public String color;
    public String style;
    public int year;
    public int mileage;
    public int mpg;

    public Car(String make, String model, String color, String style, int year, int mileage, int mpg)
    {
        this.make = make;
        this.model = model;
        this.color = color;
        this.style = style;
        this.year = year;
        this.mileage = mileage;
        this.mpg = mpg;
    }

    public void SetCarInformation(String option, String newInput)
    {
        if (option.equalsIgnoreCase("make")) 
            this.make = newInput;

        else if (option.equalsIgnoreCase("model")) 
            this.model = newInput;
        
        else if (option.equalsIgnoreCase("color")) 
            this.color = newInput;

        else if (option.equalsIgnoreCase("style")) 
            this.style = newInput;

        else if (option.equalsIgnoreCase("year"))
            this.year = ConvertStringToInt(newInput, this.make, this.model, this.year);
        
        else if (option.equalsIgnoreCase("mileage"))
            this.mileage = ConvertStringToInt(newInput, this.make, this.model, this.mileage);

        else if (option.equalsIgnoreCase("mpg"))
            this.mpg = ConvertStringToInt(newInput, this.make, this.model, this.mpg);
    }

    public static int ConvertStringToInt(String userInput, String make, String model, int previousValue)
    {
        try
                {
                    return Integer.parseInt(userInput);
                }
        catch (Exception exception)
            {
                System.out.println("\nFailed to update " + make + " " + model + ". Ensure input is an integer.");
                return previousValue;
            }
    }

    public void GetCarInformation(String parameters)
    {
        String[] params = parameters.split(" ");

        for (String param : params)
        {
            if (param.equalsIgnoreCase("make"))
                System.out.println("Make: " + this.make);

            else if (param.equalsIgnoreCase("model"))
                System.out.println("Model: " + this.model);

            else if (param.equalsIgnoreCase("color"))
                System.out.println("Color: " + this.color);

            else if (param.equalsIgnoreCase("style"))
                System.out.println("Style: " + this.style);

            else if (param.equalsIgnoreCase("year"))
                System.out.println("Year: " + this.year);

            else if (param.equalsIgnoreCase("mileage"))
                System.out.println("Mileage: " + this.mileage);

            else if (param.equalsIgnoreCase("mpg"))
                System.out.println("MPG: " + this.mpg);

            else
                System.out.println("Error: " + param + " isn't a valid parameter.");
        }
    }

    public boolean TestForRemoval(String vehicleMake, String vehicleModel, String vehicleColor, int vehicleYear)
    {
        if (vehicleMake.equalsIgnoreCase(this.make) &&
            vehicleModel.equalsIgnoreCase(this.model) &&
            vehicleColor.equalsIgnoreCase(this.color) &&
            vehicleYear == this.year)
                return true;

        return false;
    }
}
