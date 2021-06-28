package com.example.springboot.sqliteboilerplate.api;

import com.example.springboot.sqliteboilerplate.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("ApiTests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("ApiTests::MembersController Tests")
class MembersControllerTest {

  @LocalServerPort private int port;
  @Autowired private TestRestTemplate httpClient;

  private ObjectWriter writer;
  private ObjectReader reader;

  @BeforeEach
  void setUp() {
    final var mapper = new ObjectMapper();
    this.writer = mapper.writerWithDefaultPrettyPrinter();
    this.reader = mapper.reader();
  }

  private String baseUrl() {
    return "http://127.0.0.1:%d/members".formatted(this.port);
  }

  private CreateMemberParam randomMember() {
    final var age = (int) (Math.random() * 90 + 25);
    return new CreateMemberParam(randomString(), randomString(), age);
  }

  private String randomString() {
    final var size = (int) (Math.random() * 30 + 10);
    final var bytes = new byte[size];
    new SecureRandom().nextBytes(bytes);

    return Base64.getEncoder().encodeToString(bytes);
  }

  @Test
  @DisplayName("Should be correct and succeed when using /members/v1/create api")
  void testCreateMemberUsingByDefaultRepository() throws URISyntaxException, IOException {
    final var url = "%s/v1/create".formatted(baseUrl());
    final var member = randomMember();

    final var request = new HttpEntity<>(member);
    final var response = this.httpClient.postForEntity(new URI(url), request, JResponse.class);

    final var body = writer.writeValueAsString(response.getBody().data());
    final var result = reader.readValue(body, Member.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.getAge()).isEqualTo(member.age());
    assertThat(result.getFirstName()).isEqualTo(member.firstName());
    assertThat(result.getLastName()).isEqualTo(member.lastName());
  }

  @Test
  @DisplayName("Should be correct and succeed when using /members/v2/create api")
  void testCreateMemberUsingByCustomRepository() throws URISyntaxException, IOException {
    final var url = "%s/v2/create".formatted(baseUrl());
    final var member = randomMember();

    final var request = new HttpEntity<>(member);
    final var response = this.httpClient.postForEntity(new URI(url), request, JResponse.class);
    final var body = writer.writeValueAsString(response.getBody().data());
    final var result = reader.readValue(body, Member.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.getAge()).isEqualTo(member.age());
    assertThat(result.getFirstName()).isEqualTo(member.firstName());
    assertThat(result.getLastName()).isEqualTo(member.lastName());
  }

  @Test
  @DisplayName("Should be correct when using /members/v1 api")
  void testFindAllUsingByDefaultRepository() throws URISyntaxException, IOException {
    final var url = "%s/v1".formatted(baseUrl());

    this.httpClient.postForEntity(new URI("%s/v1/create".formatted(baseUrl())), new HttpEntity<>(randomMember()), JResponse.class);

    final var response = this.httpClient.getForEntity(new URI(url), JResponse.class);
    final var body = writer.writeValueAsString(response.getBody().data());
    final var result = reader.readValue(body, List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("Should be correct when using /members/v2 api")
  void testFindAllUsingByCustomRepository() throws IOException, URISyntaxException {
    final var url = "%s/v2".formatted(baseUrl());

    this.httpClient.postForEntity(new URI("%s/v2/create".formatted(baseUrl())), new HttpEntity<>(randomMember()), JResponse.class);

    final var response = this.httpClient.getForEntity(new URI(url), JResponse.class);
    final var body = writer.writeValueAsString(response.getBody().data());
    final var result = reader.readValue(body, List.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(result.size()).isGreaterThan(0);
  }
}
