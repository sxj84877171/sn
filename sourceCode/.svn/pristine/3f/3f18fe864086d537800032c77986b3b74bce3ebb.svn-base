package com.sunvote.cmd.app;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;
import com.sunvote.cmd.push.PushBaseCmd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * Created by Elvis on 2017/8/29.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description:平板模块键盘投票功能模块
 *
 * 6.1 概述
 下载多包类主要用于下载一批数据。例如项目名称表、评议规则表等。
 我们统一使用《广播式下载方式》来下载多包（即数据表），具体原理参见《表决系统通讯协议-应用文档-原理》。
 6.2 进入和退出下载状态
 通知表决器进入下载模式的好处：
 1、在重新下载未成功的表决器时候，仅通知未成功的进入下载状态，其他成功的表决器就可以不处理下载数据；
 2、能立即知道是否在线，表决器可以准备擦除FLASH，V4.52对于支持文件下载的就按文件名称创建文件；
 3、退出时，表决器能知道下载完成。

 字节	标识符	描述
 1	DOWNCMD	0x40 下载多包类指令
 2-3	KEYID	表决器编号，2字节，高位在前
 一般要指定表决器编号使指定键盘进入或退出下载状态
 但0x0000广播时候，键盘也执行
 FF01到FFFE时候，是用序列号指定键盘 V4.74
 低位字节01-FE，表示下载次序，由SDK管理，新下载变化一次，
 键盘收到的时候，记录下，同时，此次序也出现在下载数据包中，这样键盘可判断是否是我可以下载的数据，避免上次下载状态没正常退出而错误接受数据
 4	DOWNCMD	1 进入或退出下载状态
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 7	DCMD	模式 1进入下载 0退出下载
 8-24	FILENAME	在DOWNTYPE=40下载文件模式时候，是下载文件名称，16字符
 其他模式参数无意义
 SN	序列号模式时候，6字节键盘序列号，用于指定键盘
 暂不支持文件下载

 表决器回应结果状态：
 字节	标识符	描述
 1	DOWNCMD	0xC0 下载多包类指令应答
 2-3	KEYID	表决器编号，2字节，高位在前
 不是0000也不是FFFF，是要询问的表决器的编号
 FFFF 表示用SN号代表键盘
 4	DOWNCMD	1回应下载状态
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 7	DCMDACK	现在模式  1已经进入下载模式  0已经退出下载模式
 8-24	SN	使用SN号模式时候，键盘的SN，6字节
 否则参数无意义


 6.3 广播式下载数据包和查询状态
 具体原理参见《表决系统通讯协议-应用文档-原理》。
 下载数据包指令结构如下：
 字节	标识符	描述
 1	DOWNCMD	0x40 下载多包类指令
 2-3	KEYID	表决器编号，2字节，高位在前
 0xFFFF的时候是部分表决器下载，是常用模式，进入下载模式的表决器才处理数据
 0x0000时候是广播下载，所有表决器都接收和处理
 其他值是指定下载，编号和KEYID相同的才处理数据
 FF01到FFFE时候，是序列号模式 V4.74
 低位字节和进入下载模式时候的低位字节相同，用于键盘判断是否是当前下载数据
 4	DOWNCMD	2 广播式下载具体数据
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 文件下载模式时候是最高位地址 V4.52
 由于非文件下载模式PACKH加上PACKL，只能下载65536字节，文件下载模式为支持大文件，用DOWNID作为高位地址，可以下载16384K字节
 7	PACKH	数据段编号，0-255
 8	PACKL	数据片编号， 0-15
 9-24	PACKDATA	16字节的数据
 备注：
 1、	投票器先判断PACKH是否发生变化，发生变化就表示新的16片数据下载开始了，要把标志16片段下载成功状态的OKBITS全置1；
 2、	然后计算地址，把PACKDATA16字节数据写到指定位置，对于非文件下载，地址=(PACKH*16+PACKL)*16，对于文件下载，地址=（（DOWNID*256+PACKH）*16+PACKL)*16;
 3、	然后把OKBITS中对应PACKL的比特位置0表示已经对应片段下载成功，用于应答下面的下载状态询问指令

 询问下载成功状态指令结构：
 字节	标识符	描述
 1	DOWNCMD	0x40 下载多包类指令
 2-3	KEYID	表决器编号，2字节，高位在前
 使用键盘编号模式时候，不是0000也不是FFFF，是要询问的表决器的编号
 FFFF，使用SN号询问键盘
 4	DOWNCMD	3 询问广播式下载的执行状态
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 7	PACKH	数据段编号
 8-24	SN	指定键盘的SN号，6字节
 参数无意义

 表决器回应下载成功状态：
 字节	标识符	描述
 1	DOWNCMD	0xC0 下载多包类指令应答
 2-3	KEYID	表决器编号，2字节，高位在前
 不是0000也不是FFFF，是要询问的表决器的编号
 FFFF，使用SN号回答
 4	DOWNCMD	3 回应广播式下载的执行状态
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 7	PACKH	数据段编号
 8-9	OKBITS_L
 OKBITS_H	下载成功状态，共16Bit，代表0-15号数据片，Bit=0表示下载成功
 注意是低位字节在前，例如OKBITS_L=3，OKBITS_H=128时候，表示第1、2、16个数据片没下载成功，即0、1号和15号数据片不成功
 10-24	SN	指定键盘的SN号，6字节
 不是SN模式时参数无意义

 6.4 数据包类型分类
 通过DOWNTYPE和DOWNID两个字节的组合，可以决定下载数据包的类型。
 下面列出目前使用的几种类型。具体数据包的格式和含义，请参见《数据表规范-政务商务》。
 DOWNTYPE值	DOWNID值	数据包类型
 1	1	固定编号项目名称表
 1	2	随机名称编号表
 1	3	二维评测指标名称表
 2	1	评议规则表
 2	2	评议规则说明信息表
 3	1	评分规则表
 3	2	评分规则说明信息表

 10	变化	即时信息
 11	变化	短消息
 12	页号	屏幕点阵显示信息
 12	1或2	股东信息

 20	1	空闲液晶信息
 20	2	开机信息

 30	1	汉字自造字字库，16x16点阵
 31	1	多国语言资源包（未实现）

 40	高位地址	指定文件名称（可含目录）下载文件
 50		xPad的透传多包信息

 6.4.1 关于即时信息和短信的特殊说明
 即时信息定义为马上显示在屏幕上的信息，不保存，看完即丢。
 短信定义为保存的，可查看，即时显示或提醒。
 下载数据包指令结构不变，但DOWNID和PACKH含义不同：
 字节	标识符	描述
 1	DOWNCMD	0x40 下载多包类指令
 2-3	KEYID	表决器编号，2字节，高位在前
 0x0000时候是广播发信息，所有表决器都接收和处理，不需进入下载模式
 0xFFFF的时候是对多个表决器发信息，已经进入下载模式的才处理
 其他值是指定下载，编号和KEYID相同的才接收信息
 4	DOWNCMD	2 广播式下载具体数据
 5	DOWNTYPE	固定为10即时信息或11短信
 50是xPad透传多包信息，一般是字符串信息
 6	DOWNID	发一次信息就变化一次，表决器据此可知道是新的信息来了
 7	PACKH	数据片总数，0表示1，F表示最多16片，也就是所短信息最多256字符，但一般限定64字符以内
 8	PACKL	0-15，当前发送的数据片的编号
 9-24	PACKDATA	16字节的数据

 对于短信接收，表决器只要判断DOWNTYPE为10或11，然后DOWNID和以前的不同，就知道是新短信过来，并根据PACKH知道信息的长度，建立新的下载状态位。
 然后根据PACKH判断所有片段是否都接收完毕，可以自动显示。
 对于广播给所有表决器的信息，表决器不需单个单个进入下载模式，由SDK控制各片段信息多广播几次，表决器自己判断接收完整就显示。
 对应指定单个表决器发信息，为提高速度，不对表决器执行进入下载模式，但要查询下载状态，确保信息完整下载，并且不执行退出下载模式。
 对应指定多个表决器发信息，就必须进入下载模式，然后下载，然后查询，为提高下载速度，对表决器不执行退出下载模式。
 所以，因为不执行退出下载状态，要注意的是，表决器要对短信模式下的进入下载状态要单独处理，避免和指定多个的表决器下载项目名称表等其他操作搞混。
 6.4.2 桌牌点阵显示信息数据格式
 桌牌点阵显示信息一般是按规则排布的点阵亮灭二进制字节数据。例如96x32点阵的显示屏，实际的数据是96x32/8=384字节。为保证以后点阵屏幕变更，在实际点阵数据前加16字节的格式说明。

 字节	标识符	描述
 1	TYPE	点阵格式
 1	96x32点阵，（排列方式？）
 2-16		根据点阵格式有不同含义，暂时为空
 17-…	BITMAP	显示点阵数据，根据不同格式有不同的长度

 这样，如果格式为1，实际要传输的数据是16+384=400字节。
 6.4.3 股东信息
 特定型号需要显示股东姓名等信息。
 请参考6.4.1节短信，下载方法一样，就是国外应用需要考虑unicode编码，所以DOWNID来标志。
 字节	标识符	描述
 1	DOWNCMD	0x40 下载多包类指令
 2-3	KEYID	表决器编号，2字节，高位在前
 0x0000时候是广播发信息，所有表决器都接收和处理，不需进入下载模式
 0xFFFF的时候是对多个表决器发信息，已经进入下载模式的才处理
 其他值是指定下载，编号和KEYID相同的才接收信息
 FF01到FFFE时候，是用序列号指定键盘 V4.77
 （采用SN号模式下载股东信息时候，键盘先进入下载状态，参见6.6节序列号下载多包方法，和6.2节进入和退出下载状态）
 4	DOWNCMD	2 广播式下载具体数据
 5	DOWNTYPE	12 股东信息
 6	DOWNID	0 GB2312编码
 1 UTF8编码
 2 Unicode编码
 7	PACKH	数据片总数，0表示1，F表示最多16片，也就是所短信息最多256字符，但一般限定64字符以内
 8	PACKL	0-15，当前发送的数据片的编号
 9-24	PACKDATA	16字节的数据

 6.5 简单下载模式（不使用）
 简单下载方式只是作为解决方案提出，因为效率比广播式低，没有采用。
 下载数据包指令结构如下：
 字节	标识符	描述
 1	DOWNCMD	0x40 下载多包类指令
 2-3	KEYID	表决器编号，2字节，高位在前
 必须指定一个具体的表决器编号
 4	DOWNCMD	4 简单下载模式
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 7	PACKH	数据段编号
 8	PACKL	0-7，数据片编号
 9-24	PACKDATA	16字节的数据

 表决器回应下载成功状态：
 字节	标识符	描述
 1	DOWNCMD	0xB0 下载多包类指令应答
 2-3	KEYID	表决器编号，2字节，高位在前
 不是0000也不是FFFF，是要询问的表决器的编号
 4	DOWNCMD	4 回应下载状态
 5	DOWNTYPE	多包类型
 6	DOWNID	数据包标识码
 7	PACKH	数据段编号
 8	PACKL	0-7，数据片编号
 9	STATUS	1 成功
 0 不成功
 10-24		参数无意义

 6.6 序列号下载多包
 键盘使用序列号模式时，也分广播、指定多个、指定单个键盘下载。
 指定多个，修改了6.2节指定键盘进入下载状态和退出下载状态，可用SN号指定，同样，6.3节的询问下载结果，修改为同样支持SN号。而下载数据，仍然沿用原来的ID=FFFF，在下载模式的才接受。
 指定单个，包含到指定多个里面，这样，即使单个发短信，也要先进入下载模式。
 广播，则沿用ID=0000的原来模式。
 然后，发现一个问题，如果上次键盘没能正常退出下载模式，就有可能错误接受数据，包括原来使用键盘编号的，都有这个可能。所以，进入和退出下载模式，广播数据时，原来的FFFF改为FF01到FFFE，低字节01-FE表示次序，SDK管理，下载一次改变一次，这样键盘就能判断数据是不是这次的，同时如果不是，键盘改自己次序为0。

 *
 */
public class MutiPkgDownCmd extends BaseCmd {


    /***
     * 下载数据包指令结构如下：
     字节	标识符	描述
     1	DOWNCMD	0x40 下载多包类指令
     2-3	KEYID	表决器编号，2字节，高位在前
     0xFFFF的时候是部分表决器下载，是常用模式，进入下载模式的表决器才处理数据
     0x0000时候是广播下载，所有表决器都接收和处理
     其他值是指定下载，编号和KEYID相同的才处理数据
     FF01到FFFE时候，是序列号模式 V4.74
     低位字节和进入下载模式时候的低位字节相同，用于键盘判断是否是当前下载数据
     4	DOWNCMD	2 广播式下载具体数据
     5	DOWNTYPE	多包类型
     6	DOWNID	数据包标识码
     文件下载模式时候是最高位地址 V4.52
     由于非文件下载模式PACKH加上PACKL，只能下载65536字节，文件下载模式为支持大文件，用DOWNID作为高位地址，可以下载16384K字节
     7	PACKH	数据段编号，0-255
     8	PACKL	数据片编号， 0-15
     9-24	PACKDATA	16字节的数据

     * @return
     */

    public static  final  byte DOWNCMD = 0x40 ;

    private byte cmd = DOWNCMD;

    private byte[] keyId = new byte[2];

    private byte downType ;

    private byte downid ;

    private byte packh;

    private byte packl;

    private byte[] packdata = new byte[16];

    /**
     * 1 进入或退出下载状态
     * 2 广播式下载具体数据
     * 3 询问广播式下载的执行状态
     */
    private byte cmd1 ;

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            outputStream.write(getCmd());
            outputStream.write(getKeyId());
            outputStream.write(getCmd1());
            outputStream.write(getDownType());
            outputStream.write(getDownid());
            outputStream.write(getPackh());
            outputStream.write(getPackl());
            outputStream.write(getPackdata());
            return outputStream.toByteArray();
        }catch (Exception e){}
        return new byte[0];
    }

    @Override
    public ICmd parseCmd(byte[] source, int start) {
        if(source.length > start){
            setCmd(source[start]);
        }
        if(source.length > start + 2){
            setKeyId(Arrays.copyOfRange(source,start+2,start+4));
        }
        if(source.length > start + 4){
            setCmd1(source[start+4]);
        }
        if(source.length > start + 5){
            setDownType(source[start+5]);
        }
        if(source.length > start + 6){
           setDownid(source[start+6]);
        }
        if(source.length > start + 7){
            setPackh(source[start+7]);
        }
        if(source.length > start + 8){
            setPackl(source[start+8]);
        }
        if(source.length > start + 24){
            setPackdata(Arrays.copyOfRange(source,start+9,start+24));
        }
        return this;
    }

    public static MutiPkgDownCmd parseRequest(byte[] bytes, int length){
        MutiPkgDownCmd mutiPkgDownCmd = new MutiPkgDownCmd();
        mutiPkgDownCmd.parseCmd(bytes);
        return mutiPkgDownCmd;
    }

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public byte[] getKeyId() {
        return keyId;
    }

    public void setKeyId(byte[] keyId) {
        this.keyId = keyId;
    }

    public byte getDownType() {
        return downType;
    }

    public void setDownType(byte downType) {
        this.downType = downType;
    }

    public byte getDownid() {
        return downid;
    }

    public void setDownid(byte downid) {
        this.downid = downid;
    }

    public byte getPackh() {
        return packh;
    }

    public void setPackh(byte packh) {
        this.packh = packh;
    }

    public byte getPackl() {
        return packl;
    }

    public void setPackl(byte packl) {
        this.packl = packl;
    }

    public byte[] getPackdata() {
        return packdata;
    }

    public void setPackdata(byte[] packdata) {
        this.packdata = packdata;
    }

    public byte getCmd1() {
        return cmd1;
    }

    public void setCmd1(byte cmd1) {
        this.cmd1 = cmd1;
    }
}
