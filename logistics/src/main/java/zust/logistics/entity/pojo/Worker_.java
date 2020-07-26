package zust.logistics.entity.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Worker_ {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String tel;
    @Column
    private String photo;
}
