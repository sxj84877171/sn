package com.sunvote.sunvotesdk.basestation;

/**
 * Created by Elvis on 2017/9/16.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class BaseStationInfo {

    /**
     * “Mode1， Mode2， Mode3， Mode4，
     Mode5， Mode6”
     Mode1：选项显示方式，只对带液
     晶的表决器有效
     “1” 显示字母ABCD……
     “2” 显示数字1234……
     Mode2：
     “0” 提交后不能修改
     “1” 提交后可以修改
     Mode3：保密模式，具体和产品型号有关
     “0” 不保密
     “1” 显示保密
     Mode4：迫选模式
     “0” 可以缺选
     “1’ 迫选，必须选出Mode6规
     定的项目数
     Mode5：最大可供选择项目数，最
     大值为 10
     Mode6：可选出数目，最小值是
     1，最大值不超出Mode5
     */
    public static final String VOTETYPE_CHOICE_DEFAULT_SETTING = "1,1,0,0,10,10" ;

    public static final String VOTETYPE_CHOICE_DEFAULT_SETTING_BEGIN = "1,1,0,0," ;

    /**
     * 连接类型
     */
    private int connectType = 4;
    /**
     * 基站ID
     */
    private int baseId = 0;
    /**
     * 连接信息
     */
    private String connectInfo = "/dev/ttyMT1";

    /**
     * 基站名称
     */
    private String baseStationName ;

    /**
     * 是否连接
     */
    private boolean isConnected = false;

    /**
     * 是否授权
     */
    private boolean islicenseSuccess = false;


    /**
     * 是否在测试模式
     */
    private boolean isExaminationRunning = false;

    /**
     * 硬件序列号
     */
    private String basestationSerial = "" ;

    /**
     * 硬件和固件版本
     */
    private String basestationVersion = "" ;

    /**
     *配对码
     */
    private String baseStationMatchCode;

    /**
     *基站IP地址
     */
    private String baseStationIP;

    /**
     * MAC地址
     */
    private String baseStationMAC;

    /**
     * 子网掩码
     */
    private String baseStationSubnetMask;

    /**
     * 网关 标识基站所在子网的网关
     */
    private String baseStationGateway;

    /**
     * 工作模式， SSID蓝牙设备名称     AP：为无线接入点模式，需要设置自身的SSID，控制端PC或平板通过SSID识别并连接基站。
     STA：为站模式，需要设置接入点的SSID, 控制端PC或平板通过内部协议识别并连接基站
     BLE： 为蓝牙模式，平板用蓝牙连接基站，平板继续WiFi 上网， 第二个部分为蓝牙设备名称
     */
    private String wifiSSID;

    /**
     * 基站主信道
     */
    private String baseStationChannel;

    /**
     * 字符串 “密码”
     */
    private String wiFiPassword;

    /**
     * 基站功率
     * “0” 按缺省值
     “1” 满功率
     “2” 中功率
     “3” 小功率
     */
    private String baseStationRFPower;

    /**
     * 通讯通道设置格式：“是否中继，通道数，通道 1，通道2，通道3，通道4”    默认为以主信道为基础，依次加7的信道，可以单独修改
     */
    private String baseStationCommunicationChannels;

    /**
     * 键盘连接模式固定配对：成套使用时；自由配对：键盘可以由用户选择连接不同基站；免配对：不改变配对码，临时连接另一基站
     * “1” 固定配对
     “2” 自由模式
     “3” 免配对模式
     */
    private String keyPadWorkingMode;

    /**
     * 键盘识别模式ID模式：配套使用时在套内用ID标识，可能出现重复但方便记忆。SN号模式：全球唯一标识，不会重复但不容易记忆。
     */
    private String keyPadIdentificationMode ;

    /**
     * 状态报告模式：键盘报告自身状态的时机自动关机模式：键盘空闲关机的时间长度自动提交模式：只有一个选项是是否需要自动提交
     */
    private String keyPadConfig;

    /**
     *验证密码  软件狗A区密码验证，验证通过后才可以读写A区
     */
    private String softwareDongleVerifyPWD;

    /**
     * 软件狗A区密码内部使用
     */
    private String softwareDongleAPWD;

    /**
     * 软件狗A区验证密码成功后可以读写，否则显示“Locked”
     */
    private String softwareDongleAZone;

    /**
     * 软件狗B区开放的读写区域
     */
    private String softwareDongleBZone;

    /**
     * 键盘编号
     */
    private String keyPadID;

    /**
     * 用户编号
     */
    private String keyPadUserID;

    /**
     * 键盘出厂序列号
     */
    private String keyPadSN;

    /**
     * 键盘硬件和固件版本信息内部使用
     */
    private String keyPadVersion;

    /**
     * 遥控键盘关机  用于大批量测试后批量关机
     */
    private String keyPadPowerOff;

    /**
     * 对指定的键盘授权
     */
    private String keypadAuthorizeByID;

    /**
     * 收到此事件时可以提交由客户确认是否需要更换频点。
     */
    private String baseStationChannelInterference;

    /**
     * 基站自动更换频点
     */
    private String baseStationAutoChangeChannel;

    /**
     * 基站型号编码具体型号请参考
     */
    private String baseStationModel;

    /**
     * 键盘型号编码具体型号请参考
     */
    private String keyPadModel;

    /**
     * 测试模式
     */
    public int mode ;

    /**
     * 是否开启了后台签到
     */
    private boolean isBackgroundSign = false ;

    public boolean isBackgroundSign() {
        return isBackgroundSign;
    }

    public void setBackgroundSign(boolean backgroundSign) {
        isBackgroundSign = backgroundSign;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public int getConnectType() {
        return connectType;
    }

    public void setConnectType(int connectType) {
        this.connectType = connectType;
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public String getConnectInfo() {
        return connectInfo;
    }

    public void setConnectInfo(String connectInfo) {
        this.connectInfo = connectInfo;
    }

    public String getBaseStationName() {
        return baseStationName;
    }

    public void setBaseStationName(String baseStationName) {
        this.baseStationName = baseStationName;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean islicenseSuccess() {
        return islicenseSuccess;
    }

    public void setIslicenseSuccess(boolean islicenseSuccess) {
        this.islicenseSuccess = islicenseSuccess;
    }

    public boolean isExaminationRunning() {
        return isExaminationRunning;
    }

    public void setExaminationRunning(boolean examinationRunning) {
        isExaminationRunning = examinationRunning;
    }

    public void setBasestationSerial(String basestationSerial) {
        this.basestationSerial = basestationSerial;
    }

    public String getBasestationSerial() {
        return basestationSerial;
    }

    public String getBasestationVersion() {
        return basestationVersion;
    }

    public void setBasestationVersion(String basestationVersion) {
        this.basestationVersion = basestationVersion;
    }

    public String getBaseStationMatchCode() {
        return baseStationMatchCode;
    }

    public void setBaseStationMatchCode(String baseStationMatchCode) {
        this.baseStationMatchCode = baseStationMatchCode;
    }

    public String getBaseStationIP() {
        return baseStationIP;
    }

    public void setBaseStationIP(String baseStationIP) {
        this.baseStationIP = baseStationIP;
    }

    public void setBaseStationMAC(String baseStationMAC) {
        this.baseStationMAC = baseStationMAC;
    }

    public String getBaseStationMAC() {
        return baseStationMAC;
    }

    public String getBaseStationSubnetMask() {
        return baseStationSubnetMask;
    }

    public void setBaseStationSubnetMask(String baseStationSubnetMask) {
        this.baseStationSubnetMask = baseStationSubnetMask;
    }

    public String getBaseStationGateway() {
        return baseStationGateway;
    }

    public void setBaseStationGateway(String baseStationGateway) {
        this.baseStationGateway = baseStationGateway;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }

    public void setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
    }

    public void setWiFiPassword(String wiFiPassword) {
        this.wiFiPassword = wiFiPassword;
    }

    public String getWiFiPassword() {
        return wiFiPassword;
    }

    public String getBaseStationChannel() {
        return baseStationChannel;
    }

    public void setBaseStationChannel(String baseStationChannel) {
        this.baseStationChannel = baseStationChannel;
    }

    public String getBaseStationRFPower() {
        return baseStationRFPower;
    }

    public void setBaseStationRFPower(String baseStationRFPower) {
        this.baseStationRFPower = baseStationRFPower;
    }

    public void setBaseStationCommunicationChannels(String baseStationCommunicationChannels) {
        this.baseStationCommunicationChannels = baseStationCommunicationChannels;
    }

    public String getBaseStationCommunicationChannels() {
        return baseStationCommunicationChannels;
    }

    public void setKeyPadWorkingMode(String keyPadWorkingMode) {
        this.keyPadWorkingMode = keyPadWorkingMode;
    }

    public String getKeyPadWorkingMode() {
        return keyPadWorkingMode;
    }

    public void setKeyPadIdentificationMode(String keyPadIdentificationMode) {
        this.keyPadIdentificationMode = keyPadIdentificationMode;
    }

    public String getKeyPadIdentificationMode() {
        return keyPadIdentificationMode;
    }

    public String getKeyPadConfig() {
        return keyPadConfig;
    }

    public void setKeyPadConfig(String keyPadConfig) {
        this.keyPadConfig = keyPadConfig;
    }

    public String getSoftwareDongleVerifyPWD() {
        return softwareDongleVerifyPWD;
    }

    public void setSoftwareDongleVerifyPWD(String softwareDongleVerifyPWD) {
        this.softwareDongleVerifyPWD = softwareDongleVerifyPWD;
    }

    public String getSoftwareDongleAPWD() {
        return softwareDongleAPWD;
    }

    public void setSoftwareDongleAPWD(String softwareDongleAPWD) {
        this.softwareDongleAPWD = softwareDongleAPWD;
    }

    public String getSoftwareDongleAZone() {
        return softwareDongleAZone;
    }

    public void setSoftwareDongleAZone(String softwareDongleAZone) {
        this.softwareDongleAZone = softwareDongleAZone;
    }

    public void setSoftwareDongleBZone(String softwareDongleBZone) {
        this.softwareDongleBZone = softwareDongleBZone;
    }

    public String getSoftwareDongleBZone() {
        return softwareDongleBZone;
    }

    public String getKeyPadID() {
        return keyPadID;
    }

    public void setKeyPadID(String keyPadID) {
        this.keyPadID = keyPadID;
    }

    public String getKeyPadUserID() {
        return keyPadUserID;
    }

    public void setKeyPadUserID(String keyPadUserID) {
        this.keyPadUserID = keyPadUserID;
    }

    public void setKeyPadSN(String keyPadSN) {
        this.keyPadSN = keyPadSN;
    }

    public String getKeyPadSN() {
        return keyPadSN;
    }

    public String getKeyPadVersion() {
        return keyPadVersion;
    }

    public void setKeyPadVersion(String keyPadVersion) {
        this.keyPadVersion = keyPadVersion;
    }

    public String getKeyPadPowerOff() {
        return keyPadPowerOff;
    }

    public void setKeyPadPowerOff(String keyPadPowerOff) {
        this.keyPadPowerOff = keyPadPowerOff;
    }

    public String getKeypadAuthorizeByID() {
        return keypadAuthorizeByID;
    }

    public void setKeypadAuthorizeByID(String keypadAuthorizeByID) {
        this.keypadAuthorizeByID = keypadAuthorizeByID;
    }

    public void setBaseStationChannelInterference(String baseStationChannelInterference) {
        this.baseStationChannelInterference = baseStationChannelInterference;
    }

    public String getBaseStationChannelInterference() {
        return baseStationChannelInterference;
    }

    public String getBaseStationAutoChangeChannel() {
        return baseStationAutoChangeChannel;
    }

    public void setBaseStationAutoChangeChannel(String baseStationAutoChangeChannel) {
        this.baseStationAutoChangeChannel = baseStationAutoChangeChannel;
    }

    public void setBaseStationModel(String baseStationModel) {
        this.baseStationModel = baseStationModel;
    }

    public String getBaseStationModel() {
        return baseStationModel;
    }

    public void setKeyPadModel(String keyPadModel) {
        this.keyPadModel = keyPadModel;
    }

    public String getKeyPadModel() {
        return keyPadModel;
    }
}
