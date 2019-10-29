package com.example.command;

import com.example.entity.User;
import com.example.service.IUserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求合并器
 * @author liushun
 * @since JDK 1.8
 **/
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Long> {
    private IUserService userService;
    private Long userId;

    public UserCollapseCommand(IUserService userService, Long userId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
                // 设置合并延迟时间 100 毫秒
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userService = userService;
        this.userId = userId;
    }

    /**
     * 获取参数
     * @return
     */
    @Override
    public Long getRequestArgument() {
        return userId;
    }

    /**
     * 创建合并请求
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        List<Long> userIds = new ArrayList<>(collapsedRequests.size());

        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));

        return new UserBatchCommand(userService, userIds);
    }

    /**
     * 完成批量结果转单个结果的转换
     */
    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        int count = 0;
        for(CollapsedRequest<User, Long> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
