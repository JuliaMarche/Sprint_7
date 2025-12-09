import client.CourierClient;
import io.restassured.response.Response;
import model.Courier;
import model.DataCourier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {

    private CourierClient courierClient;
    private Courier courierData;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courierData = DataCourier.generateDataCourier();
    }

    @AfterEach
    public void tearDown() {
        if (courierData != null) {
            Response loginResponse = courierClient.loginCourier(
                    new Courier(courierData.getLogin(), courierData.getPassword(), null));
            if (loginResponse.statusCode() == 200) {
                int courierId = loginResponse.path("id");
                courierClient.deleteCourier(courierId);
            }
        }
    }

    @Test
    @DisplayName("Создание курьера")
    public void createCourierTest() {
        Response response = courierClient.createCourier(courierData);
                response.then()
                        .statusCode(201)
                        .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание существующего курьера")
    public void createDuplicateCourierTest() {
        courierClient.createCourier(courierData);
        Response responseDuplicate = courierClient.createCourier(courierData);
        responseDuplicate.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля:логин")
    public void createCourierWithoutLogin() {
        Courier courierWithoutLogin = new Courier(null, courierData.getPassword(), courierData.getFirstName());
        Response response = courierClient.createCourier(courierWithoutLogin);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля:пароль")
    public void createCourierWithoutPassword() {
        Courier courierWithoutPassword = new Courier(courierData.getLogin(), null, courierData.getFirstName());
        Response response = courierClient.createCourier(courierWithoutPassword);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля:имя")
    public void createCourierWithoutFirstname() {
        Courier courierWithoutFirstname = new Courier(courierData.getLogin(), courierData.getPassword(), null);
        Response response = courierClient.createCourier(courierWithoutFirstname);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
