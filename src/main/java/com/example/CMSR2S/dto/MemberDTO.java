package com.example.CMSR2S.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MemberDTO extends RegisterDTO {
    private long id;
}
