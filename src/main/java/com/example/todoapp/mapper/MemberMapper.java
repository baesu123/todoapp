package com.example.todoapp.mapper;

import com.example.todoapp.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member findByUsername(String username);

    int countByUsername(String username);

    void insert(Member member);

}
