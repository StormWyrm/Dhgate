package cn.qingfeng.dhgate_map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.qingfeng.dhgate_map.map.MapView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapView.initialize(this);
        setContentView(R.layout.activity_main);
    }
}
