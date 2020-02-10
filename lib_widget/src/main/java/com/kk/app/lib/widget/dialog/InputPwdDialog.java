package com.kk.app.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kk.app.lib.widget.R;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
public class InputPwdDialog extends Dialog implements View.OnClickListener {

    private EditText etPwd;
    private PwdInputDialogClickListener pwdInputDialogClickListener;
    private String strTextHint;
    private int maxLength = 6;
    private int inputLength = 0;
    private TextView password_hint;

    public InputPwdDialog(Context context) {
        super(context, R.style.selct_sp_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lw_dialog_input_pwd);
        etPwd = (EditText) findViewById(R.id.tv_pwd);
        if (strTextHint != null) {
            etPwd.setText(strTextHint);
        }
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        final TextView tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        TextView tvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);

        password_hint = (TextView) findViewById(R.id.password_hint);


        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = etPwd.getText();
                password_hint.setVisibility(View.GONE);
                int len = editable.length();
                inputLength = len;
                if (len == maxLength) {
                    tvConfirm.setTextColor(Color.parseColor("#E6143C"));
                    tvConfirm.setClickable(true);
                } else if (len > maxLength) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, maxLength);
                    etPwd.setText(newStr);
                    editable = etPwd.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPwd.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) etPwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.showSoftInput(etPwd, 0);
                }
            }
        }, 300);
    }

    public void setTextHint(String hintStr) {
        if (etPwd != null) {
            etPwd.setText(hintStr);
        } else {
            strTextHint = hintStr;
        }
    }

    public String getEtPwd() {
        return etPwd.getText().toString();
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_cancel) {
            dismiss();
            if (pwdInputDialogClickListener != null) {
                pwdInputDialogClickListener.onCancel();
            }
        } else if (v.getId() == R.id.tv_confirm) {
            if (getEtPwd().length() == maxLength) {
                dismiss();
            } else {
                if (TextUtils.isEmpty(getEtPwd()) || getEtPwd().length() < maxLength) {
                    password_hint.setVisibility(View.VISIBLE);
                    return;
                }
            }
            if (pwdInputDialogClickListener != null) {
                pwdInputDialogClickListener.onConfirm(getEtPwd());
            }
        } else if (v.getId() == R.id.tv_forget_pwd) {
            dismiss();
            if (pwdInputDialogClickListener != null) {
                pwdInputDialogClickListener.onForgetPwd();
            }

        } else {
            dismiss();
        }
    }

    public PwdInputDialogClickListener getPwdInputDialogClickListener() {
        return pwdInputDialogClickListener;
    }

    public void setPwdInputDialogClickListener(
            PwdInputDialogClickListener pwdInputDialogClickListener) {
        this.pwdInputDialogClickListener = pwdInputDialogClickListener;
    }

    public void passWordError() {

        password_hint.setText(getContext().getResources().getString(R.string.lw_password_error));

    }


    public interface PwdInputDialogClickListener {
        void onCancel();

        void onConfirm(String str);

        void onForgetPwd();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}