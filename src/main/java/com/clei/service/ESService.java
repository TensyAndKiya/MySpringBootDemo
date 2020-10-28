package com.clei.service;

import org.springframework.stereotype.Service;

/**
 * ES service 统一接口
 */
@Service
public interface ESService {

    /**
     * 创建索引
     *
     * @return
     */
    boolean createIndex();
}
