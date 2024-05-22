package com.example.CMSR2S.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<?> contents;
    private boolean isFirst;
    private boolean isLast;
    private int totalPages;
    private long totalItems;
    private int limit;
    private int no;
}
