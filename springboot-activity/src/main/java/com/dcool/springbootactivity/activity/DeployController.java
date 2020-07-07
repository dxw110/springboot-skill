package com.dcool.springbootactivity.activity;

import cn.hutool.core.collection.CollUtil;
import com.dcool.springbootactivity.R.R;
import lombok.AllArgsConstructor;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;


/**
 * @author DCool
 * date 2020-06-05
 */
@RestController
@RequestMapping("deploy")
@AllArgsConstructor
public class DeployController {

    private final RepositoryService repositoryService;

    /**
     * 部署流程
     * bpmnName 设计的流程图名称
     * name  流程名 eg:请假流程
     * 172.
     *
     * @author DCool
     * date 2020-06-05
     */
    @PostMapping
    public R deploy(@RequestParam("bpmnName") String bpmnName, @RequestParam("name") String name) {

        //开始创建新的部署
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name(name);
        Deployment deployment = null;

        try {
            deployment = deploymentBuilder
                    .addClasspathResource("processes/" + bpmnName + ".bpmn")
//                    .addClasspathResource("processes/"+ bpmnName +".png")
                    .deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Objects.nonNull(deployment)) {

            HashMap<String, String> result = CollUtil.newHashMap(2);
            result.put("deployID", deployment.getId());
            result.put("deployName", deployment.getName());

            return R.ok(result);
        }
        return R.failed("部署失败");

    }

    /**
     * 删除流程
     * deploymentId  部署ID  同: deployID
     *
     * @author DCool
     * date 2020-06-05
     */
    @DeleteMapping("/{deploymentId}")
    public R deleteProcess(@PathVariable("deploymentId") String deploymentId) {

        try {
            repositoryService.deleteDeployment(deploymentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 级联删除
//        repositoryService.deleteDeployment(deploymentId,true);
        return R.ok("删除成功");
    }
}
