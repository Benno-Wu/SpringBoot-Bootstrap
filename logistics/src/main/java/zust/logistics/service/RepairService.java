package zust.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zust.logistics.dao.RepairDAO;
import zust.logistics.entity.RepairState;
import zust.logistics.entity.dto.RepairDTO;
import zust.logistics.entity.pojo.Repair_;

import java.util.List;

@Service
public class RepairService {
    @Autowired
    RepairDAO repairDAO;

    @Transactional
    public Repair_ save(Repair_ repair) {
        return repairDAO.saveAndFlush(repair);
    }

    @Transactional
    public Repair_ update(Repair_ repair) {
        return save(repair);
    }

    public Repair_ getByRoomAndNote(RepairDTO repairDTO) {
        return repairDAO.getByRoomAndNote(repairDTO.getRoom(), repairDTO.getNote());
    }

    public List<Repair_> getTop5ByFixed() {
        return repairDAO.getTop5ByRepairState(RepairState.fixed);
    }

    public List<Repair_> getTop5BySubmitted() {
        return repairDAO.getTop5ByRepairState(RepairState.submitted);
    }

    public List<Repair_> getFixedFromTo(int from, int to) {
        return repairDAO.getFromToByState(from, to, RepairState.fixed.toString());
    }

    public List<Repair_> getSubmittedFromTo(int from, int to) {
        return repairDAO.getFromToByState(from, to, RepairState.submitted.toString());
    }
}
