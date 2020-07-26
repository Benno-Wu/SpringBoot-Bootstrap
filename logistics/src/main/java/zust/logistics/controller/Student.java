package zust.logistics.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zust.logistics.entity.pojo.Student_;
import zust.logistics.service.StudentService;
import zust.logistics.utils.BeanUtil;
import zust.logistics.utils.FileStorage;
import zust.logistics.utils.ResultUtil;

//@CrossOrigin(origins = {"http://127.0.0.1:5500", "null"})
@Api(tags = "学生")
@RestController
@RequestMapping(value = "student")
public class Student {
    @Autowired
    StudentService studentService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ApiOperation("uploadAvatar")
    @PostMapping("/uploadAvatar")
    public ResultUtil uploadAvatar(@ApiParam("pic") @RequestParam MultipartFile file,
                                   @ApiParam("stuID") @RequestParam String stuID) {
        String result = FileStorage.save(file, "stu_" + stuID);
        Student_ student = studentService.get_ByStuID(stuID);
        if (!result.equals("error")) {
            student.setPhoto(result);
            studentService.update(student);
            return ResultUtil.success(result, null);
        } else return ResultUtil.SFailure("FileStorage error", null);
    }

    @ApiOperation("fullInfo")
    @PostMapping("/fullInfo")
    public ResultUtil fullInfo(@ApiParam("studentID") @RequestParam String studentID,
                               @ApiParam("password") @RequestParam String password) {
        if (login(studentID, password).getCode() == 200) {
            Student_ student = studentService.get_ByStuID(studentID);
            return ResultUtil.success("success", student);
        } else return ResultUtil.BFailure("Forbidden", null);
    }

    @ApiOperation("update")
    @PostMapping("/update")
    public ResultUtil update(@ApiParam("student") @RequestBody Student_ student,
                             @ApiParam("studentID") @RequestParam String studentID,
                             @ApiParam("password") @RequestParam String password) {
        if (login(studentID, password).getCode() == 200)
            if (student.getStuID().length() > 5 && student.getName().length() > 1
                    && !student.getTel().isEmpty() && !student.getRoom().isEmpty()) {
                student.setPassword(passwordEncoder.encode(student.getPassword()));
                student = studentService.update(student,studentID);
                return ResultUtil.success("success", BeanUtil.e2d(student));
            } else return ResultUtil.BFailure("form error", null);
        else return ResultUtil.BFailure("Forbidden", null);
    }

    @ApiOperation("login")
    @PostMapping("/login")
    public ResultUtil login(@ApiParam("studentID") @RequestParam String studentID,
                            @ApiParam("password") @RequestParam String password) {
        Student_ student = studentService.get_ByStuID(studentID);
        if (student != null)
            if (passwordEncoder.matches(password, student.getPassword())) {
                return ResultUtil.success("success", BeanUtil.e2d(student));
            } else return ResultUtil.BFailure("password error", null);
        else return ResultUtil.BFailure("student error", null);
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultUtil register(@ApiParam("student") @RequestBody Student_ student,
                               @ApiParam("password") @RequestParam String password) {
        if (student.getPassword().equals(password))
            if (student.getStuID().length() > 5 && student.getName().length() > 1
                    && !student.getTel().isEmpty() && !student.getRoom().isEmpty()) {
                student.setPassword(passwordEncoder.encode(student.getPassword()));
                student.setPhoto("/pic/sun.png");
                student = studentService.save(student);
                return ResultUtil.success("success", BeanUtil.e2d(student));
            } else return ResultUtil.BFailure("form error", null);
        else return ResultUtil.BFailure("password error", null);
    }
}
