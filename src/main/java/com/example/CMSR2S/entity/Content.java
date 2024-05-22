package com.example.CMSR2S.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "frontpager", length = 256)
    private String frontpager;

    @Column(name = "brief", length = 256)
    private String brief;

    @Column(name = "content")
    private String content;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private Member member;
}
