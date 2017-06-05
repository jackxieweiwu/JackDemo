package zkrtdrone.zkrt.com.bean;

import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.ToByte;

/**
 * Created by jack_xie on 17-6-5.
 */

public class DataOne {
    @ToByte(order = 1, description = "Status1")
    private byte Status1; //值 0xFE 正常；0xFD 异常；0xFC 温度超过上限；0xFB 温度超过下限；
    @ToByte(order = 2, description = "TEMPERATURE1_1")
    private byte TEMPERATURE1One; // 温度1值为获取值除10，单位℃，
    @ToByte(order = 3, description = "TEMPERATURE1_2")
    private byte TEMPERATURE1Two; // 温度1值为获取值除10，单位℃，
    @ToByte(order = 4, description = "Status2")
    private byte Status2; //

    @ToByte(order = 5, description = "TEMPERATURE2_1")
    private byte TEMPERATURE2One;
    @ToByte(order = 6, description = "TEMPERATURE2_2")
    private byte TEMPERATURE2Two;

    //TEMPERATURE2_Low
    @ToByte(order = 7, description = "TEMPERATURE2_LowOne")
    private byte TEMPERATURE2_LowOne;
    @ToByte(order = 8, description = "TEMPERATURE2_LowTwo")
    private byte TEMPERATURE2_LowTwo;

    //TEMPERATURE2_High
    @ToByte(order = 9, description = "TEMPERATURE2_HighOne")
    private byte TEMPERATURE2_HighOne;
    @ToByte(order = 10, description = "TEMPERATURE2_HighTwo")
    private byte TEMPERATURE2_HighTwo;

    @ToByte(order = 11, description = "Gas_Status")
    private byte Gas_Status;

    @ToByte(order = 12, description = "Gas_Num1")
    private byte Gas_Num1;
    @ToByte(order = 13, description = "Gas_Value1_1")
    private byte Gas_Value1_One;
    @ToByte(order = 14, description = "Gas_Value1_2")
    private byte Gas_Value1_Two;

    @ToByte(order = 15, description = "Gas_Num2")
    private byte Gas_Num2;
    //Gas_Value2
    @ToByte(order = 16, description = "Gas_Value2_1")
    private byte Gas_Value2_One;
    @ToByte(order = 17, description = "Gas_Value2_2")
    private byte Gas_Value2_Two;

    @ToByte(order = 18, description = "Gas_Num3")
    private byte Gas_Num3;
    //Gas_Value3
    @ToByte(order = 19, description = "Gas_Value3_1")
    private byte Gas_Value3_One;
    @ToByte(order = 20, description = "Gas_Value3_2")
    private byte Gas_Value3_Two;

    @ToByte(order = 21, description = "Gas_Num4")
    private byte Gas_Num4;
    //Gas_Value4
    @ToByte(order = 22, description = "Gas_Value4_1")
    private byte Gas_Value4_One;
    @ToByte(order = 23, description = "Gas_Value4_2")
    private byte Gas_Value4_Two;

    //Device_Status
    @ToByte(order = 24, description = "Device_Status_1")
    private byte Device_Status_One;
    @ToByte(order = 25, description = "Device_Status_2")
    private byte Device_Status_Two;
    @ToByte(order = 26, description = "Device_Status_2")
    private byte Device_Status_Three;

    //Set_feedback
    @ToByte(order = 27, description = "Set_feedback_1")
    private byte Set_feedback_One;
    @ToByte(order = 28, description = "Set_feedback_2")
    private byte Set_feedback_Two;
    @ToByte(order = 29, description = "Set_feedback_2")
    private byte Set_feedback_Three;

    //Insmod_Status
    @ToByte(order = 30, description = "Insmod_Status")
    private byte Insmod_Status;
}
