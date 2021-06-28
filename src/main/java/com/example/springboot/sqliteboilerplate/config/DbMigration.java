package com.example.springboot.sqliteboilerplate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Slf4j
@Component
public class DbMigration {
  private final DataSource dataSource;
  private final DataSource customDataSource;

  public DbMigration(final DataSource dataSource, final DataSource customDataSource) {
    this.dataSource = dataSource;
    this.customDataSource = customDataSource;
  }

  @PostConstruct
  public void start() {
    final var start = System.currentTimeMillis();
    log.info("Migration Started:");

    final var schemaPopulator = new ResourceDatabasePopulator();
    schemaPopulator.addScript(new ClassPathResource("schema.sql"));

    schemaPopulator.execute(this.dataSource);
    schemaPopulator.execute(this.customDataSource);
    log.info("Migration completed: {} ms", System.currentTimeMillis() - start);
  }
}
