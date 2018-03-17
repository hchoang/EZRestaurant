package restaurant.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import restaurant.model.facade.RestaurantEngineTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdminTest.class,
    CashierTest.class,
//    CategoryTest.class,
    ItemTest.class,
    LocationTest.class,
    ManagerTest.class,
    MenuTest.class,
//    OrderTest.class,
    OrderItemTest.class,
//    TableTest.class,
    UserTest.class,
    RestaurantEngineTest.class
})
public class TestAll {
}
