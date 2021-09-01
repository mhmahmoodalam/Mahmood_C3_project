import java.time.LocalTime;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    // trying to create a console application for testing purpose
    RestaurantService restaurantService = new RestaurantService();

    // restaurant 1
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant =
        restaurantService.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    restaurant.addToMenu("Sweet corn soup", 119);
    restaurant.addToMenu("Vegetable lasagne", 269);

    // restaurant 2

    Restaurant restaurant2 =
        restaurantService.addRestaurant(
            "Glen's Bakehouse",
            "Bengaluru",
            LocalTime.parse("11:00:00"),
            LocalTime.parse("23:00:00"));
    restaurant2.addToMenu("Red Velvet Cupcake(M)", 65);
    restaurant2.addToMenu("Red Velvet Cupcake(L)", 90);
    restaurant2.addToMenu("Cheese cake", 250);

    startApplication(restaurantService);
  }

  public static void startApplication(final RestaurantService restaurantService) {
    printWelcome(restaurantService);
    String restaurantName = inputRestaurantToSearch(restaurantService);
    searchRestaurantAndDisplayDetails(restaurantService, restaurantName);
  }

  private static void searchRestaurantAndDisplayDetails(
      final RestaurantService restaurantService, final String restaurantName) {
    try {
      Restaurant restaurant = restaurantService.findRestaurantByName(restaurantName);
      restaurant.displayDetails();
    } catch (restaurantNotFoundException e) {
      System.out.println("Error: Restaurant could not be found");
      startApplication(restaurantService);
    }
  }

  private static String inputRestaurantToSearch(final RestaurantService restaurantService) {
    System.out.println("Enter Name to search:");
    Scanner scanner = new Scanner(System.in);
    String restaurant = scanner.nextLine();
    scanner.close();
    return restaurant;
  }

  public static void printWelcome(final RestaurantService restaurantService) {
    System.out.println("______________________________________________");
    System.out.println("Search for a restaurant:");
    restaurantService.getRestaurants().stream().forEach(x -> System.out.println(x.getName()));
    System.out.println("______________________________________________");
  }
}
