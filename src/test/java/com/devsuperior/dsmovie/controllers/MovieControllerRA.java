package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MovieControllerRA {

	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	private Long existingMoviesId, nonExistingMoviesId;

	private Map<String, Object> postMoviesInstance;


	@BeforeEach
	public void setup() throws JSONException {

		baseURI = "http://localhost:8080";

		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";

		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";

		postMoviesInstance = new HashMap<>();
		postMoviesInstance.put("title", "New Title");
		postMoviesInstance.put("score", 2.0);
		postMoviesInstance.put("count", 0);
		postMoviesInstance.put("image", "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg");
	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {

		given()
					.get("/movies")
				.then()
					.statusCode(200)
					.body("content.id", hasItems(1, 2, 3))
					.body("content.title", hasItems("The Witcher", "Venom: Tempo de Carnificina", "O Espetacular Homem-Aranha 2: A Ameaça de Electro"));
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {

		given()
					.get("/movies?page=0")
				.then()
					.statusCode(200)
					.body("content.id", hasItems(1, 2, 3))
					.body("content.title", hasItems("The Witcher", "Venom: Tempo de Carnificina", "O Espetacular Homem-Aranha 2: A Ameaça de Electro"));
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {

		existingMoviesId = 2L;

		given()
					.get("/movies/{id}", existingMoviesId)
				.then()
					.statusCode(200)
					.body("id", is(2))
					.body("title", equalTo("Venom: Tempo de Carnificina"))
					.body("score", is(3.3F))
					.body("count", is(3))
					.body("image", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/vIgyYkXkg6NC2whRbYjBD7eb3Er.jpg"));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {

		nonExistingMoviesId = 100L;

		given()
					.get("/movies/{id}", nonExistingMoviesId)
				.then()
					.statusCode(404)
					.body("error", equalTo("Recurso não encontrado"))
					.body("status", equalTo(404));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {

		postMoviesInstance.put("title", "ab");

		JSONObject newMovie = new JSONObject(postMoviesInstance);

		given()
					.header("Content-type", "application/json")
					.header("Authorization", "Bearer " + adminToken)
					.body(newMovie)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post("/movies")
				.then()
					.statusCode(422)
					.body("errors.message[0]", equalTo("Tamanho deve ser entre 5 e 80 caracteres"));
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {

		JSONObject newMovie = new JSONObject(postMoviesInstance);

		given()
					.header("Content-type", "application/json")
					.header("Authorization", "Bearer " + clientToken)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(newMovie)
				.when()
					.post("/movies")
				.then()
					.statusCode(403);
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {

		JSONObject newMovie = new JSONObject(postMoviesInstance);

		given()
					.header("Content-type", "application/json")
					.header("Authorization", "Bearer " + invalidToken)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(newMovie)
				.when()
					.post("/movies")
				.then()
					.statusCode(401);
	}
}
