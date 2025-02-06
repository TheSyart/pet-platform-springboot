package com.pets.pojo.dto;

import lombok.Data;

@Data
public class PageRequestDto<T> {
    T conditions;
    public Pagination pagination;
    @Data
    public static class Pagination{
        public Integer page;
        public Integer size;
    }
}
