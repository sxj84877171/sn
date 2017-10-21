package com.sunvote.txpad;

import android.text.TextUtils;

import com.sunvote.txpad.bean.StudentScore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Elvis on 2017/9/22.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class ApplicationDataHelper {
    private static final ApplicationDataHelper ourInstance = new ApplicationDataHelper();

    public static ApplicationDataHelper getInstance() {
        return ourInstance;
    }

    private ApplicationDataHelper() {
    }

    public int getQuestionType(int id) {
        switch (id) {
            case 1:
                return R.string.examination_question_type_choose;
            default:
                return 0;
        }
    }

    public int getPaperType(String id) {
        switch (id) {
            case "mn":
                return R.string.score_book_type_analog;
            case "zt":
                return R.string.score_book_type_zhenti;
            case "pv":
                return R.string.score_book_type_personal;
        }
        return R.string.score_book_type_analog;
    }

    public Map<Integer, Integer> getKeyBoardReplace(String string) {
        Map<Integer, Integer> map = new HashMap<>();
        if (string != null) {
            String[] items = string.split(";");
            if (items.length > 0) {
                for (String item : items) {
                    String[] keyboards = item.split(":");
                    if (keyboards.length == 2) {
                        int oldBoard = Integer.parseInt(keyboards[0]);
                        int newBoard = Integer.parseInt(keyboards[1]);
                        map.put(oldBoard, newBoard);
                    }
                }
            }
        }
        return map;
    }

    public String getKeyboardReplaceStr(Map<Integer, Integer> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            Set<Integer> set = map.keySet();
            for (Integer integer : set) {
                sb.append(integer).append(":").append(map.get(integer)).append(";");
            }
        }
        return sb.toString();
    }

    public Integer getKeyboardBeenReplace(Map<Integer,Integer> map, Integer newKeyboard){
        int ret = - 1;
        if(map != null){
            Set<Integer> set = map.keySet();
            for (Integer integer : set) {
                if(map.get(integer) == newKeyboard){
                    return integer;
                }
            }
        }
        return  ret;
    }

    public boolean isEffectiveStduent(StudentScore studentScore){
        if(studentScore != null){
            List<String> strings = studentScore.getAnswer();
            if(strings != null){
                for(String str : strings){
                    if(!TextUtils.isEmpty(str)){
                        String[] ss = str.split(",");
                        if(ss != null && ss.length >= 3 && (!TextUtils.isEmpty(ss[2]))){
                          if(!"0".equals(ss[2])){
                              return true;
                          }
                        }
                    }
                }
            }
        }
        return false;
    }
}
