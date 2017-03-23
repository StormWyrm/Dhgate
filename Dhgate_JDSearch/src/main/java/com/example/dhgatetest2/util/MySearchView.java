package com.example.dhgatetest2.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhgatetest2.R;

/**
 * @AUTHER: 李青峰
 * @EMAIL: 1021690791@qq.com
 * @PHONE: 18045142956
 * @DATE: 2017/3/23 15:05
 * @DESC: 自定义SearchView
 * @VERSION: V1.0
 */
public class MySearchView extends FrameLayout {
    private EditText editText;
    private ImageView imageView;
    private OnQueryTextListener mOnQueryChangeListener;

    public void setOnQueryTextListener(OnQueryTextListener listener) {
        mOnQueryChangeListener = listener;
    }

    public MySearchView(Context context) {
        this(context, null);
    }

    public MySearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(getContext(), R.layout.layout_search_view, this);
        editText = (EditText) view.findViewById(R.id.et_custom);
        imageView = (ImageView) view.findViewById(R.id.iv_clear);
        initListener();
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
            editText.setSelection(text.length());
        }

    }

    public void setHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            editText.setHint(hint);
        }
    }

    public EditText getEditText() {
        return editText;
    }

    private void initListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(VISIBLE);
                }
                if (mOnQueryChangeListener != null) {
                    mOnQueryChangeListener.onQueryTextChange(s.toString());
                }
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    return mOnQueryChangeListener.onQueryTextSubmit(getText());
                }
                return false;
            }
        });

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                imageView.setVisibility(INVISIBLE);
            }
        });
    }

    public interface OnQueryTextListener {

        boolean onQueryTextSubmit(String query);

        void onQueryTextChange(String newText);
    }

}
