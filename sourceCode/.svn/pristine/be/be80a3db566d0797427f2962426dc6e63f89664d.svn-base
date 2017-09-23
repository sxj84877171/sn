package com.sunvote.txpad;

import com.sunvote.sunvotesdk.BaseStationManager;
import com.sunvote.sunvotesdk.basestation.DefaultKeyEventCallBack;
import com.sunvote.sunvotesdk.basestation.KeyBoard;
import com.sunvote.txpad.bean.Student;
import com.sunvote.util.LogUtil;

import java.util.List;

/**
 * Created by Elvis on 2017/9/21.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ApplicationDataManager {
    private static final ApplicationDataManager ourInstance = new ApplicationDataManager();

    public static ApplicationDataManager getInstance() {
        return ourInstance;
    }

    private ApplicationDataManager() {
    }

    public void registerKeyEventCallBack() {
        BaseStationManager.getInstance().registerKeyEventCallBack(keyEventCallBack);
    }

    public void unRegisterKeyEventCallBack() {
        BaseStationManager.getInstance().unRegisterKeyEventCallBack(keyEventCallBack);
    }

    private DefaultKeyEventCallBack keyEventCallBack = new DefaultKeyEventCallBack() {
        @Override
        public void keyEventKeyResultInfo(final String keyId, final String info, final float time) {
            if ("1".equals(info)) {
                isStudentKeyIdSign(keyId);
            }
        }

        @Override
        public void keyEventKeyResultStats(String keyId, String info, float time) {
            super.keyEventKeyResultStats(keyId,info,time);
            setKeyboardOnline(keyId,info);
        }

        @Override
        public void keyEventKeyResultLoginInfo(final String keyId, final String info, float time) {
            isAdd(info, keyId);
        }

        @Override
        public void keyEventKeyResultRemoteControlAnswer(String keyId, String info, float time) {
            LogUtil.i("SigePresent", "keyId:" + keyId + ",info:" + info + ",time:" + time);
        }

        @Override
        public void keyEventKeyResultMatch(String keyId, String info, float time) {
            LogUtil.i("SigePresent", "keyId:" + keyId + ",info:" + info + ",time:" + time);
        }
    };

    private boolean isStudentKeyIdSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyId.equals(keyBoard.getKeyId())) {
                    return keyBoard.isSign();
                }
            }
        }
        return false;
    }

    private boolean addStudentKeyIdSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyBoard.getKeyId() == null) {
                    keyBoard.setKeyId(keyId);
                    keyBoard.setSign(true);
                    return true;
                } else if (keyBoard.getKeyId().equals(keyId)) {
                    keyBoard.setSign(true);
                    return true;
                }

            }
        }
        return false;
    }

    private boolean isStudentSnSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyId.equals(keyBoard.getKeyId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean addStudentSnSign(String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyBoard.getKeyId() == null) {
                    keyBoard.setKeyId(keyId);
                    return true;
                }
            }
        }
        return false;
    }

    public int getSignStudent() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyBoard.isSign()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getKeyboarWeak() {
        int count = 0;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                if (keyBoard.isWeak()) {
                    count++;
                }
            }
        }
        return count;
    }

    public void clearSign() {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            KeyBoard keyBoard = student.getKeyBoard();
            if (keyBoard != null) {
                keyBoard.setSign(false);
            }
        }
    }

    private boolean isAdd(String no, String keyId) {
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for (Student student : studentList) {
            if (student.getStudentId().equals(no)) {
                KeyBoard keyBoard = student.getKeyBoard();
                keyBoard.setKeyId(keyId);
                keyBoard.setSign(true);
                return true;
            }
        }
        return false;
    }

    public void setKeyboardOnline(String keyId,String info){
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if(studentList != null) {
            for (Student student : studentList) {
                if(keyId.equals(student.getKeyBoard().getKeyId())){
                    student.getKeyBoard().setOnline(true);
                    String[] strs = info.split(",");
                    if(strs.length >= 3){
                        float dm = Float.parseFloat(strs[2]);
                        if(dm < 2.4f){
                            student.getKeyBoard().setWeak(true);
                        }
                    }
                    return;
                }
            }
        }
    }

    public void setOfflineAllKeyboard(){
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if(studentList != null){
            for (Student student : studentList) {
                student.getKeyBoard().setOnline(false);
            }
        }
    }

    public int getStudentCount(){
        int count = 0 ;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        if(studentList != null){
            count = studentList.size();
        }
        return count;
    }

    public int getOnlineStudentCount(){
        int count = 0 ;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for(Student student : studentList){
            if(student.getKeyBoard().isOnline()){
                count ++ ;
            }
        }
        return count;
    }

    public int getWeakStudentCount(){
        int count = 0 ;
        List<Student> studentList = ApplicationData.getInstance().getStudentList();
        for(Student student : studentList){
            if(student.getKeyBoard().isWeak()){
                count ++ ;
            }
        }
        return count;
    }
}
