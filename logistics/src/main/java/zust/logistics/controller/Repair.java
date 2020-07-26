package zust.logistics.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zust.logistics.entity.RepairState;
import zust.logistics.entity.dto.RepairDTO;
import zust.logistics.entity.pojo.Repair_;
import zust.logistics.service.RepairService;
import zust.logistics.service.StudentService;
import zust.logistics.service.WorkerService;
import zust.logistics.utils.BeanUtil;
import zust.logistics.utils.ResultUtil;

import java.util.List;

//@CrossOrigin(origins = {"http://127.0.0.1:5500", "null"})
@Api(tags = "报修")
@RestController
@RequestMapping("repair")
public class Repair {
    @Autowired
    RepairService repairService;
    @Autowired
    StudentService studentService;
    @Autowired
    WorkerService workerService;

    @ApiOperation("fixed")
    @PostMapping("/fixed")
    public ResultUtil fixed(@ApiParam("from") @RequestParam("from") int from,
                            @ApiParam("to") @RequestParam("to") int to) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ArrayNode fixed = factory.arrayNode();
        List<Repair_> fix = repairService.getFixedFromTo(from, to);
        ObjectNode repair;
        for (Repair_ repair_ : fix) {
            repair = factory.objectNode();
            repair.put("room", repair_.getRoom());
            repair.put("note", repair_.getNote());
            fixed.add(repair);
        }
        return ResultUtil.success("success", fixed);
    }

    @ApiOperation("submitted")
    @PostMapping("/submitted")
    public ResultUtil submitted(@ApiParam("from") @RequestParam("from") int from,
                                @ApiParam("to") @RequestParam("to") int to) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ArrayNode submitted = factory.arrayNode();
        List<Repair_> submit = repairService.getSubmittedFromTo(from, to);
        ObjectNode repair;
        for (Repair_ repair_ : submit) {
            repair = factory.objectNode();
            repair.put("room", repair_.getRoom());
            repair.put("note", repair_.getNote());
            submitted.add(repair);
        }
        return ResultUtil.success("success", submitted);
    }

    @ApiOperation("top5")
    @PostMapping("/top5")
    public ResultUtil top5() {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectNode result = factory.objectNode();
        ArrayNode fixed = factory.arrayNode();
        ArrayNode submitted = factory.arrayNode();

        List<Repair_> fix = repairService.getTop5ByFixed();
        List<Repair_> submit = repairService.getTop5BySubmitted();
        ObjectNode repair;
        for (Repair_ repair_ : fix) {
            repair = factory.objectNode();
            repair.put("room", repair_.getRoom());
            repair.put("note", repair_.getNote());
            fixed.add(repair);
        }
        for (Repair_ repair_ : submit) {
            repair = factory.objectNode();
            repair.put("room", repair_.getRoom());
            repair.put("note", repair_.getNote());
            submitted.add(repair);
        }

        result.set("fixed", fixed);
        result.set("submitted", submitted);
        return ResultUtil.success("success", result);
    }

    @ApiOperation("fix")
    @PostMapping("/fix")
    public ResultUtil fix(@ApiParam("code") @RequestParam String code,
                          @ApiParam("repair") @RequestBody RepairDTO repairDTO) {
        if (workerService.has(code)) {
            Repair_ repair = repairService.getByRoomAndNote(repairDTO);
            repair.setCode(code);
            repair.setRepairState(RepairState.fixed);
            repair = repairService.update(repair);
            return repair != null ? ResultUtil.success("success", null)
                    : ResultUtil.SFailure("Save Error", null);
        } else return ResultUtil.BFailure("Worker error", null);
    }

    @ApiOperation("submit")
    @PostMapping("/submit")
    public ResultUtil submit(@ApiParam("stuID") @RequestParam String stuID,
                             @ApiParam("repair") @RequestBody RepairDTO repairDTO) {
        if (studentService.has(stuID)) {
            Repair_ repair = (Repair_) BeanUtil.d2e(repairDTO);
            repair.setStuID(stuID);
            repair.setRepairState(RepairState.submitted);
            repair = repairService.save(repair);
            return repair != null ? ResultUtil.success("success", null)
                    : ResultUtil.SFailure("Save Error", null);
        } else return ResultUtil.BFailure("Student error", null);
    }
}
