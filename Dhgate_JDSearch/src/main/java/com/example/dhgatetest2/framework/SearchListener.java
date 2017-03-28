package com.example.dhgatetest2.framework;

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
     * 点击历史记录搜索
     */
    int SEARCH_FORM_HISTORY  = 0;

    /**
     * 点击热词搜索
     */
    int SEARCH_FROM_HOT = 1;
    /**
     * 根据搜索 想服务器获取相应数据
     * @param value
     * @return
     */
    List<String> search(String value);

    /**
     * 点击跳转搜索详情界面
     * @param value 点击的搜索词
     * @param type  点击的搜索词的类型
     */
    void click(String value,int type);


    /**
     * 判断是否支持搜索
     * @return
     */
    boolean isSupportSearch();
}
