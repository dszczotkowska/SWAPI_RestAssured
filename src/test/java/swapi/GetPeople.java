package swapi;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;


public class GetPeople extends BaseTest {

    @Test
    public void countPeopleOnFirstPage() {

        Response response = given()
                .spec(reqspec)
                .when()
                .get(BASE_URL + PEOPLE)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> resultsName = json.getList("results.name");

        assertThat(resultsName).hasSize(10);
        assertThat(resultsName.get(0)).isEqualTo("Luke Skywalker");
    }

    @Test
    public void checksNumberOfMen() {
        Response response = given()
                .spec(reqspec)
                .when()
                .get(BASE_URL + PEOPLE)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> gender = json.getList("results.gender");

        long numberOfMales = gender.stream()
                .filter(x -> x.equals("male"))
                .count();

        assertThat(numberOfMales).isLessThanOrEqualTo(5);
    }

    @Test
    public void serachForDarthCharacters() {
        Response response = given()
                .spec(reqspec)
                .queryParam("search", "Darth")
                .when()
                .get(BASE_URL + PEOPLE)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getList("results.name")).contains("Darth Maul", "Darth Vader");
    }

    @Test
    public void updateInfoAboutDarthVader() {
        JSONObject darthVader = new JSONObject();
        darthVader.put("name", "Anakin Skywalker");

        given()
                .spec(reqspec)
                .queryParam("search", "Darth")
                .body(darthVader.toString())
                .when()
                .patch(BASE_URL + PEOPLE)
                .then()
                .statusCode(405);
    }
}
