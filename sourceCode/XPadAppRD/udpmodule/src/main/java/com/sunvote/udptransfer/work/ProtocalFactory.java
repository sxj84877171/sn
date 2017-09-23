package com.sunvote.udptransfer.work;

import com.sunvote.cmd.BaseCmd;
import com.sunvote.cmd.ICmd;
import com.sunvote.cmd.app.BaseBeaconStateRequest;
import com.sunvote.cmd.app.ModuleHeartBeatCmd;
import com.sunvote.cmd.app.MutiPkgDownCmd;
import com.sunvote.cmd.push.BaseStatusChangeRequest;
import com.sunvote.cmd.push.BaseStatusChangeResponse;
import com.sunvote.cmd.push.DownloadSingletonPkg;
import com.sunvote.cmd.push.ReadAndWriteHardwareInformation;
import com.sunvote.cmd.push.ReadAndWriteVoterAllocation;
import com.sunvote.cmd.push.VoteStatusChangeRequest;
import com.sunvote.cmd.push.VoteStatusChangeResponse;
import com.sunvote.cmd.state.GetPkgStateRequest;
import com.sunvote.cmd.state.GetPkgStateResponse;
import com.sunvote.cmd.state.KeyboardParameterStateRequest;
import com.sunvote.cmd.state.KeyboardParameterStateResponse;
import com.sunvote.cmd.state.ModeOperationStateRequest;
import com.sunvote.cmd.state.ModeOperationStateResponse;
import com.sunvote.cmd.state.QueryBeaconStateRequest;
import com.sunvote.cmd.state.QueryBeaconStateResponse;
import com.sunvote.cmd.state.QueryOnlineStateRequest;
import com.sunvote.cmd.state.QueryOnlineStateResponse;
import com.sunvote.cmd.state.WorkPattenStateRequest;
import com.sunvote.cmd.state.WorkPattenStateResponse;
import com.sunvote.cmd.upgrade.CleanFlashStateRequest;
import com.sunvote.cmd.upgrade.CleanFlashStateResponse;
import com.sunvote.cmd.upgrade.UpgradEntryStateRequest;
import com.sunvote.cmd.upgrade.UpgradEntryStateResponse;
import com.sunvote.cmd.upgrade.UpgradeExitStateRequest;
import com.sunvote.cmd.upgrade.UpgradeExitStateResponse;
import com.sunvote.cmd.upgrade.WriteFlashStateRequest;
import com.sunvote.cmd.upgrade.WriteFlashStateResponse;
import com.sunvote.cmd.upload.NumberingModeResult;
import com.sunvote.cmd.upload.SequenceFormatResult;
import com.sunvote.cmd.upload.SingerUploadPkg;
import com.sunvote.cmd.upload.TransferResult;
import com.sunvote.protocal.Protocol;
import com.sunvote.udptransfer.UDPModule;
import com.sunvote.util.LogUtil;

import java.io.ByteArrayOutputStream;

/**
 * Created by Elvis on 2017/8/14.
 * Email:Eluis@psunsky.com
 * Description:
 * <p>
 * 业务类
 * 所有的业务在此类完成
 * <p>
 * 此类完成所有数据包的业务逻辑处理
 * 每个数据包对应的处理方法
 */

public class ProtocalFactory {

    public final static long DELAY = 5 * 1000;

    /**
     * 消息总入口，进入业务操作开始
     * @param bytes 数据
     * @param length 长度
     * @param sourceType App还是基站
     * @return 任务包
     */
    public static WorkThread.MessageBean execute(byte[] bytes, int length, int sourceType) {
        WorkThread.MessageBean bean = new WorkThread.MessageBean();
        bean.datas = bytes;
        bean.sourceType = sourceType;
        boolean isHeaderRight = Protocol.checkHeader(bytes);
        boolean crcRight = true;//Protocol.checkCRC(bytes);//
        if (isHeaderRight && crcRight) {
            ICmd cmd = BaseCmd.parse(bytes, length);
            Protocol<ICmd> protocal = new Protocol<ICmd>();
            protocal.setCmd(cmd);
            bean.protocal = protocal;
            bean.executeMethod = getExecuteMethod(cmd);
        } else {
            LogUtil.e(UDPModule.TAG, "the data is not right. isHeader right ?" + isHeaderRight + ",is CRC right ?" + crcRight);
        }
        return bean;
    }

    /**
     * 各种消息命令处理总调节器，负责分发各种数据包的处理
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(ICmd cmd) {
        //基础信标变化请求
        if (cmd instanceof BaseStatusChangeRequest) {
            return getExecuteMethod((BaseStatusChangeRequest) cmd);
        }

        // 基础信标变化回复
        if (cmd instanceof BaseStatusChangeResponse) {
            return getExecuteMethod((BaseStatusChangeResponse) cmd);
        }

        //表决器管理类指令
        if (cmd instanceof DownloadSingletonPkg) {
            return getExecuteMethod((DownloadSingletonPkg) cmd);
        }

        // 投票信标变化请求
        if (cmd instanceof VoteStatusChangeRequest) {
            return getExecuteMethod((VoteStatusChangeRequest) cmd);
        }

        // 投票信标变化返回
        if (cmd instanceof VoteStatusChangeResponse) {
            return getExecuteMethod((VoteStatusChangeResponse) cmd);
        }

        // 查询和设置键盘参数请求
        if (cmd instanceof KeyboardParameterStateRequest) {
            return getExecuteMethod((KeyboardParameterStateRequest) cmd);
        }

        //查询和设置键盘参数返回
        if (cmd instanceof KeyboardParameterStateResponse) {
            return getExecuteMethod((KeyboardParameterStateResponse) cmd);
        }

        // 执行配对、配置模式操作请求
        if (cmd instanceof ModeOperationStateRequest) {
            return getExecuteMethod((ModeOperationStateRequest) cmd);
        }

        // 执行配对、配置模式操作返回
        if (cmd instanceof ModeOperationStateResponse) {
            return getExecuteMethod((ModeOperationStateResponse) cmd);
        }

        // 查询在线状态、基站识别模式、频点、信号强度、刚才发送过、基站名称，同时告诉模块电池电压（用于键盘报告状态）
        if (cmd instanceof QueryOnlineStateRequest) {
            return getExecuteMethod((QueryOnlineStateRequest) cmd);
        }

        //查询在线状态、基站识别模式、频点、信号强度、刚才发送过、基站名称，同时告诉模块电池电压（用于键盘报告状态）
        if (cmd instanceof QueryOnlineStateResponse) {
            return getExecuteMethod((QueryOnlineStateResponse) cmd);
        }

        // 查询和设置模块工作模式
        if (cmd instanceof WorkPattenStateRequest) {
            return getExecuteMethod((WorkPattenStateRequest) cmd);
        }

        // 查询和设置模块工作模式
        if (cmd instanceof WorkPattenStateResponse) {
            return getExecuteMethod((WorkPattenStateResponse) cmd);
        }

        //擦除FLASH
        if (cmd instanceof CleanFlashStateRequest) {
            return getExecuteMethod((CleanFlashStateRequest) cmd);
        }

        //擦除FLASH
        if (cmd instanceof CleanFlashStateResponse) {
            return getExecuteMethod((CleanFlashStateResponse) cmd);
        }

        //固件升级指令
        if (cmd instanceof UpgradeExitStateRequest) {
            return getExecuteMethod((UpgradeExitStateRequest) cmd);
        }

        //固件升级指令
        if (cmd instanceof UpgradeExitStateResponse) {
            return getExecuteMethod((UpgradeExitStateResponse) cmd);
        }

        // 进入升级模式
        if (cmd instanceof UpgradEntryStateRequest) {
            return getExecuteMethod((UpgradEntryStateRequest) cmd);
        }

        //进入升级模式
        if (cmd instanceof UpgradEntryStateResponse) {
            return getExecuteMethod((UpgradEntryStateResponse) cmd);
        }

        //按页写FLASH
        if (cmd instanceof WriteFlashStateRequest) {
            return getExecuteMethod((WriteFlashStateRequest) cmd);
        }

        //按页写FLASH
        if (cmd instanceof WriteFlashStateResponse) {
            return getExecuteMethod((WriteFlashStateResponse) cmd);
        }

        //编号模式结果
        if (cmd instanceof NumberingModeResult) {
            return getExecuteMethod((NumberingModeResult) cmd);
        }

        //0x73使用硬件序列号提交单包结果
        if (cmd instanceof SequenceFormatResult) {
            return getExecuteMethod((SequenceFormatResult) cmd);
        }

        //编号模式结果 返回
        if (cmd instanceof TransferResult) {
            return getExecuteMethod((TransferResult) cmd);
        }

        //模块在信标变化时候自动报告
        if (cmd instanceof QueryBeaconStateRequest) {
            return getExecuteMethod((QueryBeaconStateRequest) cmd);
        }

        // 取包信标概述
        if (cmd instanceof GetPkgStateRequest) {
            return getExecuteMethod((GetPkgStateRequest) cmd);
        }

        // 基础信标是基站定时发送到一个信标信号
        if (cmd instanceof BaseBeaconStateRequest) {
            return getExecuteMethod((BaseBeaconStateRequest) cmd);
        }

        if (cmd instanceof GetPkgStateResponse) {
            return getExecuteMethod((GetPkgStateResponse) cmd);
        }

        return null;
    }

    /**
     * 单信标恢复变化
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final GetPkgStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                if (cmd.containsKeyId(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId())) {
                    SequenceFormatResult sequenceFormatResult = new SequenceFormatResult();
                    NumberingModeResult numberingModeResult = BaseStationProcessWork.getInstance().getNumberingModeResult();
                    if (numberingModeResult != null) {
                        sequenceFormatResult.setMsgType(numberingModeResult.getMsgType());
                        sequenceFormatResult.setMsgId(numberingModeResult.getMsgId());
                        sequenceFormatResult.setAnsType(numberingModeResult.getAnsType());
                        sequenceFormatResult.setAnsData(numberingModeResult.getAnsData());
                        Protocol<SequenceFormatResult> protocol = new Protocol<>();
                        protocol.setCmd(sequenceFormatResult);
                        SDKProcessWork.getInstance().sendProtocol(protocol);
                        BaseStationProcessWork.getInstance().setNumberingModeResult(null);
                    }
                    RepeatMessageManager.getInstance().remove("SingerUploadPkg");
                }

            }
        };
    }

    /**
     * 信标变化的处理方式
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final BaseBeaconStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                if (compareNotEquals(cmd)) {
                    saveInfo(cmd);
                    LogUtil.i(UDPModule.TAG, "notifyBaseToSDK,BaseBeaconStateRequest");
                    notifyBaseToSDK();
                }

            }
        };
    }

    /**
     * 保存基础信标信息
     * @param cmd
     */
    private static void saveInfo(BaseBeaconStateRequest cmd) {
        BaseStationProcessWork.getInstance().getBaseStationInfo().setNowT(cmd.getNowt());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setAuthcode(cmd.getAuthcode());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setBaddh(cmd.getBaddh());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setConfid(cmd.getConfid());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setLogin(cmd.getLogin());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setAttrib(cmd.getAttrib());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setInfoid(cmd.getInfoid());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setMoreinfo(cmd.getMoreinfo());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setOfftime(cmd.getOfftime());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setReportLanguage(cmd.getReportLanguage());
    }

    /**
     * 比较基础信标是否发生变化
     * @param cmd
     * @return
     */
    private static boolean compareNotEquals(BaseBeaconStateRequest cmd) {

        if (!compareBytes(cmd.getNowt(), BaseStationProcessWork.getInstance().getBaseStationInfo().getNowT())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getNowt", cmd.getNowt());
            return true;
        }
        if (!compareBytes(cmd.getAuthcode(), BaseStationProcessWork.getInstance().getBaseStationInfo().getAuthcode())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getAuthcode", cmd.getAuthcode());
            return true;
        }
        if (!compareBytes(cmd.getBaddh(), BaseStationProcessWork.getInstance().getBaseStationInfo().getBaddh())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getBaddh" + cmd.getBaddh());
            return true;
        }
        if (!compareBytes(cmd.getConfid(), BaseStationProcessWork.getInstance().getBaseStationInfo().getConfid())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getConfid" + cmd.getConfid());
            return true;
        }
        if (!compareBytes(cmd.getLogin(), BaseStationProcessWork.getInstance().getBaseStationInfo().getLogin())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getLogin" + cmd.getLogin());
            return true;
        }
        if (!compareBytes(cmd.getAttrib(), BaseStationProcessWork.getInstance().getBaseStationInfo().getAttrib())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getAttrib" + cmd.getAttrib());
            return true;
        }
        if (!compareBytes(cmd.getInfoid(), BaseStationProcessWork.getInstance().getBaseStationInfo().getInfoid())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getInfoid" + cmd.getInfoid());
            return true;
        }
        if (!compareBytes(cmd.getMoreinfo(), BaseStationProcessWork.getInstance().getBaseStationInfo().getMoreinfo())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getMoreinfo" + cmd.getMoreinfo());
            return true;
        }
//        if (!compareBytes(cmd.getOfftime(), BaseStationProcessWork.getInstance().getBaseStationInfo().getOfftime())) {
//            LogUtil.i(UDPModule.TAG,"BaseBeaconStateRequest-getOfftime" + cmd.getOfftime());
//            return true;
//        }
        if (!compareBytes(cmd.getReportLanguage(), BaseStationProcessWork.getInstance().getBaseStationInfo().getReportLanguage())) {
            LogUtil.i(UDPModule.TAG, "BaseBeaconStateRequest-getReportLanguage" + cmd.getReportLanguage());
            return true;
        }

        return false;
    }

    /**
     * 两个字节是否相等
     * @param byte1
     * @param byte2
     * @return
     */
    private static boolean compareBytes(byte byte1, byte byte2) {
        return byte1 == byte2;
    }

    /**
     * 比较两个字节数组是否相等
     * @param byte1
     * @param byte2
     * @return
     */
    private static boolean compareBytes(byte[] byte1, byte[] byte2) {
        if (byte1 != null && byte2 != null) {
            if (byte1.length != byte2.length) {
                return false;
            }

            for (int i = 0; i < byte1.length; i++) {
                if (byte1[i] != byte2[i]) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }


    /**
     * 信标变化的处理方式
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final GetPkgStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {

                if (BaseStationProcessWork.getInstance().getBaseStationInfo().isBaseNofify()) {
                    // 信标是否有变化，没有变化，则跳过处理
                    if (BaseStationProcessWork.getInstance().needUpdateState(cmd)) {

                        LogUtil.i(UDPModule.TAG, "notifyVoteToSDK,GetPkgStateRequest");
                        // 如果发现信标有变化，先保存新的信标
                        BaseStationProcessWork.getInstance().setOldPkgState(cmd);
                        // 保存信息
                        BaseStationProcessWork.getInstance().getBaseStationInfo().setSunvoteMode(cmd.getMode());
                        BaseStationProcessWork.getInstance().getBaseStationInfo().setNowT(cmd.getNowT());
                        BaseStationProcessWork.getInstance().getBaseStationInfo().setDataPos(cmd.getDataPos());
                        BaseStationProcessWork.getInstance().getBaseStationInfo().setModes(cmd.getModes());
                        notifyVoteToSDK();

                        if (cmd.getMode() == 0x09) {
                            sendModuleHeartBeat();
                        }
                    }
                }
            }
        };
    }

    /**
     * 通知APP 投票信标变化
     */
    private static void notifyVoteToSDK() {
        VoteStatusChangeRequest voteStatusChangeRequest = getVoteStatusChangeRequest();
        Protocol<VoteStatusChangeRequest> protocol = new Protocol<>();
        protocol.setCmd(voteStatusChangeRequest);
        SDKProcessWork.getInstance().sendProtocol(protocol);

//        RepeatMessageManager.getInstance().put("VoteStatusChange",protocol,RepeatMessageManager.SDK);

        LogUtil.i(UDPModule.TAG, "notifyVoteToSDK", protocol.toBytes());
    }

    /**
     * 投票信标组包
     * @return
     */
    private static VoteStatusChangeRequest getVoteStatusChangeRequest() {
        VoteStatusChangeRequest voteStatusChangeRequest = new VoteStatusChangeRequest();
        voteStatusChangeRequest.setNowt(BaseStationProcessWork.getInstance().getBaseStationInfo().getNowT());
        voteStatusChangeRequest.setBaddh(BaseStationProcessWork.getInstance().getBaseStationInfo().getBaddh());
        voteStatusChangeRequest.setDatapos(BaseStationProcessWork.getInstance().getBaseStationInfo().getDataPos());
        if (BaseStationProcessWork.getInstance().getOldPkgState() != null) {
            voteStatusChangeRequest.setMode(BaseStationProcessWork.getInstance().getOldPkgState().getMode());
        }
        voteStatusChangeRequest.setModes(BaseStationProcessWork.getInstance().getBaseStationInfo().getModes());
        return voteStatusChangeRequest;
    }

    /**
     * 通知基础信标变化
     */
    private static void notifyBaseToSDK() {
        BaseStatusChangeRequest baseStatusChangeRequest = getBaseStatusChangeRequest();

        // 需要发送到SDK
        Protocol<BaseStatusChangeRequest> protocol = new Protocol<>();
        protocol.setCmd(baseStatusChangeRequest);
        SDKProcessWork.getInstance().sendProtocol(protocol);
//        RepeatMessageManager.getInstance().put("BaseStatusChange",protocol,RepeatMessageManager.SDK);
        BaseStationProcessWork.getInstance().getBaseStationInfo().setBaseNofify(true);
        LogUtil.i(UDPModule.TAG, "notifyBaseToSDK", protocol.toBytes());
    }

    /**
     * 构造通知APP信标变化的数据包
     * @return 基础信标
     */
    private static BaseStatusChangeRequest getBaseStatusChangeRequest() {
        // 构造通知APP信标变化的数据包
        BaseStatusChangeRequest baseStatusChangeRequest = new BaseStatusChangeRequest();
        baseStatusChangeRequest.setBaddh(BaseStationProcessWork.getInstance().getBaseStationInfo().getBaddh());
        baseStatusChangeRequest.setBasename(BaseStationProcessWork.getInstance().getBaseStationInfo().getBasename());
        baseStatusChangeRequest.setConfid(BaseStationProcessWork.getInstance().getBaseStationInfo().getConfid());
        baseStatusChangeRequest.setIdmode(BaseStationProcessWork.getInstance().getBaseStationInfo().getIdmode());
        baseStatusChangeRequest.setAuthcode(BaseStationProcessWork.getInstance().getBaseStationInfo().getAuthcode());
        baseStatusChangeRequest.setVoteid(BaseStationProcessWork.getInstance().getBaseStationInfo().getInfoid());
        baseStatusChangeRequest.setIdmode(BaseStationProcessWork.getInstance().getBaseStationInfo().getIdmode());
        baseStatusChangeRequest.setAttrib(BaseStationProcessWork.getInstance().getBaseStationInfo().getAttrib());
        baseStatusChangeRequest.setReportLanguage(BaseStationProcessWork.getInstance().getBaseStationInfo().getReportLanguage());
        baseStatusChangeRequest.setOfftime(BaseStationProcessWork.getInstance().getBaseStationInfo().getOfftime());
        baseStatusChangeRequest.setLogin(BaseStationProcessWork.getInstance().getBaseStationInfo().getLogin());
        return baseStatusChangeRequest;
    }

    /**
     * 处理查询在线状态数据包
     * 处理方式相应即可
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final QueryBeaconStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "QueryBeaconStateRequest", messageBean.datas);
                QueryBeaconStateResponse queryBeaconStateResponse = new QueryBeaconStateResponse(cmd);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    if (cmd.getCmd1() == 0x04) {
                        LogUtil.i(UDPModule.TAG, "notifyVoteToSDK,QueryBeaconStateRequest");
                        notifyVoteToSDK();
                        VoteStatusChangeRequest request = getVoteStatusChangeRequest();
                        byte[] datas = request.toBytes();
                        byteArrayOutputStream.write(datas, 1, datas.length - 1);
                    } else if (cmd.getCmd1() == 0x03) {
                        LogUtil.i(UDPModule.TAG, "notifyBaseToSDK,QueryBeaconStateRequest");
                        notifyBaseToSDK();
                        BaseStatusChangeRequest request = getBaseStatusChangeRequest();
                        byte[] datas = request.toBytes();
                        byteArrayOutputStream.write(datas, 1, datas.length - 1);
                    }
                } catch (Exception ex) {
                    LogUtil.e(UDPModule.TAG, ex);
                }
                queryBeaconStateResponse.setCmd1(cmd.getCmd1());
                queryBeaconStateResponse.setDatas(byteArrayOutputStream.toByteArray());
                Protocol<QueryBeaconStateResponse> protocol = new Protocol<>();
                protocol.setCmd(queryBeaconStateResponse);
//                SDKProcessWork.getInstance().sendProtocol(protocol);
            }
        };
    }

    /**
     * 基础信标处理任务
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final BaseStatusChangeRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "BaseStatusChangeRequest", messageBean.datas);

                BaseStationProcessWork.getInstance().getBaseStationInfo().setBaddh(cmd.getBaddh());
                BaseStationProcessWork.getInstance().getBaseStationInfo().setBasename(cmd.getBasename());
                BaseStationProcessWork.getInstance().getBaseStationInfo().setConfid(cmd.getConfid());
                BaseStationProcessWork.getInstance().getBaseStationInfo().setAuthcode(cmd.getAuthcode());
                BaseStationProcessWork.getInstance().getBaseStationInfo().setIdmode(cmd.getIdmode());

                BaseStatusChangeResponse baseStatusChangeResponse = new BaseStatusChangeResponse(cmd);

                Protocol<BaseStatusChangeResponse> protocol = new Protocol<>();
                protocol.setCmd(baseStatusChangeResponse);
                BaseStationProcessWork.getInstance().sendProtocol(protocol);
            }
        };
    }

    /**
     * 基础信标返回信息包处理方式
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final BaseStatusChangeResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "BaseStatusChangeResponse", messageBean.datas);
                RepeatMessageManager.getInstance().remove("BaseStatusChange");
            }
        };
    }

    /**
     * 下载单包数据处理方式
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final DownloadSingletonPkg cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "DownloadSingletonPkg", messageBean.datas);
                if (cmd.getKcmd() >= 0x05) {
                    if (compareBytes(cmd.getKeyid(), BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId())
                            || compareBytes(cmd.getKeyid(), new byte[]{0x00, 0x00})) {
                        if (cmd.getKcmd() == 0x07) {
                            sendTestResponse(cmd);
                        }
                        // 透传指令，直接转发给应用层APP
                        Protocol<DownloadSingletonPkg> protocol = new Protocol<>();
                        protocol.setCmd(cmd);
                        SDKProcessWork.getInstance().sendProtocol(protocol);
                    }
                } else {
                    // 0x01 ~ 0x04 的指令在module内部处理
                    // 0x01 读配置 0x02 写配置 0x03 读硬件信息（软模） 0x04 写软模信息
                    if (BaseStationProcessWork.getInstance().getBaseStationInfo().isCanWrite()) {
                        try {
                            if (cmd.getKcmd() == 0x01 || cmd.getKcmd() == 0x02) {
                                sendReadAndWriteVoterResponse(cmd);
                                return;
                            }
                            if (cmd.getKcmd() == 0x03 || cmd.getKcmd() == 0x04) {
                                sendReadAndWriteHardInfoResponse(cmd);
                            }
                        } finally {
                            forwardModeOperationState();
                        }
                    }
                }

            }
        };
    }

    /***
     * 转发给APP操作数据包
     */
    private static void forwardModeOperationState() {
        ModeOperationStateResponse response = new ModeOperationStateResponse();
        response.setOk((byte) 0x01);
        response.setCmd1((byte) 0x09);
        response.setChan(BaseStationProcessWork.getInstance().getBaseStationInfo().getChan());
        response.setKeyId(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        response.setKeySn(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeySn());
        response.setMatchCode(BaseStationProcessWork.getInstance().getBaseStationInfo().getMatchCode());
        Protocol<ModeOperationStateResponse> protocol = new Protocol<>();
        protocol.setCmd(response);
        SDKProcessWork.getInstance().sendProtocol(protocol);
    }

    /**
     * 发送读写硬件信息处理
     * @param cmd
     */
    private static void sendReadAndWriteHardInfoResponse(DownloadSingletonPkg cmd) {
        ReadAndWriteHardwareInformation request = new ReadAndWriteHardwareInformation();
        request.parseCmd(cmd.toBytes());
        if (cmd.getKcmd() == 0x04) {
            BaseStationProcessWork.getInstance().getBaseStationInfo().setModel(request.getModel());
            BaseStationProcessWork.getInstance().getBaseStationInfo().setSver(request.getNewverName());
            BaseStationProcessWork.getInstance().commit();
        }
        Protocol<ReadAndWriteHardwareInformation> p2 = new Protocol<>();
        p2.setCmd(request);
        BaseStationProcessWork.getInstance().sendProtocol(p2);
    }

    /**
     * 读写键盘编号信息包处理方式
     * @param cmd
     */
    private static void sendReadAndWriteVoterResponse(DownloadSingletonPkg cmd) {
        ReadAndWriteVoterAllocation request = new ReadAndWriteVoterAllocation();
        request.parseCmd(cmd.toBytes());
        if (cmd.getKcmd() == 0x02) {
            BaseStationProcessWork.getInstance().getBaseStationInfo().setKeyId(request.getNewid());
            BaseStationProcessWork.getInstance().getBaseStationInfo().setOfftime(request.getOfftime());
            BaseStationProcessWork.getInstance().getBaseStationInfo().setLockBase(request.getLockbase());
            BaseStationProcessWork.getInstance().getBaseStationInfo().setFixchan(request.getFixchan());
            BaseStationProcessWork.getInstance().commit();//持久化保存
        }
        request.setKeyid(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        request.setOfftime(BaseStationProcessWork.getInstance().getBaseStationInfo().getOfftime());
        request.setLockbase(BaseStationProcessWork.getInstance().getBaseStationInfo().getLockBase());
        request.setFixchan(BaseStationProcessWork.getInstance().getBaseStationInfo().getFixchan());
        request.setNewid(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        request.setKeycmd((byte) 0xB0);
        request.setKcmd((byte) 0x01);
        Protocol<ReadAndWriteVoterAllocation> p = new Protocol<>();
        p.setCmd(request);
        BaseStationProcessWork.getInstance().sendProtocol(p);
    }

    /**
     * 100次测试数据包处理
     * @param cmd
     */
    private static void sendTestResponse(DownloadSingletonPkg cmd) {
        DownloadSingletonPkg downloadSingletonPkg = new DownloadSingletonPkg();
        downloadSingletonPkg.parseCmd(cmd.toBytes());
        downloadSingletonPkg.setKeycmd((byte) 0xB0);
        downloadSingletonPkg.setKeyid(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        Protocol<DownloadSingletonPkg> protocol = new Protocol<>();
        protocol.setCmd(downloadSingletonPkg);
        BaseStationProcessWork.getInstance().sendProtocol(protocol);
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final KeyboardParameterStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "KeyboardParameterStateRequest", messageBean.datas);

                if (cmd.getCmd1() == 0x06) {
                    BaseStationProcessWork.getInstance().getBaseStationInfo().setKeyId(cmd.getKeyid());
                    BaseStationProcessWork.getInstance().getBaseStationInfo().setKeySn(cmd.getSkysn());
                    BaseStationProcessWork.getInstance().getBaseStationInfo().setMatchCode(cmd.getMatchCode());
                    BaseStationProcessWork.getInstance().commit();
                }
                notifyKeyboardState(cmd.getCmd1());
            }
        };
    }

    /**
     *
     * @param cmd1
     */
    public static void notifyKeyboardState(byte cmd1) {
        KeyboardParameterStateResponse keyboardParameterStateResponse = new KeyboardParameterStateResponse();
        keyboardParameterStateResponse.setOk((byte) 0x01);//ok
        keyboardParameterStateResponse.setChan(BaseStationProcessWork.getInstance().getBaseStationInfo().getChan());//FreqN
        keyboardParameterStateResponse.setCmd1(cmd1);
        keyboardParameterStateResponse.setKeyid(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        keyboardParameterStateResponse.setKeysn(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeySn());
        keyboardParameterStateResponse.setMatchCode(BaseStationProcessWork.getInstance().getBaseStationInfo().getMatchCode());
        keyboardParameterStateResponse.setHmode(BaseStationProcessWork.getInstance().getBaseStationInfo().getHmodel());
        keyboardParameterStateResponse.setSver(BaseStationProcessWork.getInstance().getBaseStationInfo().getSver());
        Protocol<KeyboardParameterStateResponse> protocol = new Protocol<>();
        protocol.setCmd(keyboardParameterStateResponse);
        SDKProcessWork.getInstance().sendProtocol(protocol);
    }

    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final ModeOperationStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "ModeOperationStateRequest", messageBean.datas);

                if (cmd.getCmd1() == 0x09) {
                    BaseStationProcessWork.getInstance().getBaseStationInfo().setCanWrite(true);
                    BaseStationProcessWork.getInstance().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BaseStationProcessWork.getInstance().getBaseStationInfo().setCanWrite(false);
                        }
                    });
                }
            }
        };
    }

    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final QueryOnlineStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "QueryOnlineStateRequest", messageBean.datas);
                sendQueryOnlineResponse();
                BaseStationProcessWork.getInstance().getBaseStationInfo().setVolt(cmd.getVolt());
                sendModuleHeartBeat();
            }
        };
    }

    /***
     *
     */
    private static void sendModuleHeartBeat() {
        ModuleHeartBeatCmd moduleHeartBeatCmd = new ModuleHeartBeatCmd();

        moduleHeartBeatCmd.setKeyid(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        moduleHeartBeatCmd.setAnstype((byte) 0x00);
        moduleHeartBeatCmd.setVolt(BaseStationProcessWork.getInstance().getBaseStationInfo().getVolt());

        moduleHeartBeatCmd.setRssi(BaseStationProcessWork.getInstance().getRx());
        BaseStationProcessWork.getInstance().getBaseStationInfo().setRssi(BaseStationProcessWork.getInstance().getRx());
        moduleHeartBeatCmd.setQpos(BaseStationProcessWork.getInstance().getBaseStationInfo().getQpos());
        moduleHeartBeatCmd.setDone(BaseStationProcessWork.getInstance().getBaseStationInfo().getDone());
        moduleHeartBeatCmd.setChange(BaseStationProcessWork.getInstance().getBaseStationInfo().getChange());
        moduleHeartBeatCmd.setSeconds(BaseStationProcessWork.getInstance().getBaseStationInfo().getSeconds());

        Protocol<ModuleHeartBeatCmd> protocol1 = new Protocol<>();
        protocol1.setCmd(moduleHeartBeatCmd);
        BaseStationProcessWork.getInstance().sendProtocol(protocol1);
    }

    /***
     *
     */
    private static void sendQueryOnlineResponse() {
        QueryOnlineStateResponse queryOnlineStateResponse = new QueryOnlineStateResponse();
        queryOnlineStateResponse.setCmd1(QueryOnlineStateResponse.CMD1);
        queryOnlineStateResponse.setOnline(BaseStationProcessWork.getInstance().getBaseStationInfo().getOnline());
        queryOnlineStateResponse.setIdmode(BaseStationProcessWork.getInstance().getBaseStationInfo().getIdmode());
        queryOnlineStateResponse.setChan(BaseStationProcessWork.getInstance().getBaseStationInfo().getChan());
        queryOnlineStateResponse.setRssi(BaseStationProcessWork.getInstance().getBaseStationInfo().getRssi());
        queryOnlineStateResponse.setTx(BaseStationProcessWork.getInstance().getBaseStationInfo().getTx());//sdk 是否到moduel有数据？
        queryOnlineStateResponse.setRx(BaseStationProcessWork.getInstance().getBaseStationInfo().getRx());//基站导模块是否有数据？
        queryOnlineStateResponse.setBaseID(BaseStationProcessWork.getInstance().getBaseStationInfo().getBaseID());
        queryOnlineStateResponse.setKeyId(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
        queryOnlineStateResponse.setKeySn(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeySn());
        Protocol<QueryOnlineStateResponse> protocol = new Protocol<>();
        protocol.setCmd(queryOnlineStateResponse);
        SDKProcessWork.getInstance().sendProtocol(protocol);
    }

    /****
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final WorkPattenStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "WorkPattenStateRequest", messageBean.datas);
                if (cmd.getCmd1() == WorkPattenStateRequest.CMD1_1) {
                    WorkPattenStateResponse workPattenStateResponse = new WorkPattenStateResponse();
                    workPattenStateResponse.setCmd(WorkPattenStateResponse.RESPONSE_CMD);
                    workPattenStateResponse.setCmd1(cmd.getCmd1());
                    workPattenStateResponse.setMode(BaseStationProcessWork.getInstance().getBaseStationInfo().getMode());////1 基站模式 2 键盘模式
                    workPattenStateResponse.setHmodel(BaseStationProcessWork.getInstance().getBaseStationInfo().getHmodel());
                    workPattenStateResponse.setSver(BaseStationProcessWork.getInstance().getBaseStationInfo().getSver());
                    Protocol<WorkPattenStateResponse> protocol = new Protocol<>();
                    protocol.setCmd(workPattenStateResponse);
                    SDKProcessWork.getInstance().sendProtocol(protocol);
                } else {
                    // 设置没有数据，从哪里来？？？
                    // TODO
                    BaseStationProcessWork.getInstance().getBaseStationInfo().setMode(cmd.getMode());
                    BaseStationProcessWork.getInstance().commit();
                }
            }
        };
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final NumberingModeResult cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "NumberingModeResult", messageBean.datas);

                BaseStationProcessWork.getInstance().setNumberingModeResult(cmd);
                // 转发
                SingerUploadPkg singerUploadPkg = new SingerUploadPkg();
                singerUploadPkg.setKeyid(BaseStationProcessWork.getInstance().getBaseStationInfo().getKeyId());
                singerUploadPkg.setAnstype(cmd.getAnsType());
                singerUploadPkg.setAnsdata(cmd.getAnsData());
                Protocol<SingerUploadPkg> singerUploadPkgProtocol = new Protocol<>();
                singerUploadPkgProtocol.setCmd(singerUploadPkg);
                BaseStationProcessWork.getInstance().sendProtocol(singerUploadPkgProtocol);

                // need ?
//                RepeatMessageManager.getInstance().put("SingerUploadPkg",singerUploadPkgProtocol,RepeatMessageManager.BASE_STATION);

                // 回复给SDK
                TransferResult transferResult = new TransferResult();
                transferResult.setMsgId(cmd.getMsgId());
                transferResult.setStatus((byte) 0x01);
                transferResult.setMsgType(cmd.getMsgType());
                Protocol<TransferResult> protocol = new Protocol<>();
                protocol.setCmd(transferResult);
                SDKProcessWork.getInstance().sendProtocol(protocol);

            }
        };
    }

    /**
     * 多包命令
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final MutiPkgDownCmd cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                Protocol<MutiPkgDownCmd> protocol = new Protocol<>();
                protocol.setCmd(cmd);
                SDKProcessWork.getInstance().sendProtocol(protocol);
            }
        };
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final VoteStatusChangeRequest cmd) {
        return null;
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final VoteStatusChangeResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "VoteStatusChangeResponse", messageBean.datas);
                //
                RepeatMessageManager.getInstance().remove("VoteStatusChange");

            }
        };
    }
    /****
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final KeyboardParameterStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "KeyboardParameterStateResponse", messageBean.datas);
            }
        };
    }
    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final ModeOperationStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "ModeOperationStateResponse", messageBean.datas);
            }
        };
    }

    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final QueryOnlineStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "QueryOnlineStateResponse", messageBean.datas);
            }
        };
    }



    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final WorkPattenStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "WorkPattenStateResponse", messageBean.datas);
            }
        };
    }

    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final CleanFlashStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "CleanFlashStateRequest", messageBean.datas);
            }
        };
    }


    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final CleanFlashStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "CleanFlashStateResponse", messageBean.datas);
            }
        };
    }

    /**
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final UpgradeExitStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "UpgradeExitStateRequest", messageBean.datas);
            }
        };
    }

    /***
     *
     * @param cmd
     * @return
     */
    public static WorkThread.ExecuteMethod getExecuteMethod(final UpgradeExitStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "UpgradeExitStateResponse", messageBean.datas);
            }
        };
    }

    public static WorkThread.ExecuteMethod getExecuteMethod(final UpgradEntryStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "UpgradEntryStateRequest", messageBean.datas);
            }
        };
    }

    public static WorkThread.ExecuteMethod getExecuteMethod(final UpgradEntryStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "UpgradEntryStateResponse", messageBean.datas);
            }
        };
    }

    public static WorkThread.ExecuteMethod getExecuteMethod(final WriteFlashStateRequest cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "WriteFlashStateRequest", messageBean.datas);
            }
        };
    }

    public static WorkThread.ExecuteMethod getExecuteMethod(final WriteFlashStateResponse cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "WriteFlashStateResponse", messageBean.datas);
            }
        };
    }

    public static WorkThread.ExecuteMethod getExecuteMethod(final SequenceFormatResult cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "SequenceFormatResult", messageBean.datas);
            }
        };
    }

    public static WorkThread.ExecuteMethod getExecuteMethod(final TransferResult cmd) {
        return new WorkThread.ExecuteMethod() {
            @Override
            public void execute(WorkThread.MessageBean messageBean) {
                LogUtil.i(UDPModule.TAG, "TransferResult", messageBean.datas);
            }
        };
    }


}
