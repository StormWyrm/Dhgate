package com.example.dhgatetest2.base;

import java.util.List;

/**
 * Created by liqingfeng on 2017/3/22.
 */

public interface SearchListener {
    List<String> search(String value);
    void click(String value);
    boolean isSupportSearch();
}
