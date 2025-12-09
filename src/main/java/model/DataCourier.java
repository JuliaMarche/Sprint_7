package model;
import net.datafaker.Faker;

public class DataCourier {
    private static final Faker dataCourier = new Faker();

    public static Courier generateDataCourier() {
        String login = dataCourier.name().username();
        String password = dataCourier.internet().password();
        String firstName = dataCourier.name().firstName();

        return new Courier(login, password, firstName);
    }
}
