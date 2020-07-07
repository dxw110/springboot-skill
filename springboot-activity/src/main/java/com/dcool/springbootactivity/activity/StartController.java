package com.dcool.springbootactivity.activity;

import cn.hutool.core.collection.CollUtil;
import com.dcool.springbootactivity.R.R;
import lombok.AllArgsConstructor;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Lao Cat
 * @Date: 2020/4/15 9:34
 */
@RestController
@RequestMapping("start")
@AllArgsConstructor
public class StartController {
    private final RuntimeService runtimeService;

    /**
     * 启动流程
     * processKey  流程key  ** 每一个流程有对应的一个key这个是某一个流程内固定的写在bpmn内的
     * user  启动流程的用户
     *
     * @author DCool
     * date 2020-06-05
     */
//    @GetMapping("/{processKey}/{userKey}/{user}")
//    public R start(@PathVariable("processKey") String processKey, @PathVariable("userKey") String[] userKey,
//                   @PathVariable(required = false, value = "user") String user) {
//        HashMap<String, Object> variables = CollUtil.newHashMap(1);
////        variable.put("userKey", userKey);
//        String[] strings = new String[11];
//        strings[0] = "11";
//        strings[1] = "zhangsan";
//        strings[2] = "李四";
//        strings[3] = "王五";
//        strings[4] = "小刘";
//        strings[5] = "小段";
//        strings[6] = "小刘";
//        strings[7] = "小王";
//        strings[8] = "小马";
//        strings[9] = "思聪";
//
//
//        variables.put("user", strings);
//
//        ProcessInstance instance = null;
//
//        try {
//            instance = runtimeService
//                    .startProcessInstanceByKey(processKey, user, variables);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (Objects.nonNull(instance)) {
//            HashMap<String, String> result = CollUtil.newHashMap(2);
//            // 流程实例id
//            result.put("processID", instance.getId());
//            // 流程定义id
//            result.put("processDefinitionKey", instance.getProcessDefinitionId());
//
//            return R.ok(result, "启动成功");
//        }
//        return R.failed();
//    }

    /**
     * 查询流程实例
     * processKey 流程key
     *
     * @author DCool
     * date 2020-06-05
     */
    @GetMapping("/searchByKey/{processKey}")
    public R searchProcessInstanceByKey(@PathVariable("processKey") String processKey) {
        List<ProcessInstance> runningList = CollUtil.newArrayList();

        try {
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            runningList = processInstanceQuery.processDefinitionKey(processKey).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int size = runningList.size();
        if (size > 0) {
            List<Map<String, String>> resultList = CollUtil.newArrayList();
            runningList.forEach(running -> {
                HashMap<String, String> resultMap = CollUtil.newHashMap(2);
                // 流程实例id
                resultMap.put("processID", running.getId());
                // 流程定义id
                resultMap.put("processDefinitionKey", running.getProcessDefinitionId());
                resultList.add(resultMap);
            });
            return R.ok(resultList);
        }
        return R.failed();
    }


    /**
     * 查询流程实例
     * processID  实例id
     *
     * @author DCool
     * date 2020-06-05
     */
    @GetMapping("/searchByID/{processID}")
    public R searchProcessInstanceByID(@PathVariable("processID") String processID) {
        ProcessInstance instance = null;

        try {
            instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processID)
                    .singleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Objects.nonNull(instance)) {
            HashMap<String, String> result = CollUtil.newHashMap(2);
            result.put("processID", instance.getId());
            result.put("processDefinitionKey", instance.getProcessDefinitionId());
            return R.ok(result);
        }
        return R.failed();
    }


    /**
     * 根据流程实例key删除流程实例
     * processKey 流程实例Key
     *
     * @author DCool
     * date 2020-06-05
     */
    @DeleteMapping("/processInstance")
    public R deleteProcessInstanceByKey(@RequestParam("processKey") String processKey) {
        List<ProcessInstance> runningList = CollUtil.newArrayList();

        try {
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            runningList = processInstanceQuery.processDefinitionKey(processKey).list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int size = runningList.size();
        if (size > 0) {

            runningList.forEach(running -> {
                runtimeService.deleteProcessInstance(running.getId(), "删除");
            });

            return R.ok("删除成功");
        }
        return R.failed();
    }


    /**
     * 根据流程实例ID删除流程实例
     * processID  流程实例ID
     *
     * @author DCool
     * date 2020-06-05
     */
    @DeleteMapping("/processInstance/{processID}")
    public R deleteProcessInstanceByID(@PathVariable("processID") String processID) {

        try {
            runtimeService.deleteProcessInstance(processID, "删除" + processID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok("删除成功");
    }

}
