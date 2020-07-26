package zust.logistics.entity.pojo;

import lombok.Getter;
import lombok.Setter;
import zust.logistics.entity.RepairState;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Repair_ {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String code;
    @Column(name = "stu_id")
    private String stuID;
    @Column
    private String room;
    @Column
    private String note;
    @Enumerated(EnumType.STRING)
    private RepairState repairState;
}
