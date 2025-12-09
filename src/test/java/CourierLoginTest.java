import client.CourierClient;
import io.restassured.response.Response;
import model.Courier;
import model.DataCourier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

private CourierClient courierClient;
private Courier courierData;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courierData = DataCourier.generateDataCourier();
        courierClient.createCourier(courierData);
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
    @DisplayName("Логин курьера")
    public void courierLoginTest() {
        Courier correctCourierLoginTest = new Courier(courierData.getLogin(), courierData.getPassword(), null);
        Response response = courierClient.loginCourier(correctCourierLoginTest);
        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера без атрибута: логин")
    public void courierLoginWithoutLoginTest() {
        Courier courierWithoutLogin = new Courier(null, courierData.getPassword(), null);
        Response response = courierClient.loginCourier(courierWithoutLogin);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера без атрибута: пароль")
    public void courierLoginWithoutPasswordTest() {
        Courier courierWithoutPassword = new Courier(courierData.getLogin(), null, null);
        Response response = courierClient.loginCourier(courierWithoutPassword);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин c некорректным логином")
    public void courierWrongLoginTest() {
        Courier courierWrongLogin =  new Courier("wrongloginjulia", courierData.getPassword(), null);
        Response response = courierClient.loginCourier(courierWrongLogin);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин c некорректным паролем")
    public void courierWrongPasswordTest() {
        Courier courierWrongPassword = new Courier(courierData.getLogin(), "wrongpass123julia", null);
        Response response = courierClient.loginCourier(courierWrongPassword);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин под несуществующим пользователем")
    public void loginNonExistingCourierTest() {
        Courier loginNonExistingCourier = new Courier("wrongloginjulia", "wrongpass123julia", null);
        Response response = courierClient.loginCourier(loginNonExistingCourier);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
