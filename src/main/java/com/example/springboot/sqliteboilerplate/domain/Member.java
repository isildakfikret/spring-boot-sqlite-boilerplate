package com.example.springboot.sqliteboilerplate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
  private String id = UUID.randomUUID().toString();
  private String firstName;
  private String lastName;
  private int age;
}
