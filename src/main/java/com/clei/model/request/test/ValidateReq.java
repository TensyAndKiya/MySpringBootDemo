package com.clei.model.request.test;

import javax.validation.constraints.NotNull;

/**
 * 验证请求参数
 *
 * @author KIyA
 * @date 2021-04-07
 */
public class ValidateReq {

    /**
     * 消息1
     */
    @NotNull
    private String msg1;

    /**
     * 消息2
     */
    @NotNull(message = "消息2不能为空")
    private String msg2;

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }
}
