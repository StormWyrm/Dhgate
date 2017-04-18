package cn.qingfeng.dhgate_map.map;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.mapapi.SDKInitializer;

import cn.qingfeng.dhgate_map.R;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/4/18 22:33
 * @DESC: 自定义Map
 * @VERSION: V1.0
 */
public class MapView extends FrameLayout {

    public MapView(@NonNull Context context) {
        this(context,null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_my_map, this);
    }

    public static void initialize(Context context) {
        SDKInitializer.initialize(context.getApplicationContext());
    }
}
