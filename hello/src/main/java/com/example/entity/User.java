package com.example.entity;

import lombok.Data;

/**
 * User
 * @author liushun
 * @since JDK 1.8
 */
@Data
public class User {
    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
