package spartan_util;

import com.github.javafaker.Faker;
import pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    /**
     * Used to get valid Map object to represent post body for POST / spartans request
     *
     * @return Map object with Random name, gender , phone
     */
    private static Faker faker = new Faker();

    public static Map<String, Object> getRandomSpartanMap() {


        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", faker.name().firstName());
        bodyMap.put("gender", faker.demographic().sex());
        bodyMap.put("phone", faker.number().numberBetween(5_000_000_000L, 10_000_000_000L));

        return bodyMap;

    }

    public static Spartan getRandomSpartanPOJO() {

        Spartan sp = new Spartan();
        sp.setName(faker.name().firstName());
        sp.setGender(faker.demographic().sex());
        sp.setPhone(faker.number().numberBetween(5_000_000_000L, 10_000_000_000L));

        return sp;

    }


    public static void main(String[] args) {

        System.out.println(getRandomSpartanMap());
        System.out.println(getRandomSpartanPOJO());

    }
}
