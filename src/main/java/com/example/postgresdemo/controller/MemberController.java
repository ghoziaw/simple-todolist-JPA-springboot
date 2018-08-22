package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Member;
import com.example.postgresdemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member")
    public Page<Member> getmember(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @PostMapping("/member")
    public Member createmember(@Valid @RequestBody Member member) {
        return memberRepository.save(member);
    }

    @PutMapping("/members/{id}")
    public Member updatemember(@PathVariable Long id,
                             @Valid @RequestBody Member memberRequest) {
        return memberRepository.findById(id)
                .map(member -> {
                    member.setmember(memberRequest.getmember());
                    return memberRepository.save(member);
                }).orElseThrow(() -> new ResourceNotFoundException("member not found with id " + id));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> deletemember(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(members -> {
                    memberRepository.delete(members);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("member not found with id " + id));
    }
}
