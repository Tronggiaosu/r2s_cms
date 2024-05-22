package com.example.CMSR2S.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.example.CMSR2S.dto.MemberDTO;
import com.example.CMSR2S.dto.RegisterDTO;
import com.example.CMSR2S.entity.Member;

@Mapper(componentModel = "spring")
@Component
public interface MemberMapper {
    Member toEntity(RegisterDTO registerDTO);

    RegisterDTO toDTO(Member member);

    Member toEntity(MemberDTO memberDTO);

    MemberDTO toMemberDTO(Member member);
}
