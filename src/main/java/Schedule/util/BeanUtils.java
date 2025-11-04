package Schedule.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class BeanUtils {

    @SneakyThrows
    public void copyFields(Object sours, Object destination) {
        Class<?> clazz = sours.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            Object value = field.get(sours);

            if (value != null) field.set(destination, value);
        }
    }
}
