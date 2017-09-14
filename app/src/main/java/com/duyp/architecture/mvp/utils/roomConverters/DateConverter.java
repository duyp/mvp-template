package com.duyp.architecture.mvp.utils.roomConverters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Converter class used for Room SQLite database {@link android.arch.persistence.room.TypeConverters}
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}