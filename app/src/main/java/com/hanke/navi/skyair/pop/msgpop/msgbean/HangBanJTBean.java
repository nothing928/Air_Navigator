package com.hanke.navi.skyair.pop.msgpop.msgbean;


/**
 * Created by Che on 2017/1/23.
 */
public class HangBanJTBean {
    public String hangbanhao_jt;//交通信息航班号
    public String juli_jt;//距离

    public String getHangbanhao_jt() {
        return hangbanhao_jt;
    }

    public void setHangbanhao_jt(String hangbanhao_jt) {
        this.hangbanhao_jt = hangbanhao_jt;
    }

    public String getJuli_jt() {
        return juli_jt;
    }

    public void setJuli_jt(String juli_jt) {
        this.juli_jt = juli_jt;
    }

    @Override
    public String toString() {
        return "HangBanJTBean{" +
                "hangbanhao_jt='" + hangbanhao_jt + '\'' +
                ", juli_jt='" + juli_jt + '\'' +
                '}';
    }
}
