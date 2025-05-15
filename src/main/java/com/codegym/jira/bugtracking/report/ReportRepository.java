package com.codegym.jira.bugtracking.report;

import com.codegym.jira.common.BaseRepository;
import com.codegym.jira.bugtracking.task.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReportRepository extends BaseRepository<Task> {
    @Query("SELECT new com.codegym.jira.bugtracking.report.TaskSummary(r.title, COUNT(t.id)) " +
            "FROM Reference r LEFT JOIN Task t " +
            "ON r.code = t.statusCode AND t.sprint.id = :sprintId " +
            "WHERE r.refType = :#{T(com.codegym.jira.ref.RefType).TASK_STATUS} " +
            "GROUP BY r.title, r.id " +
            "ORDER BY r.id")
    List<TaskSummary> getTaskSummaries(long sprintId);
}
