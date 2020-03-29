package com.jos.cata;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.github.tomakehurst.wiremock.WireMockServer;

public class RestGodClientTests2 {
	WireMockServer wireMockServer;
	
	@BeforeEach
	public void setup () {
		wireMockServer = new WireMockServer(8989);
		wireMockServer.start();
	}

	@AfterEach
	public void teardown () {
		wireMockServer.stop();
	}

	@DisplayName("Should calculate the correct sum")
    @ParameterizedTest(name = "{index} => god={0}, sum={1}")
    @CsvSource({
            "'Nike', 391",
            "'Nemesis', 724",
            "'Njord', 509",
            "'Neptun', 634",
    })
	public void whenCall_and_retrieve_all_API_info(String god, int sum) {
		
		int result = GodClientRestService.sumDecimalName(GodClientRestService.convertNameToDecimal(god));
		assertEquals(sum, result);

	}
}
