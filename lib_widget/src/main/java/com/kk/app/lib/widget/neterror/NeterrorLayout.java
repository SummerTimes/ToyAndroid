package com.kk.app.lib.widget.neterror;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kk.app.lib.widget.R;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
public class NeterrorLayout extends FrameLayout implements View.OnClickListener{

    private OnNetRefresh mOnNetRefresh;
    private TextView tvNetMsg;
    private TextView tvNetRefresh;
    private TextView tvNetSetting;
    private String netServerError;
    private String netTimeoutError;
    private long clickTime = 0;

    public NeterrorLayout(@NonNull Context context) {
        this(context,null);
    }

    public NeterrorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NeterrorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public NeterrorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected <T extends View> T id(int resId){
        return (T)findViewById(resId);
    }

    protected void init(@NonNull Context context){
        if(!isInEditMode()) {
            setVisibility(GONE);
        }
        inflate(context, R.layout.layout_net_error,this);
        tvNetMsg = id(R.id.tv_net_msg);
        tvNetRefresh = id(R.id.tv_net_reresh);
        tvNetSetting = id(R.id.tv_net_setting);
        tvNetRefresh.setOnClickListener(this);
        tvNetSetting.setOnClickListener(this);
        setNetServerTxt(getResources().getString(R.string.lw_hint_net_server));
        setNetTimeoutTxt(getResources().getString(R.string.lw_hint_net_timeout));
    }

    @Override
    public void onClick(View v) {
        if(System.currentTimeMillis() - clickTime < 500){
            return;
        }
        clickTime = System.currentTimeMillis();
        int id = v.getId();
        if(R.id.tv_net_reresh == id){
            if(mOnNetRefresh != null){
                mOnNetRefresh.onNetRefresh();
            }
        }else if(R.id.tv_net_setting == id){
            getContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    public void setOnNetRefresh(OnNetRefresh l){
        this.mOnNetRefresh = l;
    }

    /**
     * 网络连接正常，服务器出错误
     */
    public void onServerError(){
        tvNetMsg.setText(netServerError);
        tvNetSetting.setVisibility(GONE);
        setVisibility(VISIBLE);
    }

    /**
     * 连接超时或网络未连接
     */
    public void onTimeoutError(){
        tvNetMsg.setText(netTimeoutError);
        tvNetSetting.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
    }

    /**
     * 网络和服务都正常
     */
    public void onConnected(){
        setVisibility(GONE);
    }

    public void setNetServerTxt(String txt){
        if(txt != null) {
            this.netServerError = txt;
        }
    }

    public void setNetTimeoutTxt(String txt){
        if(txt != null) {
            this.netTimeoutError = txt;
        }
    }
}
