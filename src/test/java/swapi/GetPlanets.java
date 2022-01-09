package swapi;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class GetPlanets extends BaseTest {
    private static Stream<Arguments> planetList(){
        return Stream.of(
                Arguments.of("1","Tatooine","23","desert"),
                Arguments.of("2","Alderaan","24","grasslands, mountains"),
                Arguments.of("3","Yavin IV","24","jungle, rainforests")
        );
    }

    @DisplayName("Get planet's infos")
    @ParameterizedTest
    @MethodSource("planetList")
    public void getSelectedPlanetInfo(String id, String planetName, String rotationPeriod, String terrain){
        Response response = given()
                .spec(reqspec)
                .pathParam("id",id)
                .when()
                .get(BASE_URL + PLANETS +"/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo(planetName);
        assertThat(json.getString("rotation_period")).isEqualTo(rotationPeriod);
        assertThat(json.getString("terrain")).isEqualTo(terrain);
    }

    @Test
    public void countFilms(){
        Response response = given()
                .spec(reqspec)
                .pathParam("id", 1)
                .when()
                .get(BASE_URL + PLANETS + "/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getList("films")).hasSize(5);

    }
}