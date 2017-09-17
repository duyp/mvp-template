package com.duyp.architecture.mvp.utils.roomConverters;

import android.arch.persistence.room.TypeConverter;

import com.duyp.architecture.mvp.data.model.Label;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class LabelConverter {

    @TypeConverter
    public static List<Label> toList(String labels) {
        List<Label> result = new ArrayList<>();
        String[] items = labels.split(",");
        for (String s : items) {
            if (!s.isEmpty()) {
                String[] s1 = s.split("-");
                Label label = new Label();
                label.setName(s1[0]);
                label.setColor(s1[1]);
                result.add(label);
            }
        }
        return result;
    }

    @TypeConverter
    public static String fromList(List<Label> labels) {
        StringBuilder sb = new StringBuilder("");
        for (Label label : labels) {
            sb.append(label.getName()).append("-").append(label.getColor())
                    .append(",");
        }
        return sb.toString();
    }
}
