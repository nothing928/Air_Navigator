package com.hanke.navi.skyair.pop.msgpop.msgbean;


/**
 * Created by Che on 2017/1/23.
 */
public class HangBanGJBean {
    public String hangbanhao_gj;//告警信息航班号
    public String jinggaoxinxi_gj;//警告信息

    public String getHangbanhao_gj() {
        return hangbanhao_gj;
    }

    public void setHangbanhao_gj(String hangbanhao_gj) {
        this.hangbanhao_gj = hangbanhao_gj;
    }

    public String getJinggaoxinxi_gj() {
        return jinggaoxinxi_gj;
    }

    public void setJinggaoxinxi_gj(String jinggaoxinxi_gj) {
        this.jinggaoxinxi_gj = jinggaoxinxi_gj;
    }

    @Override
    public String toString() {
        return "HangBanGJBean{" +
                "hangbanhao_gj='" + hangbanhao_gj + '\'' +
                ", jinggaoxinxi_gj='" + jinggaoxinxi_gj + '\'' +
                '}';
    }
}
