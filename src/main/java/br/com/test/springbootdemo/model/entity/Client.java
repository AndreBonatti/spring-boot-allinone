package br.com.test.springbootdemo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Table(name = "client")
@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    public int getAge(){
        Period period = Period.between(birthday, LocalDate.now());
        return period.getYears();
    }
}