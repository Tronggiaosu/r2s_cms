package com.example.CMSR2S.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.example.CMSR2S.dto.ContentDTO;
import com.example.CMSR2S.entity.Content;

@Mapper(componentModel = "spring", uses = { MemberMapper.class })
@Component
public interface ContentMapper {
    @Mapping(source = "member", target = "member.id")
    Content toEntity(ContentDTO contentDTO);

    @Mapping(source = "member.id", target = "member")
    ContentDTO toDTO(Content content);
}
