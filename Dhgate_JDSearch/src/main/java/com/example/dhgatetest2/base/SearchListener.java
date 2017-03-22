package com.example.dhgatetest2.base;

import java.util.List;


/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/22 22:17
 * @DESC: 提供给上层调用者的接口
 * @VERSION: V1.0
 */
public interface SearchListener {

    /**
     * 根据搜索 想服务器获取相应数据
     * @param value
     * @return
     */
    List<String> search(String value);

    /**
     * 点击跳转搜索详情界面
     * @param value
     */
    void click(String value);


    /**
     * 判断是否支持搜索
     * @return
     */
    boolean isSupportSearch();
}
