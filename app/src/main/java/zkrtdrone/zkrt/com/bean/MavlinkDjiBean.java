package zkrtdrone.zkrt.com.bean;


import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.ToByte;

/**
 * Created by jack_xie on 17-5-15.
 */

public class MavlinkDjiBean {
    @ToByte(order = 0, description = "c")
    private byte startCode;  //固定值
    @ToByte(order = 1,description = "v")
    private byte ver;   //协议版本
    @ToByte(order = 2,description = "a")
    private byte sessionAck;  //f 前7位：表示通信过程 第8位：值 0：数据帧，1：应答帧􀀁
    @ToByte(order = 3,description = "e")
    private byte paddingEnc;  //前7位：表示加密帧数据长度 第8位：值 0：不加密，1：加密
    @ToByte(order = 4,description = "c")
    private byte CMD;        //f  APP_TO_UAV	上位机到下位机 值固定为：0    UAV_TO_APP 	下位机到上位机 值固定为：1
    @ToByte(order = 5,description = "l")
    private byte bylength;   //字节长度
    @ToByte(order = 6,description = "s")
    private byte seq;  //帧序列号，值自增

    //appid
    @ToByte(order = 7,description = "a1")
    private byte appIDOne;      //app的id
    @ToByte(order = 8,description = "a2")
    private byte appIDTwo;       //app的id
    @ToByte(order = 9,description = "a3")
    private byte appIDThree;      //app的id

    //UAVID
    @ToByte(order = 10,description = "u1")
    private byte uavIDOne;      //飞机id
    @ToByte(order = 11,description = "u2")
    private byte uavIDTwo;      //飞机id
    @ToByte(order = 12,description = "u3")
    private byte uavIDThree;      //飞机id
    @ToByte(order = 13,description = "u4")
    private byte uavIDFour;      //飞机id
    @ToByte(order = 14,description = "u5")
    private byte uavIDFIve;      //飞机id
    @ToByte(order = 15,description = "u6")
    private byte uavIDSix;      //飞机id

    @ToByte(order = 16,description = "c")
    private byte command;    //保留字段，值为0

    //Data one
    @ToByte(order = 17, description = "Status1")
    private byte Status1; //值 0xFE 正常；0xFD 异常；0xFC 温度超过上限；0xFB 温度超过下限；
    @ToByte(order = 18, description = "TEMPERATURE1_1")
    private byte TEMPERATURE1One; // 温度1值为获取值除10，单位℃，
    @ToByte(order = 19, description = "TEMPERATURE1_2")
    private byte TEMPERATURE1Two; // 温度1值为获取值除10，单位℃，
    @ToByte(order = 20, description = "Status2")
    private byte Status2; //

    @ToByte(order = 21, description = "TEMPERATURE2_1")
    private byte TEMPERATURE2One;
    @ToByte(order = 22, description = "TEMPERATURE2_2")
    private byte TEMPERATURE2Two;

    //TEMPERATURE2_Low
    @ToByte(order = 23, description = "TEMPERATURE2_LowOne")
    private byte TEMPERATURE2_LowOne;
    @ToByte(order = 24, description = "TEMPERATURE2_LowTwo")
    private byte TEMPERATURE2_LowTwo;

    //TEMPERATURE2_High
    @ToByte(order = 25, description = "TEMPERATURE2_HighOne")
    private byte TEMPERATURE2_HighOne;
    @ToByte(order = 26, description = "TEMPERATURE2_HighTwo")
    private byte TEMPERATURE2_HighTwo;

    @ToByte(order = 27, description = "Gas_Status")
    private byte Gas_Status;

    @ToByte(order = 28, description = "Gas_Num1")
    private byte Gas_Num1;
    @ToByte(order = 29, description = "Gas_Value1_1")
    private byte Gas_Value1_One;
    @ToByte(order = 30, description = "Gas_Value1_2")
    private byte Gas_Value1_Two;

    @ToByte(order = 31, description = "Gas_Num2")
    private byte Gas_Num2;
    //Gas_Value2
    @ToByte(order = 32, description = "Gas_Value2_1")
    private byte Gas_Value2_One;
    @ToByte(order = 33, description = "Gas_Value2_2")
    private byte Gas_Value2_Two;

    @ToByte(order = 34, description = "Gas_Num3")
    private byte Gas_Num3;
    //Gas_Value3
    @ToByte(order = 35, description = "Gas_Value3_1")
    private byte Gas_Value3_One;
    @ToByte(order = 36, description = "Gas_Value3_2")
    private byte Gas_Value3_Two;

    @ToByte(order = 37, description = "Gas_Num4")
    private byte Gas_Num4;
    //Gas_Value4
    @ToByte(order = 38, description = "Gas_Value4_1")
    private byte Gas_Value4_One;
    @ToByte(order = 39, description = "Gas_Value4_2")
    private byte Gas_Value4_Two;

    //Device_Status
    @ToByte(order = 40, description = "Device_Status_1")
    private byte Device_Status_One;
    @ToByte(order = 41, description = "Device_Status_2")
    private byte Device_Status_Two;
    @ToByte(order = 42, description = "Device_Status_2")
    private byte Device_Status_Three;

    //Set_feedback
    @ToByte(order = 43, description = "Set_feedback_1")
    private byte Set_feedback_One;
    @ToByte(order = 44, description = "Set_feedback_2")
    private byte Set_feedback_Two;
    @ToByte(order = 45, description = "Set_feedback_2")
    private byte Set_feedback_Three;

    //Insmod_Status
    @ToByte(order = 46, description = "Insmod_Status")
    private byte Insmod_Status;

    ///*****
    //CRC
    @ToByte(order = 47,description = "crc1")
    private byte crc1;
    @ToByte(order = 48,description = "crc2")
    private byte crc2;
    @ToByte(order = 49,description = "endCode")
    private byte endCode;     //固定值BE

    public byte getStartCode() {
        return startCode;
    }

    public byte getVer() {
        return ver;
    }

    public byte getSessionAck() {
        return sessionAck;
    }

    public byte getPaddingEnc() {
        return paddingEnc;
    }

    public byte getCMD() {
        return CMD;
    }

    public byte getBylength() {
        return bylength;
    }

    public byte getSeq() {
        return seq;
    }

    public byte getAppIDOne() {
        return appIDOne;
    }

    public byte getAppIDTwo() {
        return appIDTwo;
    }

    public byte getAppIDThree() {
        return appIDThree;
    }

    public byte getUavIDOne() {
        return uavIDOne;
    }

    public byte getUavIDTwo() {
        return uavIDTwo;
    }

    public byte getUavIDThree() {
        return uavIDThree;
    }

    public byte getUavIDFour() {
        return uavIDFour;
    }

    public byte getUavIDFIve() {
        return uavIDFIve;
    }

    public byte getUavIDSix() {
        return uavIDSix;
    }

    public byte getCommand() {
        return command;
    }

    public byte getStatus1() {
        return Status1;
    }

    public byte getTEMPERATURE1One() {
        return TEMPERATURE1One;
    }

    public byte getTEMPERATURE1Two() {
        return TEMPERATURE1Two;
    }

    public byte getStatus2() {
        return Status2;
    }

    public byte getTEMPERATURE2One() {
        return TEMPERATURE2One;
    }

    public byte getTEMPERATURE2Two() {
        return TEMPERATURE2Two;
    }

    public byte getTEMPERATURE2_LowOne() {
        return TEMPERATURE2_LowOne;
    }

    public byte getTEMPERATURE2_LowTwo() {
        return TEMPERATURE2_LowTwo;
    }

    public byte getTEMPERATURE2_HighOne() {
        return TEMPERATURE2_HighOne;
    }

    public byte getTEMPERATURE2_HighTwo() {
        return TEMPERATURE2_HighTwo;
    }

    public byte getGas_Status() {
        return Gas_Status;
    }

    public byte getGas_Num1() {
        return Gas_Num1;
    }

    public byte getGas_Value1_One() {
        return Gas_Value1_One;
    }

    public byte getGas_Value1_Two() {
        return Gas_Value1_Two;
    }

    public byte getGas_Num2() {
        return Gas_Num2;
    }

    public byte getGas_Value2_One() {
        return Gas_Value2_One;
    }

    public byte getGas_Value2_Two() {
        return Gas_Value2_Two;
    }

    public byte getGas_Num3() {
        return Gas_Num3;
    }

    public byte getGas_Value3_One() {
        return Gas_Value3_One;
    }

    public byte getGas_Value3_Two() {
        return Gas_Value3_Two;
    }

    public byte getGas_Num4() {
        return Gas_Num4;
    }

    public byte getGas_Value4_One() {
        return Gas_Value4_One;
    }

    public byte getGas_Value4_Two() {
        return Gas_Value4_Two;
    }

    public byte getDevice_Status_One() {
        return Device_Status_One;
    }

    public byte getDevice_Status_Two() {
        return Device_Status_Two;
    }

    public byte getDevice_Status_Three() {
        return Device_Status_Three;
    }

    public byte getSet_feedback_One() {
        return Set_feedback_One;
    }

    public byte getSet_feedback_Two() {
        return Set_feedback_Two;
    }

    public byte getSet_feedback_Three() {
        return Set_feedback_Three;
    }

    public byte getInsmod_Status() {
        return Insmod_Status;
    }

    public byte getCrc1() {
        return crc1;
    }

    public byte getCrc2() {
        return crc2;
    }

    public byte getEndCode() {
        return endCode;
    }
}
