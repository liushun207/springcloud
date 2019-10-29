package com.example.service;

import com.example.entity.User;
import rx.Observable;

import java.util.List;
import java.util.concurrent.Future;

/**
 * IUserService
 * @author liushun
 * @since JDK 1.8
 */
public interface IUserService {
    /**
     * 同步 Gets user by id.
     * @param id the id
     * @return the user by id
     */
    User getUserById(Long id);

    /**
     * 异步 Gets user by id async.
     * @param id the id
     * @return the user by id async
     */
    Future<User> getUserByIdAsync(final String id);

    /**
     * 响应式 Gets user by id async.
     * @param id the id
     * @return the user by id async
     */
    Observable<User> getUserById(final String id);

    /**
     * Update.
     * @param user the user
     */
    void update(User user);

    /**
     * Find user.
     * @param id the id
     * @return the user
     */
    User find(Long id);

    /**
     * Find all list.
     * @param ids the ids
     * @return the list
     */
    List<User> findAll(List<Long> ids);
}
