package com.ctw.workstation.rest.resource;

import com.ctw.workstation.item.Location;
import com.ctw.workstation.rest.dto.TeamDTO;
import com.ctw.workstation.util.Message;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeamResourceTest {
    @Test
    @DisplayName("Get all teams when there's no teams")
    @Order(1)
    void getting_all_teams() {

        String response = given()
                .when()
                .get("/teams")
                .then().extract().response().asPrettyString();
        Log.info(response);


        given()

        .when()
                .get("/teams")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(equalTo("No Teams were found!"));
    }

    @Test
    @DisplayName("Adding a new team")
    @Order(2)
    void adding_a_new_team() {
        given()
                .contentType(ContentType.JSON)
                .body(new TeamDTO("Benfica", "Football", Location.LISBON))
        .when()
                .post("/teams")
        .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }

    @Test
    @DisplayName("Getting all teams when there's one team")
    @Order(3)
    void getting_all_teams_when_there_is_one_team() {
        given()
        .when()
                .get("/teams")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("Benfica"))
                .body("[0].product", equalTo("Football"))
                .body("[0].defaultLocation", equalTo(Location.LISBON.toString()));
    }

    @Test
    @DisplayName("Getting a specific team using its ID")
    @Order(3)
    void getting_a_specific_team_using_its_id() {
        given()
        .when()
                .get("/teams/{id}", 1)
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("name", equalTo("Benfica"))
                .body("product", equalTo("Football"))
                .body("defaultLocation", equalTo(Location.LISBON.toString()));
    }

    @Test
    @DisplayName("Getting a team that doesn't exist")
    @Order(4)
    void getting_a_team_that_doesnt_exist() {
        given()
        .when()
                .get("/teams/{id}", 100)
        .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
        .body(equalTo("Team with id 100 not found."));
    }

    @Test
    @DisplayName("Deleting a team using its ID")
    @Order(5)
    void deleting_a_specific_team_using_its_id() {
        given()
        .when()
                .delete("/teams/{id}", 1)
        .then()
                .statusCode(204);
        given()
        .when()
                .get("/teams")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(equalTo("No Teams were found!"));
    }


}
