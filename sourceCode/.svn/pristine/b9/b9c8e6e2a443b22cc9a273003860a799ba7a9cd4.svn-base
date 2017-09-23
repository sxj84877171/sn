package com.sunvote.txpad.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sunvote.txpad.R;
import com.sunvote.txpad.base.BaseActivity;
import com.sunvote.txpad.ui.main.HomeActivity;

/**
 * Created by Elvis on 2017/9/8.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    private EditText usernameEditText ;
    private EditText passwordEditText ;
    private Button loginButton ;
    private LoginPresent loginPresent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutID() {
        return R.layout.login_main;
    }
    @Override
    public void initMVP() {
        loginPresent = new LoginPresent();
        loginPresent.setModel(new LoginModel());
        loginPresent.setView(this);
    }
    @Override
    public void initView() {
        usernameEditText = getViewById(R.id.username);
        passwordEditText = getViewById(R.id.password);
        loginButton = getViewById(R.id.submit);
    }
    @Override
    public void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginPresent.login(username,password);
            }
        });
    }
    @Override
    public void gotoHomePage(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void showError() {
        showToast(R.string.login_username_or_password_error);
    }
    @Override
    public void showUsernameEmpty() {
        showToast(R.string.login_username_is_empty);
    }
    @Override
    public void showPassowrdEmpty() {
        showToast(R.string.login_password_is_empty);
    }
    @Override
    public void showPasswordError() {
        showToast(R.string.login_password_invald);
    }

}
