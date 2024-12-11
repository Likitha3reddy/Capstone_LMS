package com.example.Practice_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contents")
@Getter
@Setter
public class Content {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long contentId;

        @Enumerated(EnumType.STRING)
        private ContentType contentType;

        @Lob
        @Column(nullable = false)
        private byte[] urlOrPath;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "module_id", nullable = false)
        @JsonBackReference // Prevent infinite recursion
         // Exclude this field from serialization
        private Module module;
}
