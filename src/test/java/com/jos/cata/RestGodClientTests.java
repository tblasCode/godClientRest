package com.jos.cata;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

public class RestGodClientTests {
	WireMockServer wireMockServer;
	
	@BeforeEach
	public void setup () {
		wireMockServer = new WireMockServer(8989);
		wireMockServer.start();
		setupStub();
	}

	@AfterEach
	public void teardown () {
		wireMockServer.stop();
	}

	public void setupStub() {
		wireMockServer.stubFor(get(urlEqualTo("/gods/?nationality=greek"))
				.willReturn(aResponse().withHeader("Content-Type", "text/plain")
						.withStatus(200)
						.withBodyFile("json/godsGreek.json")));

		wireMockServer.stubFor(get(urlEqualTo("/gods/?nationality=roman"))
				.willReturn(aResponse().withHeader("Content-Type", "text/plain")
						.withStatus(200)
						.withBodyFile("json/godsRoman.json")));
		wireMockServer.stubFor(get(urlEqualTo("/gods/?nationality=nordic"))
				.willReturn(aResponse().withHeader("Content-Type", "text/plain")
						.withStatus(200)
						.withBodyFile("json/godsNordic.json")));


	}


	@Test
	public void whenCall_and_retrieve_all_API_info() {
		List<String> greekGods = given()
            .baseUri("http://localhost:8989/gods")
            .and()
            .queryParam("nationality", "greek")
        .when()
            .get("/")
        .then()
            .log().all()
            .and().assertThat().statusCode(is(equalTo(200)))
            .and().extract().body().jsonPath().getList(".", String.class);
		
		List<String> romanGods = given()
	            .baseUri("http://localhost:8989/gods")
	            .and()
	            .queryParam("nationality", "roman")
	        .when()
	            .get("/")
	        .then()
	            .log().all()
	            .and().assertThat().statusCode(is(equalTo(200)))
	            .and().extract().body().jsonPath().getList(".", String.class);
		
		List<String> nordicGods = given()
	            .baseUri("http://localhost:8989/gods")
	            .and()
	            .queryParam("nationality", "nordic")
	        .when()
	            .get("/")
	        .then()
	            .log().all()
	            .and().assertThat().statusCode(is(equalTo(200)))
	            .and().extract().body().jsonPath().getList(".", String.class);
		
		
		List<String> godsGreekFilterFirstLetterN = GodClientRestService.filterGods(greekGods);
		assertEquals(2,godsGreekFilterFirstLetterN.size());
		
		List<String> godsRomanFilterFirstLetterN = GodClientRestService.filterGods(romanGods);
		assertEquals(1,godsRomanFilterFirstLetterN.size());
		
		List<String> godsNordicFilterFirstLetterN = GodClientRestService.filterGods(nordicGods);
		assertEquals(1,godsNordicFilterFirstLetterN.size());

		
		int resultGreek = godsGreekFilterFirstLetterN.stream().flatMapToInt(data -> IntStream.of(GodClientRestService.sumDecimalName(GodClientRestService.convertNameToDecimal(data)))).sum();
		assertEquals(1115, resultGreek);
		int resultRoman= godsRomanFilterFirstLetterN.stream().flatMapToInt(data -> IntStream.of(GodClientRestService.sumDecimalName(GodClientRestService.convertNameToDecimal(data)))).sum();
		assertEquals(634, resultRoman);
		int resultNordic = godsNordicFilterFirstLetterN.stream().flatMapToInt(data -> IntStream.of(GodClientRestService.sumDecimalName(GodClientRestService.convertNameToDecimal(data)))).sum();
		assertEquals(509, resultNordic);

	}
}
