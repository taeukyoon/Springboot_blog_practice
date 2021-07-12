package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
@Entity
//@DynamicInsert //Inset시에 null인 필드를 제외시킨다.
public class User {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //auto_increment

    @Column(nullable = false, length = 30)
    private String username; //아이디

    @Column(nullable = false, length = 100)
    private String password; // 12345 => 해쉬(암호화)

    @Column(nullable = false, length = 50)
    private String email;

    // DB에는 RoleType이 없다.
    // @ColumnDefault("user")
    @Enumerated(EnumType.STRING)
    private RoleType role; //ADMIN, USER, MANAGER

    @CreationTimestamp //시간이 자동으로 입력된다.
    private Timestamp createDate;
}
