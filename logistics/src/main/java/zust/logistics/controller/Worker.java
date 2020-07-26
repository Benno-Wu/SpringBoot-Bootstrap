package zust.logistics.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zust.logistics.entity.dto.WorkerDTO;
import zust.logistics.entity.pojo.Worker_;
import zust.logistics.service.WorkerService;
import zust.logistics.utils.BeanUtil;
import zust.logistics.utils.FileStorage;
import zust.logistics.utils.ResultUtil;

//@CrossOrigin(origins = {"http://127.0.0.1:5500", "null"})
@Api(tags = "维修工")
@RestController
@RequestMapping(value = "worker")
public class Worker {
    @Autowired
    WorkerService workerService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ApiOperation("uploadAvatar")
    @PostMapping("/uploadAvatar")
    public ResultUtil uploadAvatar(@ApiParam("pic") @RequestParam MultipartFile file,
                                   @ApiParam("workerCode") @RequestParam String code) {
        String result = FileStorage.save(file, "worker_" + code);
        Worker_ worker = workerService.get_ByCode(code);
        if (!result.equals("error")) {
            worker.setPhoto(result);
            workerService.update(worker);
            return ResultUtil.success(result, null);
        } else return ResultUtil.SFailure("FileStorage error", null);
    }

    @ApiOperation("fullInfo")
    @PostMapping("/fullInfo")
    public ResultUtil fullInfo(@ApiParam("code") @RequestParam String code,
                               @ApiParam("password") @RequestParam String password) {
        if (login(code, password).getCode() == 200) {
            Worker_ worker = workerService.get_ByCode(code);
            return ResultUtil.success("success", worker);
        } else return ResultUtil.BFailure("Forbidden", null);
    }

    @ApiOperation("update")
    @PostMapping("/update")
    public ResultUtil update(@ApiParam("worker") @RequestBody WorkerDTO worker,
                             @ApiParam("code") @RequestParam String code,
                             @ApiParam("password") @RequestParam String password) {
        if (login(code, password).getCode() == 200)
            if (worker.getName().length() > 1 && !worker.getTel().isEmpty()) {
                worker.setPassword(passwordEncoder.encode(worker.getPassword()));
                worker = (WorkerDTO) BeanUtil.e2d(workerService.update((Worker_) BeanUtil.d2e(worker), code));
                return ResultUtil.success("success", BeanUtil.e2d(worker));
            } else return ResultUtil.BFailure("form error", null);
        else return ResultUtil.BFailure("Forbidden", null);
    }

    @ApiOperation("login")
    @PostMapping("/login")
    public ResultUtil login(@ApiParam("code") @RequestParam String code,
                            @ApiParam("password") @RequestParam String password) {
        Worker_ worker = workerService.get_ByCode(code);
        if (worker != null)
            if (passwordEncoder.matches(password, worker.getPassword())) {
                return ResultUtil.success("success", BeanUtil.e2d(worker));
            } else return ResultUtil.BFailure("password error", null);
        else return ResultUtil.BFailure("worker error", null);
    }

    @ApiOperation("register")
    @PostMapping("/register")
    public ResultUtil register(@ApiParam("worker") @RequestBody Worker_ worker,
                               @ApiParam("password") @RequestParam String password) {
        if (worker.getPassword().equals(password))
            if (worker.getName().length() > 1 && !worker.getTel().isEmpty()) {
                worker.setPassword(passwordEncoder.encode(worker.getPassword()));
                worker.setPhoto("/pic/sun.png");
                worker = workerService.save(worker);
                return ResultUtil.success("success", BeanUtil.e2d(worker));
            } else return ResultUtil.BFailure("form error", null);
        else return ResultUtil.BFailure("password error", null);
    }
}
