import java.util.ArrayList;

public class Automobile {
    String make;
    String model;
    String color;
    int year;
    int mileage;

    ArrayList<String> vehicleData = new ArrayList<String>();

    public Automobile(String vehicleMake, String vehicleModel, String vehicleColor, int vehicleYear, int vehicleMileage)
    {
        this.make = vehicleMake;
        this.model = vehicleModel;
        this.color = vehicleColor;
        this.year = vehicleYear;
        this.mileage = vehicleMileage;

        //Add data to array for printing
        this.vehicleData.add(this.make);
        this.vehicleData.add(this.model);
        this.vehicleData.add(this.color);
        this.vehicleData.add(Integer.toString(this.year));
        this.vehicleData.add(Integer.toString(this.mileage));

        PrintVehicleData();
    }

    public void PrintVehicleData()
    {
        for (int i=0; i<vehicleData.size(); i++)
        {
            System.out.println(vehicleData.get(i));
        }
    }

    public String GetVehicleInfo()
    {
        //All-purpose method to print vehicle info to console. Returns string to main program class for printing.
        String info = this.year + " " + this.make + " " + this.model + " :: Color: " + this.color + " :: Mileage: " + this.mileage;
        return info;
    }

    public String GetVehicleInfo(String infoType)
    {
        //Returns string containing data of user-specified data type.
        if (infoType.equalsIgnoreCase("make"))
        {
            return this.make;
        }
        else if (infoType.equalsIgnoreCase("model"))
        {
            return this.model;
        }
        else if (infoType.equalsIgnoreCase("color"))
        {
            return this.color;
        }
        else if (infoType.equalsIgnoreCase("year"))
        {
            String convertedYear = Integer.toString(this.year);
            return convertedYear;
        }
        else if (infoType.equalsIgnoreCase("mileage"))
        {
            String convertedMileage = Integer.toString(this.mileage);
            return convertedMileage;
        }

        String notAvailable = "n/a";
        return notAvailable;
    }

    public void UpdateVehicleData(String dataType, String newData)
    {
        //update automobile data based on user-specified data type.
        try
        {      
            if (dataType.equalsIgnoreCase("make"))
            {
                this.make = newData;
                vehicleData.set(0, newData);
            }
            else if (dataType.equalsIgnoreCase("model"))
            {
                this.model = newData;
                vehicleData.set(1, newData);
            }
            else if (dataType.equalsIgnoreCase("color"))
            {
                this.color = newData;
                vehicleData.set(2, newData);
            }
            else if (dataType.equalsIgnoreCase("year"))
            {
                this.year = Integer.parseInt(newData);
                vehicleData.set(3, newData);
            }
            else if (dataType.equalsIgnoreCase("mileage"))
            {
                this.mileage = Integer.parseInt(newData);
                vehicleData.set(4, newData);
            }

            System.out.println("\n" + this.year + " " + this.make + " " + this.model + " has been successfully updated.");
            PrintVehicleData();
        }
        catch (NumberFormatException e)
        {
            System.out.println("\nThere was an error updating " + this.year + " " + this.make + " " + this.model);
            System.out.println("Invalid number for data type: " + dataType + ". Your input: " + newData);
        }
    }
}
