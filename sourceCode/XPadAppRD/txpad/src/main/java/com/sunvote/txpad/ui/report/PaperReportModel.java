package com.sunvote.txpad.ui.report;

import com.sunvote.txpad.base.BaseModel;
import com.sunvote.txpad.bean.PaperScore;
import com.sunvote.txpad.bean.ResponseDataBean;

import rx.Observable;

/**
 * Created by Elvis on 2017/10/9.
 * Email:Eluis@psunsky.com
 * 版权所有：长沙中天电子设计开发有限公司
 * Description: 易测（ETest）
 */
public class PaperReportModel extends BaseModel {

    public static final String LEFT_FIX_CONTENT_ITEM_COL = "<col style=\"width: 25%\" />\n" +
            "\t\t\t<col style=\"width: 25%\" />\n" +
            "\t\t\t<col style=\"width: 20%\" />\n" +
            "\t\t\t<col style=\"width: 20%\" />\n" +
            "\t\t\t<col style=\"width: 10%\" />" ;

    public static final String LEFT_FIX_CONTENT_ITEM_ = "" ;

    public Observable<ResponseDataBean<PaperScore>> getReport(String reportId){
        return apiService.getReport(reportId).compose(BaseModel.<ResponseDataBean<PaperScore>>io_main());
    }

    public String getHtmlHeaderContent(String title){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "\n" +
                "<title>统计表</title> \n" +
                "<meta name=\"viewport\" content=\"width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\">\n" +
                "<style> \n" +
                "table{border-collapse:collapse;border-spacing:0px; width:100%; border:#000 solid 0px;} \n" +
                "table td{border:1px solid #000;height:25px; text-align:center; border-left:0px;} \n" +
                "table th{ background:#ebfbfd; color:#3d5968; border:#000 solid 1px; white-space:nowrap; height:21px; border-top:0px;border-left:0px;} \n" +
                ".flunk{background:#ff7c81;}\n" +
                ".true{background:#93d150;}\n" +
                ".t_left{width:40%; height:auto; float:left;border-top:1px solid #000} \n" +
                ".t_r_content{width:100%; height:100%; background:#fff; overflow:auto;} \n" +
                ".cl_freeze{height:100%;overflow:hidden; width:100%;} \n" +
                ".cl_freeze tr{border-left:1px solid #000;}\n" +
                ".cl_freeze tr:nth-child(2n){background:#f1f1f1;}\n" +
                ".t_r{width:59%; height:auto; float:left;border-top:1px solid #000; border-right:#000 solid 1px;}   \n" +
                ".t_r table{width:1000px;}\n" +
                ".t_r_title{width:1020px;}\n" +
                ".t_r_t{width:100%; overflow:hidden;} \n" +
                ".bordertop{ border-top:0px;} \n" +
                "</style> \n" +
                "<script> \n" +
                "  function aa(){ \n" +
                "     var a=document.getElementById(\"t_r_content\").scrollTop; \n" +
                "     var b=document.getElementById(\"t_r_content\").scrollLeft; \n" +
                "      document.getElementById(\"cl_freeze\").scrollTop=a; \n" +
                "      document.getElementById(\"t_r_t\").scrollLeft=b; \n" +
                "  } \n" +
                "</script> \n" +
                "</head> ";
    }

    public String leftFixContentItem(){
        return LEFT_FIX_CONTENT_ITEM_COL;
    }

    public String getDivTLeftTableTBodyTag(String NO,String username,String rate,String score,String rang){
        return "<tbody>\n" +
                "\t\t\t\t<tr> \n" +
                "\t\t\t\t\t<th style=\"width:auto;height:47px;border-left:1px solid #000;\">" + NO + "</th> \n" +
                "\t\t\t\t\t<th style=\"width:auto\" >" + username + "</th> \n" +
                "\t\t\t\t\t<th style=\"width:auto\" >" + rate + "</th> \n" +
                "\t\t\t\t\t<th style=\"width:auto\" >" + score + "</th> \n" +
                "\t\t\t\t\t<th style=\"width:auto\" >" + rang + "</th> \n" +
                "\t\t\t\t</tr> \n" +
                "\t\t\t</tbody>\n";
    }

    public String getFristTableTag(){
        return "<table>\n";
    }

    public String getLastTableTag(){
        return "</table>\n";
    }

    public String getDivTLeft(){
        return "<div class=\"t_left\"> \n";
    }

    public String getLastDiv(){
        return "</div>\n";
    }

    public String getDivStyle100(){
        return "<div style=\"width:100%\">\n";
    }

    public String getDivFreeze(){
        return "<div class=\"cl_freeze\" id=\"cl_freeze\">\n";
    }

    public String getDivTLeftTableTBodyContentTag(String NO,String username,String rate,String score,String rang){
        return "<tr> \n" +
                "\t\t\t\t\t  <td style=\"auto\" class=\"bordertop\">" + NO + "</td> \t\n" +
                "\t\t\t\t\t  <td style=\"auto\" class=\"bordertop\">" + username + "</td>\t\n" +
                "\t\t\t\t\t  <td style=\"auto\" class=\"bordertop\">" + rate + "</td>\t\n" +
                "\t\t\t\t\t  <td style=\"auto\" class=\"bordertop\">" + score + "</td>\n" +
                "\t\t\t\t\t  <td style=\"auto\" class=\"bordertop\">" + rang + "</td>\n" +
                "\t\t\t\t\t</tr>\n";
    }

    public String getDivT_R(){
        return "<div class=\"t_r\">\n";
    }

    public String getDivT_R_T(){
        return "<div class=\"t_r_t\" id=\"t_r_t\">\n ";
    }

    public String getDivT_R_Title(){
        return "<div class=\"t_r_title\"> \n";
    }

    public String getDivT_R_content(){
        return "<div class=\"t_r_content\" id=\"t_r_content\" onscroll=\"aa()\"> ";
    }

    public String getQuestionCell(String content){
        if(content == null) content = "" ;
        return "<th width=\"50px\">" + content + "</th>\n";
    }

    public String getQuestionCellLow(String content){
        if(content == null) content = "" ;
        return "<th width=\"50px\" class=\"flunk\">" + content + "</th>\n";
    }

    public String getQuestionCellRight(String content){
        if(content == null) content = "" ;
        return " <td width=\"50px\" class=\"bordertop true\">" + content + "</td>\n" ;
    }

    public String getQuestionCellWrong(String content){
        if(content == null) content = "" ;
        return "<td width=\"50px\" class=\"bordertop\">" + content + "</td>\n";
    }

    public String getTrFristTag(){
        return "<tr>\n";
    }

    public String getTrLastTag(){
        return "</tr>";
    }

    public String getBodyFrist(){
        return "<body>\n" ;
    }

    public String getBodyLast(){
        return "</body>\n" ;
    }
}
