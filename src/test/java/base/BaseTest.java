package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected final String BASE_URL = "https://swapi.dev/api/";
    protected final String PEOPLE = "people";
    protected final String FILMS = "films";
    protected final String PLANETS = "planets";

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqspec;

    @BeforeAll
    public static void beforeAll() {
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);

        reqspec = reqBuilder.build();

    }
}
