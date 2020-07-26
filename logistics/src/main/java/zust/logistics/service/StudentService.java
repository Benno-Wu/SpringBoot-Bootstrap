package zust.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zust.logistics.dao.StudentDAO;
import zust.logistics.entity.pojo.Student_;

@Service
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    public Student_ get_ByStuID(String stuID) {
        return studentDAO.getByStuID(stuID);
    }

    @Transactional
    public Student_ save(Student_ student) {
        return studentDAO.saveAndFlush(student);
    }

    @Transactional
    public Student_ update(Student_ student, String studentID) {
        student.setId(studentDAO.getByStuID(studentID).getId());
        return studentDAO.saveAndFlush(student);
    }

    @Transactional
    public Student_ update(Student_ student) {
        student.setId(studentDAO.getByStuID(student.getStuID()).getId());
        return studentDAO.saveAndFlush(student);
    }

    public boolean has(String stuID) {
        return studentDAO.getByStuID(stuID) != null;
    }
}
