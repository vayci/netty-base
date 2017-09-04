package com.gospell.nms.service.netty.base.frame;

import com.gospell.nms.service.netty.base.util.GroupField;

public class FrameHead {

	@GroupField( id=false , type=true )
    protected int orderType;  // 命令类型码

    protected String addrNo;  // 地点（台站）编码

    protected int transType;  // 设备类型（发射机类型）

    protected int manufactorNo; // 厂家编号

    protected int hardM;  // 硬件主版本号

    protected int hardS;  // 硬件次版本号

    protected int softM;  // 软件主版本号

    protected int softS;  // 软件次版本号

    protected int reserverd;  // 预留字段

    public FrameHead(){
        this.reserverd = 0;
    }

    public FrameHead(int orderType){
        this.reserverd = 0;
        this.orderType = orderType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getAddrNo() {
        return addrNo;
    }

    public void setAddrNo(String addrNo) {
        this.addrNo = addrNo;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public int getManufactorNo() {
        return manufactorNo;
    }

    public void setManufactorNo(int manufactorNo) {
        this.manufactorNo = manufactorNo;
    }

    public int getHardM() {
        return hardM;
    }

    public void setHardM(int hardM) {
        this.hardM = hardM;
    }

    public int getHardS() {
        return hardS;
    }

    public void setHardS(int hardS) {
        this.hardS = hardS;
    }

    public int getSoftM() {
        return softM;
    }

    public void setSoftM(int softM) {
        this.softM = softM;
    }

    public int getSoftS() {
        return softS;
    }

    public void setSoftS(int softS) {
        this.softS = softS;
    }

    public int getReserverd() {
        return reserverd;
    }

    public void setReserverd(int reserverd) {
        this.reserverd = reserverd;
    }
}
