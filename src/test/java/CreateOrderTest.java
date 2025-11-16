import client.OrderClient;
import io.restassured.response.Response;
import model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {

    private final OrderClient orderClient = new OrderClient();
    private Integer track;

    @AfterEach
    public void tearDown(){
        if(track != null) {
            orderClient.cancelOrder(track);
        }
    }

    @ParameterizedTest
    @MethodSource("model.DataOrder#orderData")
    @DisplayName("Создание заказа")
    void createOrderTest(String firstName,
                         String lastName,
                         String address,
                         String metroStation,
                         String phone,
                         int rentTime,
                         String deliveryDate,
                         String comment,
                         String[] colors) {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime,deliveryDate, comment, colors);
        Response response = orderClient.createOrder(order);
        track = response.path("track");
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
