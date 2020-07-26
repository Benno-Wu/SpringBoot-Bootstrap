package zust.logistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zust.logistics.entity.pojo.Student_;

public interface StudentDAO extends JpaRepository<Student_, Integer> {
    Student_ getByStuID(String stuID);
}
