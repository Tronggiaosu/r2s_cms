package com.example.CMSR2S.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.CMSR2S.dto.ContentDTO;
import com.example.CMSR2S.dto.PaginationDTO;
import com.example.CMSR2S.entity.Content;
import com.example.CMSR2S.entity.Member;
import com.example.CMSR2S.exception.ContentNotFoundException;
import com.example.CMSR2S.exception.NotFoundException;
import com.example.CMSR2S.mapper.ContentMapper;
import com.example.CMSR2S.mapper.MemberMapper;
import com.example.CMSR2S.repository.ContentRepository;
import com.example.CMSR2S.service.ContentService;
import com.example.CMSR2S.service.MemberService;

@Service
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final ContentMapper contentMapper;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public ContentServiceImpl(ContentRepository contentRepository, ContentMapper contentMapper,
            MemberService memberService, MemberMapper memberMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationDTO findAllContentPaginate(long memberId, int no, int limit, String keyword)
            throws ContentNotFoundException {
        Page<ContentDTO> contentDTOPages;
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, lấy tất cả nội dung
            contentDTOPages = contentRepository.findAll(memberId, PageRequest.of(no, limit))
                    .map(contentMapper::toDTO);
        } else {
            // Nếu có từ khóa, tìm kiếm nội dung theo từ khóa
            contentDTOPages = contentRepository.findContentByKeyword(memberId, keyword, PageRequest.of(no, limit))
                    .map(contentMapper::toDTO);
        }

        if (contentDTOPages.getTotalElements() == 0)
            throw new ContentNotFoundException("Không tìm thấy nội dung nào!");

        PaginationDTO paginationDTO = new PaginationDTO();

        paginationDTO.setContents(contentDTOPages.getContent());
        paginationDTO.setFirst(contentDTOPages.isFirst());
        paginationDTO.setLast(contentDTOPages.isLast());
        paginationDTO.setTotalPages(contentDTOPages.getTotalPages());
        paginationDTO.setTotalItems(contentDTOPages.getTotalElements());
        paginationDTO.setLimit(contentDTOPages.getSize());
        paginationDTO.setNo(contentDTOPages.getNumber());

        return paginationDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public ContentDTO findContentById(long memberId, long id) throws ContentNotFoundException {
        Content content = contentRepository.findContentById(memberId, id);
        if (content == null) {
            throw new ContentNotFoundException("Hiện tại không có nội dung nào!");
        }
        return contentMapper.toDTO(content);
    }

    @Transactional
    @Override
    public ContentDTO create(ContentDTO contentDTO) {
        Member member = memberMapper.toEntity(memberService.getCurrentMember());
        Content content = contentMapper.toEntity(contentDTO);
        content.setMember(member);
        content = contentRepository.save(content);

        return contentMapper.toDTO(content);
    }

    @Transactional
    @Override
    public ContentDTO update(long id, ContentDTO contentDTO) throws ContentNotFoundException {
        Content existedContent = contentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        // cập nhật thông tin của content
        existedContent.setBrief(contentDTO.getBrief());
        existedContent.setFrontpager(contentDTO.getFrontpager());
        existedContent.setContent(contentDTO.getContent());
        existedContent = contentRepository.save(existedContent);

        return contentMapper.toDTO(existedContent);
    }

    @Transactional
    @Override
    public String delete(long id) throws ContentNotFoundException {
        Optional<Content> existedContent = contentRepository.findById(id);
        if (existedContent.isPresent()) {
            contentRepository.deleteById(id);
            return "Xoá nội dung thành công";
        } else {
            throw new ContentNotFoundException("nội dung này không tồn tại!");
        }
    }
}
