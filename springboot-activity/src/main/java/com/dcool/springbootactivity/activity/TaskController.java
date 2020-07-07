package com.dcool.springbootactivity.activity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.dcool.springbootactivity.R.R;
import com.dcool.springbootactivity.activity.entity.ActHiProcinst;
import com.dcool.springbootactivity.activity.mapper.ActHiProcinstMapper;
import com.dcool.springbootactivity.web.entity.YzApprove;
import com.dcool.springbootactivity.web.mapper.ApproveWebMapper;
import lombok.AllArgsConstructor;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lao Cat
 * @Date: 2020/4/15 10:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;
    private final ProcessEngine processEngine;
    private final ActHiProcinstMapper actHiProcinstMapper;
    private final ApproveWebMapper approveWebMapper;

    /**
     * 根据流程assignee查询当前人的个人任务
     * assignee  代理人（当前用户）
     *
     * @author DCool
     * date 2020-06-05
     */
    @GetMapping("/{assignee}")
    public R findTaskByAssignee(@PathVariable("assignee") String assignee) {

        //创建任务查询对象
        List<Task> taskList = CollUtil.newArrayList();
        try {
            taskList = taskService.createTaskQuery()
                    //指定个人任务查询
                    .taskAssignee(assignee)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CollUtil.isNotEmpty(taskList) && taskList.size() > 0) {
            List<Map<String, String>> resultList = CollUtil.newArrayList();

            taskList.forEach(task -> {
                Map<String, String> resultMap = CollUtil.newHashMap(7);

                /* 任务ID */
                resultMap.put("taskID", task.getId());

                /* 任务名称 */
                resultMap.put("taskName", task.getName());

                /* 任务的创建时间 */
                resultMap.put("taskCreateTime", DateUtil.format(task.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

                /* 任务的办理人 */
                resultMap.put("taskAssignee", task.getAssignee());

                /* 流程实例ID */
                resultMap.put("processInstanceId", task.getProcessInstanceId());

                /* 执行对象ID */
                resultMap.put("executionId", task.getExecutionId());

                /* 流程定义ID */
                resultMap.put("processDefinitionId", task.getProcessDefinitionId());
                resultList.add(resultMap);

            });
            return R.ok(resultList);

        } else {

            return R.ok(Collections.emptyList());
        }
    }

    /**
     * 完成任务，任务进入下一个节点
     * taskId  任务ID
     * args  eg: days 请假的天数 提交的申请内容
     *
     * @author DCool
     * date 2020-06-05
     */
//    @PostMapping()
//    public R completeTask(@RequestParam("taskId") String taskId, @RequestParam(required = false, value = "args") String args) {
//
//        try {
//            Map<String, Object> variables = CollUtil.newHashMap(1);
////            variables.put("days", isNumber(args) ? Integer.valueOf(args) : 0);
////            variables.put("args",args);
//            if (CollUtil.isNotEmpty(variables)) {
//                taskService.complete(taskId, variables);
//            } else {
//                taskService.complete(taskId);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return R.ok(taskId);
//    }

    /**
     * 判断是否为数字
     *
     * @author DCool
     * date 2020-06-05
     */
    private Boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 历史任务查询
     */
    @GetMapping("/getHisTaskByAss")
    @Transactional(rollbackFor = Exception.class)
    public R historyTaskList(@RequestParam String assigneeId) {
        if (StringUtils.isEmpty(assigneeId)) {
            return R.failed("传入的审批人id为空");
        }
        List<Object> taskList = new ArrayList();
        //查询历史任务实例
        List<HistoricTaskInstance> lists = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee(assigneeId)
                .finished()
                .list();
        lists.forEach(x -> {
            //获取提交用户id和审批id
            ActHiProcinst businessKey = actHiProcinstMapper.getBusinessKey(x.getProcessInstanceId());
            //获取审批id，拿审批id去查审批内容
            String[] approveId = businessKey.getBusinessKey().split(":");
            if (!StringUtils.isEmpty(approveId)) {
                YzApprove yzApprove = approveWebMapper.selectById(approveId[1]);
                taskList.add(yzApprove);
            }
        });
        return R.ok(taskList);
    }


    /**
     * 修改任务处理人>>>>委托任务处理人
     */
    @GetMapping("/reset")
    @Transactional(rollbackFor = Exception.class)
    public R reset(@RequestParam String processInstanceId, @RequestParam String assignee) {
        if (StringUtils.isEmpty(processInstanceId) && StringUtils.isEmpty(assignee)) {
            return R.failed("实例id或者委托人id为空");
        }
        //查到task
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        String taskId = task.getId();
        taskService.setAssignee(taskId, assignee);
        return R.ok("替换成功");
    }
}
