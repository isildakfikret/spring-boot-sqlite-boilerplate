package com.example.springboot.sqliteboilerplate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;

@Slf4j
@Configuration
public class DbConfig {

  // 1st Approach (Default Config)
  @Bean
  @Primary
  public DataSource dataSource(
      @Value("${spring.datasource.url}") final String url,
      @Value("${spring.datasource.driver-class-name}") final String driverClassName
  ) {
    return DataSourceBuilder.create()
                            .url(url)
                            .driverClassName(driverClassName)
                            .build();
  }

  // 2nd Approach (Custom Config)
  @Bean
  public DataSource customDataSource(@Value("${spring.datasource.v2.url}") final String url) throws SQLException {
    final var dbConfig = new SQLiteConfig();

    dbConfig.setCacheSize(10000);
    dbConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);
    dbConfig.setLockingMode(SQLiteConfig.LockingMode.EXCLUSIVE);
    dbConfig.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
    dbConfig.enforceForeignKeys(true);
    dbConfig.setSharedCache(true);

    final var dataSource = new SQLiteDataSource(dbConfig);
    dataSource.setUrl(url);
    dataSource.setLogWriter(new PrintWriter(System.out));

    return dataSource;
  }
}
