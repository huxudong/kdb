package com.kdb.window;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kdb.consts.Consts;
import com.kdb.util.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import javax.swing.*;
import java.util.Vector;

public class KdbWindow {

    JFrame JF=new JFrame("窗口");
    //创建一个 JMenuBar、JMenu、JMenuItem
    JMenuBar jmb=new JMenuBar();//菜单栏




    public KdbWindow(){
        //添加组件 代码要从简
        //将下拉菜单选项添加到菜单中
        RequestBody body = new FormBody.Builder().build();
        String categoryJsons = HttpRequest.post(Consts.CATEGORY_URL,body);
        JSONArray categorys = JSON.parseObject(categoryJsons).getJSONArray("info");
        Vector model = new Vector();
        for(int i = 0 ; i < categorys.size() ; i++ ){
            JSONObject category = categorys.getJSONObject(i);
            model.addElement(category);
        }
        JComboBox jComboBox = new JComboBox(model);
        jComboBox.setRenderer(new ItemRenderer());
        jComboBox.setSelectedIndex(0);
        //将菜单添加到菜单栏
        jmb.add(jComboBox);


        JF.setJMenuBar(jmb);//将菜单栏设置到窗体中
        JF.setSize(400, 300);
        JF.setVisible(true); //显示窗体　false隐藏窗体
    }

    public static void main(String[] args) {
        new KdbWindow();
    }
}
