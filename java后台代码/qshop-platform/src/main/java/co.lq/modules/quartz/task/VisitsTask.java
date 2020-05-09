package co.lq.modules.quartz.task;

import org.springframework.stereotype.Component;

import co.lq.modules.monitor.service.VisitsService;

/**
 * @author billy
 * @date 2018-12-25
 */
@Component
public class VisitsTask {

    private final VisitsService visitsService;

    public VisitsTask(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run() {
        visitsService.save();
    }
}
