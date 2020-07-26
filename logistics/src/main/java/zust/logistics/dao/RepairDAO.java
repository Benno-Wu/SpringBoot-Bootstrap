package zust.logistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zust.logistics.entity.RepairState;
import zust.logistics.entity.pojo.Repair_;

import java.util.List;

public interface RepairDAO extends JpaRepository<Repair_, Integer> {
    Repair_ getByRoomAndNote(String room, String note);

    List<Repair_> getTop5ByRepairState(RepairState repairState);

    @Query(value = "select * from repair_ where repair_state = ?3 limit ?1, ?2 ", nativeQuery = true)
    List<Repair_> getFromToByState(int from, int to, String repairState);
}
