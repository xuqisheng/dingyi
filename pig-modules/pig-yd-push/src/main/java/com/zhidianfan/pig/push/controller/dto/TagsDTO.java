package com.zhidianfan.pig.push.controller.dto;

import lombok.Data;

import java.util.Set;



@Data
public class TagsDTO {

    private Set<String> add;

    private Set<String> remove;
}
