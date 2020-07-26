package zust.logistics.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zust.logistics.entity.pojo.Worker_;

public interface WorkerDAO extends JpaRepository<Worker_, Integer> {
    Worker_ getByCode(String code);
}
