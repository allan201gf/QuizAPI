package br.com.allangf.quizapi.damain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String statement;

    @Column
    private String answer1;

    @Column
    private String answer2;

    @Column
    private String answer3;

    @Column
    private String answer4;

    @Column
    private String answerCorrect;

    @Column
    private String category;

    @Column
    private LocalDateTime createdAt;

    @JoinColumn(name = "system_user")
    @OneToOne
    private SystemUser createdBy;

}
