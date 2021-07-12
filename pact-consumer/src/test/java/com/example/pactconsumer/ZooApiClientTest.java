package com.example.pactconsumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ZooApiClientTest {
    private static final String PROVIDER = "zoo";
    private static final String CONSUMER = "zoo_client";

    private ZooApiClient client;

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2(PROVIDER, this);

    @Before
    public void beforeEach() {
        System.setProperty("pact.rootDir", "pacts");
        client = new ZooApiClient(baseUrl(), new RestTemplate());
    }

	@Test
	@PactVerification(fragment = "listAnimals_3animals_fragment")
	public void listAnimals_returnsAnimals() {
		List<Animal> expectedAnimals = Arrays.asList(
				new Animal("cat"),
				new Animal("dolphin"),
				new Animal("sloth")
		);

		assertThat(client.listAnimals()).isEqualTo(expectedAnimals);
	}

    @Pact(consumer=CONSUMER)
    public RequestResponsePact listAnimals_3animals_fragment(PactDslWithProvider builder) {
        return builder
                .given("3 animals")
                .uponReceiving("A request to get all animals")
                .path("/animals")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(animalsJson, MediaType.APPLICATION_JSON_VALUE)
                .toPact();
    }

    private String baseUrl() {
        return String.format("http://localhost:%s", mockProvider.getPort());
    }

    private static String animalsJson = "{\"animals\": [{\"name\": \"cat\" },{\"name\": \"dolphin\"},{\"name\": \"sloth\"}]}";
}
