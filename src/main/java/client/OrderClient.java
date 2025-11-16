package client;

import config.BaseUrl;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;
import static config.ApiEndpoint.CANCEL_ORDER;
import static config.ApiEndpoint.CREATE_ORDERS;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseUrl {
    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .post(CREATE_ORDERS);
    }

    @Step("Проверка списка заказов")
    public Response getOrder() {
        return given()
                .spec(getBaseSpec())
                .get(CREATE_ORDERS);
    }

    @Step("Отмена заказа")
    public Response cancelOrder(int track) {
        return given()
                .spec(getBaseSpec())
                .body("{\"track\":" + track + "}")
                .put(CANCEL_ORDER);
    }
}
