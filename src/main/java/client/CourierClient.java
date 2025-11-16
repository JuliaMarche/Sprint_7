package client;

import config.BaseUrl;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import static config.ApiEndpoint.*;
import static io.restassured.RestAssured.given;

public class CourierClient extends BaseUrl {

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .post(CREATE_COURIER);
    }

    @Step("Логин курьера")
    public Response loginCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .post(LOGIN_COURIER);
    }

    @Step("Удаление курьера")
    public Response deleteCourier(int id) {
        return given()
                .spec(getBaseSpec())
                .delete(DELETE_COURIER + id);
    }
}
