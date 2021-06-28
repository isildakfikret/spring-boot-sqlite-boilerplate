package com.example.springboot.sqliteboilerplate.api;

import com.example.springboot.sqliteboilerplate.domain.Member;
import com.example.springboot.sqliteboilerplate.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MembersController {
  private final MemberRepository memberRepository;
  private final MemberRepository customMemberRepository;

  public MembersController(
      final MemberRepository memberRepository,
      @Qualifier("customMemberRepository") final MemberRepository customMemberRepository
  ) {
    this.memberRepository = memberRepository;
    this.customMemberRepository = customMemberRepository;
  }

  @PostMapping("/v1/create")
  public ResponseEntity<JResponse> createByDefault(final @RequestBody CreateMemberParam param) {
    final var result = this.memberRepository.save(param.transform());
    final var responsePayload = JResponse.succeed(result);

    return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
  }

  @PostMapping("/v2/create")
  public ResponseEntity<JResponse> createByCustomRepository(final @RequestBody CreateMemberParam param) {
    final var result = this.customMemberRepository.save(param.transform());
    final var responsePayload = JResponse.succeed(result);

    return new ResponseEntity<>(responsePayload, HttpStatus.CREATED);
  }

  @GetMapping("/v1")
  public ResponseEntity<JResponse> findAllByDefault() {
    final var result = this.memberRepository.findAll();
    final var responsePayload = JResponse.succeed(result);

    return ResponseEntity.ok(responsePayload);
  }

  @GetMapping("/v2")
  public ResponseEntity<JResponse> findAllByCustom() {
    final var result = this.customMemberRepository.findAll();
    final var responsePayload = JResponse.succeed(result);

    return ResponseEntity.ok(responsePayload);
  }
}

record JResponse(String status, LocalDateTime time, Object data) {
  public static JResponse succeed(final Object data) {
    return new JResponse("succeed", LocalDateTime.now(ZoneOffset.UTC), data);
  }
}

record CreateMemberParam (String firstName, String lastName, Integer age){
  Member transform(){
    return new Member(firstName, lastName, age);
  }
}
