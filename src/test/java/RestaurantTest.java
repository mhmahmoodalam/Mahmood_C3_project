import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RestaurantTest {
  private Restaurant restaurant;

  @BeforeEach
  private void setupRestaurantTestData() {
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    restaurant.addToMenu("Sweet corn soup", 119);
    restaurant.addToMenu("Vegetable lasagne", 269);
  }

  @Test
  public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
    LocalTime timeBetweenOpeningAndClosingTime = LocalTime.parse("11:00:00");
    Restaurant spiedRestaurant = Mockito.spy(restaurant);
    Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(timeBetweenOpeningAndClosingTime);
    assertTrue(spiedRestaurant.isRestaurantOpen());
  }

  @Test
  public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {

    LocalTime timeAfterClosingTime = LocalTime.parse("23:00:00");
    Restaurant spiedRestaurant = Mockito.spy(restaurant);
    Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(timeAfterClosingTime);
    assertFalse(spiedRestaurant.isRestaurantOpen());
  }

  @Test
  public void adding_item_to_menu_should_increase_menu_size_by_1() {
    int initialMenuSize = restaurant.getMenu().size();
    restaurant.addToMenu("Sizzling brownie", 319);
    assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
  }

  @Test
  public void removing_item_from_menu_should_decrease_menu_size_by_1()
      throws itemNotFoundException {
    int initialMenuSize = restaurant.getMenu().size();
    restaurant.removeFromMenu("Vegetable lasagne");
    assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
  }

  @Test
  public void removing_item_that_does_not_exist_should_throw_exception() {
    assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
  }

  @Test
  public void adding_item_to_cart_should_return_the_total_order_value() {
    restaurant.addToMenu("Sizzling brownie", 319);
    List<String> orderItems = new ArrayList<>();
    orderItems.add("Sweet corn soup");
    orderItems.add("Sizzling brownie");
    assertEquals(restaurant.getOrderValue(orderItems), 438);
  }

  @Test
  public void when_no_item_in_cart_should_return_the_total_order_value_0() {
    List<String> orderItems = new ArrayList<>();
    assertEquals(restaurant.getOrderValue(orderItems), 0);
  }
}
