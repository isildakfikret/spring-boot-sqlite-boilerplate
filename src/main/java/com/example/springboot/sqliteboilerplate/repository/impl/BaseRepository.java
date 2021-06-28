package com.example.springboot.sqliteboilerplate.repository.impl;

import com.example.springboot.sqliteboilerplate.domain.Member;
import com.example.springboot.sqliteboilerplate.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseRepository  implements MemberRepository {
  protected final JdbcTemplate jdbcTemplate;

  @Override
  public Member mapTo(final ResultSet rs, final int rowNum) throws SQLException {
    return new Member(
        rs.getString("id"),
        rs.getString("firstName"),
        rs.getString("lastName"),
        rs.getInt("age")
    );
  }

  @Override
  public Member save(final Member member) {
    final var sql = "INSERT INTO Members (id, firstName, lastName, age) VALUES (?, ?, ?, ?)";
    this.jdbcTemplate.update(sql, pss -> {
      pss.setString(1, member.getId());
      pss.setString(2, member.getFirstName());
      pss.setString(3, member.getLastName());
      pss.setInt(4, member.getAge());
    });
    return member;
  }

  @Override
  public Collection<Member> findAll() {
    final var sql = "SELECT * FROM Members";
    return this.jdbcTemplate.query(sql, this::mapTo);
  }
}
