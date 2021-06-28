package com.example.springboot.sqliteboilerplate;

import com.example.springboot.sqliteboilerplate.api.MembersController;
import com.example.springboot.sqliteboilerplate.config.DbConfig;
import com.example.springboot.sqliteboilerplate.config.DbMigration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = SqliteBoilerplateApplication.class)
class SqliteBoilerplateApplicationTests {

  @Autowired private MembersController membersController;
  @Autowired private DbConfig dbConfig;
  @Autowired private DbMigration dbMigration;

  @Test
  void contextLoads() {
    assertThat(this.membersController).isNotNull();
    assertThat(this.dbConfig).isNotNull();
    assertThat(this.dbMigration).isNotNull();
  }

}
