package com.sunvote.txpad;

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

    public int getQuestionType(int id){
        switch (id){
            case 1:
                return R.string.examination_question_type_choose;
            default:
                return 0;
        }
    }
}
