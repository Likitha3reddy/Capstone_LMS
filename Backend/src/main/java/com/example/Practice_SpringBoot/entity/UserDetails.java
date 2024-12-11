package com.example.Practice_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Setter
@Getter
@Table(name="UserDetails",schema = "dbo")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false,unique = true)
    private String userName;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private boolean isActive;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Enrollment> enrollments;

    public boolean getActive() {
        return isActive;
    }
    public UserDetails(String userName, String passwordHash, String role, String email, String phoneNumber, boolean isActive) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }
}
