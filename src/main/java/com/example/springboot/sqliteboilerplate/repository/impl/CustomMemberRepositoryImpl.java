package com.example.springboot.sqliteboilerplate.repository.impl;

import com.example.springboot.sqliteboilerplate.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("customMemberRepository")
public class CustomMemberRepositoryImpl extends BaseRepository implements MemberRepository {

  public CustomMemberRepositoryImpl(final @Qualifier("customJdbcTemplate") JdbcTemplate customJdbcTemplate) {
    super(customJdbcTemplate);
  }

}
