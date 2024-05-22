package com.example.CMSR2S.service;

import com.example.CMSR2S.dto.MemberDTO;
import com.example.CMSR2S.dto.RegisterDTO;
import com.example.CMSR2S.exception.MemberNotFoundException;

public interface MemberService {
    public MemberDTO create(RegisterDTO registerDTO) throws Exception;

    MemberDTO getCurrentMember() throws MemberNotFoundException;

    MemberDTO findById(long id) throws MemberNotFoundException;

    String getUsernameOfCurrentLoginMember() throws MemberNotFoundException;
}
