package zust.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zust.logistics.dao.WorkerDAO;
import zust.logistics.entity.pojo.Worker_;

@Service
public class WorkerService {

    @Autowired
    private WorkerDAO workerDAO;

    public Worker_ get_ByCode(String code) {
        return workerDAO.getByCode(code);
    }

    @Transactional
    public Worker_ save(Worker_ worker) {
        return workerDAO.saveAndFlush(worker);
    }

    @Transactional
    public Worker_ update(Worker_ worker) {
        worker.setId(workerDAO.getByCode(worker.getCode()).getId());
        return workerDAO.saveAndFlush(worker);
    }

    @Transactional
    public Worker_ update(Worker_ worker, String code) {
        worker.setId(workerDAO.getByCode(code).getId());
        return workerDAO.saveAndFlush(worker);
    }

    public boolean has(String code) {
        return workerDAO.getByCode(code) != null;
    }
}
