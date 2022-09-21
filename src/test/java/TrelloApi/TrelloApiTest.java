package com.Beymen.Tests;

import com.Beymen.Utilities.ConfigurationReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class TrelloApiTest {

    static Properties properties;

    @BeforeAll
    public static void setup() {
        properties = ConfigurationReader.initializeProperties();

        RestAssured.baseURI = properties.getProperty("baseURI");
        RestAssured.basePath = properties.getProperty("basePath");
    }

    @Test
    public void apiTest() {

        //Trello üzerinde bir board oluşturunuz.
        String boardId = given()
                .contentType("application/json")
                .when()
                .queryParam("key", properties.getProperty("key"))
                .queryParam("token", properties.getProperty("token"))
                .queryParam("name", "Trello My Board")
                .post("/boards")
                .then()
                .statusCode(200)
                .extract().path("id");

        String listId = given()
                .contentType("application/json")
                .when()
                .queryParam("key", properties.getProperty("key"))
                .queryParam("token", properties.getProperty("token"))
                .queryParam("name", "New List")
                .post("/boards/" + boardId + "/lists")
                .then()
                .statusCode(200)
                .extract().path("id");

        //Oluşturduğunuz board’a iki tane kart oluşturunuz.
        String[] cardsArr = new String[2];
        for (int i = 0; i < 2; i++) {
            cardsArr[i] = given()
                    .contentType("application/json")
                    .when()
                    .queryParam("key", properties.getProperty("key"))
                    .queryParam("token", properties.getProperty("token"))
                    .queryParam("name", "Card Title" + i)
                    .queryParam("idList", listId)
                    .post("/cards")
                    .then()
                    .statusCode(200)
                    .extract().path("id");
        }

        //Oluşturduğunuz bu iki karttan random olacak şekilde bir tanesini güncelleyiniz.
        Random random = new Random();
        int temp = random.nextInt(2);
        given()
                .contentType("application/json")
                .when()
                .queryParam("key", properties.getProperty("key"))
                .queryParam("token", properties.getProperty("token"))
                .queryParam("name", "New Card Title")
                .queryParam("desc", "Card description edited.")
                .put("/cards/" + cardsArr[temp])
                .then()
                .statusCode(200)
                .extract().path("id");

        //Oluşturduğunuz kartları siliniz.
        for (int i = 0; i < 2; i++) {
            given()
                    .contentType("application/json")
                    .when()
                    .queryParam("key", properties.getProperty("key"))
                    .queryParam("token", properties.getProperty("token"))
                    .delete("/cards/" + cardsArr[i])
                    .then()
                    .statusCode(200);
        }

        //Oluşturduğunuz board’u siliniz.
        given()
                .contentType("application/json")
                .when()
                .queryParam("key", properties.getProperty("key"))
                .queryParam("token", properties.getProperty("token"))
                .pathParam("id", boardId)
                .delete("/boards/{id}")
                .then()
                .statusCode(200);

    }

}