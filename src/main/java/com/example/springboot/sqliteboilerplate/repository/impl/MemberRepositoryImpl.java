package com.example.springboot.sqliteboilerplate.repository.impl;

import com.example.springboot.sqliteboilerplate.repository.MemberRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class MemberRepositoryImpl extends BaseRepository implements MemberRepository {

  public MemberRepositoryImpl(final JdbcTemplate jdbcTemplate) {
    super(jdbcTemplate);
  }

}
