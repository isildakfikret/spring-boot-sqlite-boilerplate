package com.example.springboot.sqliteboilerplate.repository;

import com.example.springboot.sqliteboilerplate.domain.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface MemberRepository {
  Member save(final Member member);
  Collection<Member> findAll();
  Member mapTo(final ResultSet rs, final int rowNum) throws SQLException;
}
