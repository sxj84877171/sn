package com.sunvote.cmd.push;

import com.sunvote.cmd.BaseCmd;

/**
 * Created by Elvis on 2017/8/14.
 * Email:Eluis@psunsky.com
 * Description:
 */

public abstract class PushBaseCmd extends BaseCmd {

    public static PushBaseCmd parseRequest(byte[] bytes, int length) {
        switch (bytes[0]){
            case BaseStatusChangeRequest.REQUEST_CMD:
                BaseStatusChangeRequest baseStatusChangeRequest = new BaseStatusChangeRequest();
                baseStatusChangeRequest.parseCmd(bytes);
                return baseStatusChangeRequest;
            case DownloadSingletonPkg.CMD:
                DownloadSingletonPkg downloadSingletonPkg= new DownloadSingletonPkg();
                downloadSingletonPkg.parseCmd(bytes);
                return downloadSingletonPkg;
            case VoteStatusChangeRequest.REQUEST_CMD:
                VoteStatusChangeRequest voteStatusChangeRequest = new VoteStatusChangeRequest();
                voteStatusChangeRequest.parseCmd(bytes);
                return voteStatusChangeRequest;
        }
        return null;

    }

    public static PushBaseCmd parseResponse(byte[] bytes, int length) {

        switch (bytes[0]){
            case BaseStatusChangeResponse.RESPONSE_CMD:
                BaseStatusChangeResponse baseStatusChangeResponse = new BaseStatusChangeResponse();
                baseStatusChangeResponse.parseCmd(bytes);
                return baseStatusChangeResponse;
        }
        return null;

    }
}
