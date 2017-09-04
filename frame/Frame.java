package com.gospell.nms.service.netty.base.frame;

import java.util.List;

import com.gospell.nms.common.utils.FrameTypeContants;
import com.gospell.nms.dto.netty.FrameParam;
import com.gospell.nms.service.netty.base.util.GroupField;

public class Frame extends FrameHead{
	
	private int transId;    // 设备ID

    private int funCode;    // 功能码
    
    private int dataLen;    // 数据长度
    
    private String cpuNo;      //CPU序列号

    private List<FrameParam> params;    // 参数集合 (请求和响应)

 
	public String getCpuNo() {
		return cpuNo;
	}

	public void setCpuNo(String cpuNo) {
		this.cpuNo = cpuNo;
	}

	private int crc16;  // 校验CRC-16

    private String commLink;

    @GroupField(isId = true , isType = false)
    private String commTerminalId;
    
    
    private String commProtocol; // 通信协议
    


    public String getCommProtocol() {
		return commProtocol;
	}

	public void setCommProtocol(String commProtocol) {
		this.commProtocol = commProtocol;
	}

	public Frame() {
        super();
    }

    public Frame(String addrNo, int transId, int funCode, List<FrameParam> params) {
        super(FrameTypeContants.ORDER_HEAD);
        if (null == addrNo) {    // 为 null 时设置默认值，否则无法获取数据
            this.addrNo = "";
        } else {
            this.addrNo = addrNo;
        }
        this.transId = transId;
        this.funCode = funCode;
        this.params = params;
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getFunCode() {
        return funCode;
    }

    public void setFunCode(int funCode) {
        this.funCode = funCode;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public List<FrameParam> getParams() {
        return params;
    }

    public void setParams(List<FrameParam> params) {
        this.params = params;
    }

    public int getCrc16() {
        return crc16;
    }

    public void setCrc16(int crc16) {
        this.crc16 = crc16;
    }

    public String getCommLink() {
        return commLink;
    }

    public void setCommLink(String commLink) {
        this.commLink = commLink;
    }

    public String getCommTerminalId() {
        return commTerminalId;
    }

    public void setCommTerminalId(String commTerminalId) {
        this.commTerminalId = commTerminalId;
    }
}
