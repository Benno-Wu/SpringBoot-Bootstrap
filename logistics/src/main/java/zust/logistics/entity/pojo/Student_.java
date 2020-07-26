package zust.logistics.entity.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Student_ {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stu_id")
    private String stuID;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String tel;
    @Column
    private String email;
    @Column
    private String room;
    @Column
    private String photo;
}
