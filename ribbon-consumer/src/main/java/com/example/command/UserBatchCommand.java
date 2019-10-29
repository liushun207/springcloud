package com.example.command;

import com.example.entity.User;
import com.example.service.IUserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.List;

/**
 * 批量请求命令
 * @author liushun
 * @since JDK 1.8
 **/
public class UserBatchCommand extends HystrixCommand<List<User>> {
    private IUserService userService;
    private List<Long> userIds;

    public UserBatchCommand(IUserService userService, List<Long> userIds) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
        this.userService = userService;
        this.userIds = userIds;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.findAll(userIds);
    }
}
