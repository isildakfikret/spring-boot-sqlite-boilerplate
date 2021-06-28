package com.example.springboot.sqliteboilerplate.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("ConfigTests")
@SpringBootTest(classes = DbConfig.class)
@DisplayName("ConfigTests::DbConfig Tests")
class DbConfigTest {

  @Autowired private DataSource dataSource;
  @Autowired private DataSource customDataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private JdbcTemplate customJdbcTemplate;

  @Test
  @DisplayName("data sources and jdbc templates should be created when the app started")
  void contextLoads() {
    assertThat(this.dataSource).isNotNull();
    assertThat(this.customDataSource).isNotNull();
    assertThat(this.jdbcTemplate).isNotNull();
    assertThat(this.customJdbcTemplate).isNotNull();
  }

}
