package com.sunvote.cmd.upload;

import com.sunvote.cmd.BaseCmd;

/**
 * Created by Elvis on 2017/8/14.
 * Email:Eluis@psunsky.com
 * Description:
 */

public abstract class UploadBaseCmd extends BaseCmd {

    public static final byte REQUEST_CMD = 0x73 ;
    public static final byte RESPONSE_CMD = (byte) 0xF3;

    /**
     * TODO
     * @param bytes
     * @param length
     * @return
     */
    public static UploadBaseCmd parseRequest(byte[] bytes, int length){
        if(bytes[2] == 0x01){
            NumberingModeResult numberingModeResult = new NumberingModeResult();
            numberingModeResult.parseCmd(bytes);
            return numberingModeResult;
        }
        if(bytes[2] == 0x02) {
            SequenceFormatResult sequenceFormatResult = new SequenceFormatResult();
            sequenceFormatResult.parseCmd(bytes);
            return sequenceFormatResult;
        }
        return null;
    }

    /**
     * TODO
     * @param bytes
     * @param length
     * @return
     */
    public static UploadBaseCmd parseResponse(byte[] bytes, int length){
        TransferResult transferResult = new TransferResult();
        transferResult.parseCmd(bytes);
        return transferResult;
    }
}
