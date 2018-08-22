package com.example.postgresdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "member")
public class Member extends AuditModel {
    @Id
    @GeneratedValue(generator = "memberId_generator")
    @SequenceGenerator(
            name = "memberId_generator",
            sequenceName = "memberId_sequence",
            initialValue = 10000
    )
    private Long memberId;

    @NotBlank
    @Size(min = 3, max = 100)
    private String member;

    public Long getmemberId() {
        return memberId;
    }

    public void setmemberId(Long memberId) {
        this.memberId= memberId;
    }

    public String getmember() {
        return member;
    }

    public void setmember(String member) {
        this.member = member;
    }
}
