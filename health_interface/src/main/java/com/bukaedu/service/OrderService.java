package com.bukaedu.service;

import entity.Result;

import java.util.Map;

public interface OrderService {
    Result setOrder(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
