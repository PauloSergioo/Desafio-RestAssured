package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.*;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class ScoreControllerRA {

	private String clientUsername, clientPassword;
	private String clientToken;
	private Map<String, Object> putScoresInstance;

	@BeforeEach
	public void setup() throws JSONException {

		baseURI = "http://localhost:8080";

		clientUsername = "alex@gmail.com";
		clientPassword = "123456";

		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);

		putScoresInstance = new HashMap<>();
		putScoresInstance.put("movieId", "1");
		putScoresInstance.put("score", 4);
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {

		putScoresInstance.put("movieId", "100");
		JSONObject newScore = new JSONObject(putScoresInstance);

		given()
					.header("Content-type", "application/json")
					.header("Authorization", "Bearer " + clientToken)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(newScore)
				.when()
					.put("/scores")
				.then()
					.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {

		putScoresInstance.put("movieId", null);
		JSONObject newScore = new JSONObject(putScoresInstance);

		given()
					.header("Content-type", "application/json")
					.header("Authorization", "Bearer " + clientToken)
					.body(newScore)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.log()
					.all()
				.when()
					.put("/scores")
				.then()
					.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {

		putScoresInstance.put("score", -1);
		JSONObject newScore = new JSONObject(putScoresInstance);

		given()
					.header("Content-type", "application/json")
					.header("Authorization", "Bearer " + clientToken)
					.body(newScore)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.log()
					.all()
				.when()
					.put("/scores")
				.then()
					.statusCode(422);
	}
}