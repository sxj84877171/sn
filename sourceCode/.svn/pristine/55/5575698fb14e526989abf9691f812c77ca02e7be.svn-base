package com.sunvote.xpadapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reader.bookreadpdf.bookreadslideview.PDFViewShowActivity;
import com.sunvote.xpadapp.MainActivity;
import com.sunvote.xpadapp.R;
import com.sunvote.xpadapp.base.BaseFragment;
import com.ycanfunc.func.LibHttpOperate;

import java.io.File;

/**
 *
 * Created by wutaian on 2017/9/19 0019.
 */

public class PDFContextShowFragment extends BaseFragment {

    boolean isShowBar=false;
    String strUserName = "";
    String filetype = "0";  // 必须为0
    String filename = "";//
    String fileauthor = "";
    String filepagenum = "1";
    String fileid = "1"; // 必须存在
    String filepath = "";
    String coverpath = "";
    String filekey = "";
    String filecover = "";
    String lockpage= "0";

    MainActivity mact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View  view = inflater.inflate(R.layout.activity_pdf, container, false);
        mact = (MainActivity) getActivity();
        if(!mact.isLoadPDF){
            openFile();
            mact.isLoadPDF=true;
        }
        /*//获取数据
        Bundle bundle=this.getIntent().getExtras();
        String filepathTemp= bundle.get("filepath").toString();
        if(!StringUtils.isEmpty(filepathTemp)){
            filepath = filepathTemp;
        }
        String filenameTemp= bundle.get("filename").toString();
        if(!StringUtils.isEmpty(filenameTemp)){
            filename = filenameTemp;
        }
        String filepagenumTemp= bundle.get("filepagenum").toString();
        if(!StringUtils.isEmpty(filepagenumTemp)){
            filepagenum = filepagenumTemp;
        }
        String lockpageTemp= bundle.get("lockpage").toString();
        if(!StringUtils.isEmpty(lockpageTemp)){
            lockpage = lockpageTemp;
        }*/
       return view;
    }

       /**
     * 设置页码
     * @param nPage
     * @return
     */
    public int setPageIndex(int  nPage){
        return  PDFViewShowActivity.setPageIndex(nPage);
    }

    /**
     * 关闭
     */
    public void setCloseFile(){
        try {
            PDFViewShowActivity.setCloseFile();
        }catch (Exception e){
            Log.d("PDFClose",e.toString());
        }
    }

    /**
     *  设置关闭按钮显示隐藏
     *  显示false,隐藏true
     */
    public void setTopBarstate(){
        try {
            PDFViewShowActivity.setTopBarstate(isShowBar);
        }catch (Exception e){
            Log.d("TopBarstate",e.toString());
        }
    }

    /**
     * 设置pdf信息
     * @param filepathTemp
     *          文件路径
     * @param filenameTemp
     *          文件名
     * @param filepagenumTemp
     *          页面页数
     * @param lockpageTemp
     *          是否锁屏(0不锁屏，1锁屏)
     */
    public void setInfo(String filepathTemp,String filenameTemp,String filepagenumTemp,String lockpageTemp,boolean isShow){
        filepath=filepathTemp;
        filename=filenameTemp;
        filepagenum=filepagenumTemp;
        lockpage=lockpageTemp;
        isShowBar=isShow;
    }

    // 打开文件
    public void openFile() {
        File fpath = new File(filepath);
        if (!fpath.exists()) {
            Toast.makeText(getActivity(),
                    "文件不存在", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent();
        if (filetype.equalsIgnoreCase("0")) {
            LibHttpOperate LibHttpOperate = new LibHttpOperate(getActivity());
            PDFViewShowActivity.SetLibHttpOperate(LibHttpOperate);
            intent.setClass(getActivity(), PDFViewShowActivity.class);
        } else {
            Toast.makeText(getActivity(),
                    "文件类型错误", Toast.LENGTH_LONG).show();
            return;
        }
        // 文件id
        intent.putExtra("bookId", fileid);
        // 文件名
        intent.putExtra("bookName", filename);
        // 文件作者
        intent.putExtra("author", fileauthor);
        // 文件阅读初始化页面页数
        intent.putExtra("pageNum", filepagenum);
        // 文件路径
        intent.putExtra("path", filepath);
        intent.putExtra("key", filekey);
        // 用户名
        intent.putExtra("username", strUserName);
        // 封面路径
        intent.putExtra("coverpath", filecover);
        // 头像路径
        intent.putExtra("portraitpath", coverpath);
        //显示隐藏关闭bar
        intent.putExtra("topbarstate",isShowBar);
        // 是否锁屏(0不锁屏，1锁屏)
        intent.putExtra("lockpage",lockpage);
        // app授权key(优看提供,用户提供项目包名)
        intent.putExtra("appkey","3F28FD12C0EB74833639901D785F1E6F");
        // app授权secret(优看提供,用户提供项目包名)
        intent.putExtra("appsecret","D63C0B90F584B0BE1A100B0406F2C");
        startActivityForResult(intent, 4);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==4){
            mact.isLoadPDF=false;
        }
    }
}
