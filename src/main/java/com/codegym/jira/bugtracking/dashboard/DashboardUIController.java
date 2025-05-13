package com.codegym.jira.bugtracking.dashboard;

import com.codegym.jira.bugtracking.Handlers;
import com.codegym.jira.common.TimestampRepository;
import com.codegym.jira.ref.RefType;
import com.codegym.jira.ref.ReferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(DashboardUIController.DASHBOARD_URL)
@RequiredArgsConstructor
public class DashboardUIController {
    static final String DASHBOARD_URL = "/ui/dashboard";

    private final Handlers.ProjectHandler handler;

    @GetMapping
    public String showDashboard(Model model) {
        log.info("show dashboard");
        model.addAttribute("projects", handler.getAllTos(TimestampRepository.NEWEST_FIRST));
        model.addAttribute("taskStatusRefs", ReferenceService.getRefs(RefType.TASK_STATUS));
        return "dashboard";
    }
}
