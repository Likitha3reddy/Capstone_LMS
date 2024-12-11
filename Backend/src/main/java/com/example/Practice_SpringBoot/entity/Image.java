package com.example.Practice_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
image table
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Image",schema = "dbo")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Lob
    /*
    to store in byte
     */
    private byte[] image;
}
