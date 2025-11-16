package model;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class DataOrder {
    public static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of("Ольга", "Иванова","ул.Кутузовская,35","Московская","+79837667888",5,"2025-11-20", "Комментарий 1", new String[]{"BLACK"}),
                Arguments.of("Иван","Иванов","ул.Кутузовская","Невский проспект","89837667888",1,"2024-11-20", "Комментарий два",  new String[]{"GREY"}),
                Arguments.of("Юлия","Иванова","ул.Маясковская, д.34","Московская","+79837667883",8,"2025-10-20", "Комментарий три",  new String[]{"BLACK", "GREY"}),
                Arguments.of("Сергей","Иванов","Москва","Зелинаградская","89835667888",3,"2025-09-20", "Комментарий 4",  new String[]{})
        );
    }
}
