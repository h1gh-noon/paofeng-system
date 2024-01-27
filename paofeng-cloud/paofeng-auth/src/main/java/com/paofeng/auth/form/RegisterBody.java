package com.paofeng.auth.form;

/**
 * 用户注册对象
 *
 * @author ruoyi
 */
public class RegisterBody extends LoginBody {


    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
