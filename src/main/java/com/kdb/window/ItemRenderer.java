package com.kdb.window;

import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class ItemRenderer extends BasicComboBoxRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null) {
            JSONObject item = (JSONObject) value;
            setText(item.getString("name"));
        }

        if (index == -1) {
            JSONObject item = (JSONObject) value;
            setText(item.getString("categoryId"));
        }
        return this;
    }
}
