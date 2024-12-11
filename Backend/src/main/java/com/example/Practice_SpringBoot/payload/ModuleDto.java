package com.example.Practice_SpringBoot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto   {
    private Long moduleId;
    private String title;
    private String description;
    private List<ContentDto> contents;

    public void setContents(List<ContentDto> contents) {
        this.contents = new ArrayList<>(contents);
    }

}
