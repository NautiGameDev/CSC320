import java.util.ArrayList;

public class Inventory {
        
    ArrayList<Automobile> inventoryData = new ArrayList<Automobile>();

    public void AddVehicle(String[] vehicleInformation)
    {
        //Convert string array to individual strings, then pass to new automobile object constructor.
        try
        {
            String vehicleMake = vehicleInformation[0];
            String vehicleModel = vehicleInformation[1];
            String vehicleColor = vehicleInformation[2];
            int vehicleYear = Integer.parseInt(vehicleInformation[3]);
            int vehicleMileage = Integer.parseInt(vehicleInformation[4]);

            Automobile vehicle = new Automobile(vehicleMake, vehicleModel, vehicleColor, vehicleYear, vehicleMileage);
            this.inventoryData.add(vehicle);

            System.out.println("\nVehicle has been successfully added.");
        }
        catch (NumberFormatException e)
        {
            //Print error message if user enters non integers for year or mileage.
            System.out.println("\nThere was an error adding " + vehicleInformation[3] + " " + vehicleInformation[0] + " " + vehicleInformation[1] + " to the inventory.");
            System.out.println("Invalid number input.");
            System.out.println("You entered " + vehicleInformation[3] + " for the year, and " + vehicleInformation[4] + " for the mileage.");
        }
        catch (Exception e)
        {
            //Covers potential misc. errors.
            System.out.println("\nThere was an error adding " + vehicleInformation[3] + " " + vehicleInformation[0] + " " + vehicleInformation[1] + " to the inventory.");
            System.out.println("Error: " + e);
        }
    }


    public void RemoveVehicle(int vehicleIndex)
    {
        //Removes vehicle from ArrayList at index
        System.out.println("\nRemoving " + inventoryData.get(vehicleIndex).GetVehicleInfo());
        inventoryData.remove(vehicleIndex);        
    }
}
