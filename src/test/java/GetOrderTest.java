import client.OrderClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isA;


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
                .body("orders", isA(List.class))
                .body("orders.size()", greaterThan(0));
    }
}
