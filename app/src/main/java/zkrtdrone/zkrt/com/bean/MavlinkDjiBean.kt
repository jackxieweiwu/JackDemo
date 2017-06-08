package zkrtdrone.zkrt.com.bean

import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.ToByte

/**
 * Created by jack_xie on 17-6-6.
 */

class MavlinkDjiBean {
    @ToByte(order = 0, description = "c")
    val startCode: Byte = 0  //固定值
    @ToByte(order = 1, description = "v")
    val ver: Byte = 0   //协议版本
    @ToByte(order = 2, description = "a")
    val sessionAck: Byte = 0  //f 前7位：表示通信过程 第8位：值 0：数据帧，1：应答帧􀀁
    @ToByte(order = 3, description = "e")
    val paddingEnc: Byte = 0  //前7位：表示加密帧数据长度 第8位：值 0：不加密，1：加密
    @ToByte(order = 4, description = "c")
    val cmd: Byte = 0        //f  APP_TO_UAV	上位机到下位机 值固定为：0    UAV_TO_APP 	下位机到上位机 值固定为：1
    @ToByte(order = 5, description = "l")
    val bylength: Byte = 0   //字节长度
    @ToByte(order = 6, description = "s")
    val seq: Byte = 0  //帧序列号，值自增

    //appid
    @ToByte(order = 7, description = "a1")
    val appIDOne: Byte = 0      //app的id
    @ToByte(order = 8, description = "a2")
    val appIDTwo: Byte = 0       //app的id
    @ToByte(order = 9, description = "a3")
    val appIDThree: Byte = 0      //app的id

    //UAVID
    @ToByte(order = 10, description = "u1")
    val uavIDOne: Byte = 0      //飞机id
    @ToByte(order = 11, description = "u2")
    val uavIDTwo: Byte = 0      //飞机id
    @ToByte(order = 12, description = "u3")
    val uavIDThree: Byte = 0      //飞机id
    @ToByte(order = 13, description = "u4")
    val uavIDFour: Byte = 0      //飞机id
    @ToByte(order = 14, description = "u5")
    val uavIDFIve: Byte = 0      //飞机id
    @ToByte(order = 15, description = "u6")
    val uavIDSix: Byte = 0      //飞机id

    @ToByte(order = 16, description = "c")
    val command: Byte = 0    //保留字段，值为0


    //Data one
    @ToByte(order = 17, description = "Status1")
    val status1: Byte = 0 //值 0xFE 正常；0xFD 异常；0xFC 温度超过上限；0xFB 温度超过下限；
    @ToByte(order = 18, description = "TEMPERATURE1_1")
    val temperaturE1One: Byte = 0 // 温度1值为获取值除10，单位℃，
    @ToByte(order = 19, description = "TEMPERATURE1_2")
    val temperaturE1Two: Byte = 0 // 温度1值为获取值除10，单位℃，
    @ToByte(order = 20, description = "Status2")
    val status2: Byte = 0 //

    @ToByte(order = 21, description = "TEMPERATURE2_1")
    val temperaturE2One: Byte = 0
    @ToByte(order = 22, description = "TEMPERATURE2_2")
    val temperaturE2Two: Byte = 0

    //TEMPERATURE2_Low
    @ToByte(order = 23, description = "TEMPERATURE2_LowOne")
    val temperaturE2_LowOne: Byte = 0
    @ToByte(order = 24, description = "TEMPERATURE2_LowTwo")
    val temperaturE2_LowTwo: Byte = 0

    //TEMPERATURE2_High
    @ToByte(order = 25, description = "TEMPERATURE2_HighOne")
    val temperaturE2_HighOne: Byte = 0
    @ToByte(order = 26, description = "TEMPERATURE2_HighTwo")
    val temperaturE2_HighTwo: Byte = 0

    @ToByte(order = 27, description = "Gas_Status")
    val gas_Status: Byte = 0

    @ToByte(order = 28, description = "Gas_Num1")
    val gas_Num1: Byte = 0
    @ToByte(order = 29, description = "Gas_Value1_1")
    val gas_Value1_One: Byte = 0
    @ToByte(order = 30, description = "Gas_Value1_2")
    val gas_Value1_Two: Byte = 0

    @ToByte(order = 31, description = "Gas_Num2")
    val gas_Num2: Byte = 0
    //Gas_Value2
    @ToByte(order = 32, description = "Gas_Value2_1")
    val gas_Value2_One: Byte = 0
    @ToByte(order = 33, description = "Gas_Value2_2")
    val gas_Value2_Two: Byte = 0

    @ToByte(order = 34, description = "Gas_Num3")
    val gas_Num3: Byte = 0
    //Gas_Value3
    @ToByte(order = 35, description = "Gas_Value3_1")
    val gas_Value3_One: Byte = 0    //19
    @ToByte(order = 36, description = "Gas_Value3_2")
    val gas_Value3_Two: Byte = 0   //20

    @ToByte(order = 37, description = "Gas_Num4")
    val gas_Num4: Byte = 0
    //Gas_Value4
    @ToByte(order = 38, description = "Gas_Value4_1")
    val gas_Value4_One: Byte = 0
    @ToByte(order = 39, description = "Gas_Value4_2")
    val gas_Value4_Two: Byte = 0  //23

    //Device_Status
    @ToByte(order = 40, description = "Device_Status_1")
    val device_Status_One: Byte = 0
    @ToByte(order = 41, description = "Device_Status_2")
    val device_Status_Two: Byte = 0
    @ToByte(order = 42, description = "Device_Status_2")
    val device_Status_Three: Byte = 0  //26

    //Set_feedback
    @ToByte(order = 43, description = "Set_feedback_1")
    val set_feedback_One: Byte = 0
    @ToByte(order = 44, description = "Set_feedback_2")
    val set_feedback_Two: Byte = 0
    @ToByte(order = 45, description = "Set_feedback_2")
    val set_feedback_Three: Byte = 0

    //Insmod_Status
    @ToByte(order = 46, description = "Insmod_Status")
    val insmod_Status: Byte = 0


    ///*****
    //CRC
    @ToByte(order = 47, description = "crc1")
    val crc1: Byte = 0
    @ToByte(order = 48, description = "crc2")
    val crc2: Byte = 0
    @ToByte(order = 49, description = "endCode")
    val endCode: Byte = 0     //固定值BE
}