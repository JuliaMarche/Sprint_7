import client.OrderClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest {
    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void getOrderTest() {
        Response response = orderClient.getOrder();
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
