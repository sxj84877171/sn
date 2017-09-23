package com.sunvote.cmd;

import com.sunvote.cmd.app.BaseBeaconStateRequest;
import com.sunvote.cmd.app.MutiPkgDownCmd;
import com.sunvote.cmd.push.BaseStatusChangeRequest;
import com.sunvote.cmd.push.BaseStatusChangeResponse;
import com.sunvote.cmd.push.DownloadSingletonPkg;
import com.sunvote.cmd.push.PushBaseCmd;
import com.sunvote.cmd.push.VoteStatusChangeRequest;
import com.sunvote.cmd.push.VoteStatusChangeResponse;
import com.sunvote.cmd.state.GetPkgStateRequest;
import com.sunvote.cmd.state.GetPkgStateResponse;
import com.sunvote.cmd.state.StateBaseCmd;
import com.sunvote.cmd.upgrade.UpgradeBaseCmd;
import com.sunvote.cmd.upload.UploadBaseCmd;

/**
 * Created by Elvis on 2017/8/11.
 * Email:Eluis@psunsky.com
 * Description:
 */

public abstract class BaseCmd implements ICmd {

    @Override
    public ICmd parseCmd(byte[] source) {
        return parseCmd(source,0);
    }

    public static ICmd parse(byte[] datas,int length){
        if(length > 5){
            byte[] ds = new byte[length - 4];
            for(int i = 0 ; i < length - 4; i ++){
                ds[i] = datas[i+4];
            }
            switch (ds[0]) {
                case StateBaseCmd.REQUEST_CMD:
                    return StateBaseCmd.parseRequest(ds, length);
                case BaseStatusChangeRequest.REQUEST_CMD:
                    return PushBaseCmd.parseRequest(ds,length);
                case UpgradeBaseCmd.REQUEST_CMD:
                    return UpgradeBaseCmd.parseRequest(ds,length);
                case UploadBaseCmd.REQUEST_CMD:
                    return UploadBaseCmd.parseRequest(ds,length);
                case StateBaseCmd.RESPONSE_CMD:
                    return StateBaseCmd.parseResponse(ds, length-4);
                case BaseStatusChangeResponse.RESPONSE_CMD:
                    return PushBaseCmd.parseResponse(ds,length-4);
                case UpgradeBaseCmd.RESPONSE_CMD:
                    return UpgradeBaseCmd.parseResponse(ds,length-4);
                case UploadBaseCmd.RESPONSE_CMD:
                    return UploadBaseCmd.parseResponse(ds,length-4);
                case GetPkgStateRequest.CONTEND_1:
                case GetPkgStateRequest.CONTEND_2:
                     return GetPkgStateRequest.parseRequest(ds,length-4);
                case BaseBeaconStateRequest.CMD:
                    return BaseBeaconStateRequest.parseRequest(ds,length-4);
                case GetPkgStateResponse.CMD:
                    return GetPkgStateResponse.parseRequest(ds,length-4);
                case DownloadSingletonPkg.CMD:
                    return PushBaseCmd.parseRequest(ds,length-4);
                case VoteStatusChangeRequest.REQUEST_CMD:
                    return PushBaseCmd.parseRequest(ds,length-4);
                case MutiPkgDownCmd.DOWNCMD:
                    return MutiPkgDownCmd.parseRequest(ds,length-4);
                case VoteStatusChangeResponse.RESPONE_CMD:
                    return VoteStatusChangeResponse.parseRequest(ds,length-4);
                default:
                    return null;
            }
        }
        return null;
    }
}
