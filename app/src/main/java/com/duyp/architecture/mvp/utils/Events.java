package com.duyp.architecture.mvp.utils;

import android.location.Location;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by phamd on 6/9/2017.
 * Event bus classes
 */

public final class Events {

    // not allow creating instance of this class
    private Events() {}

    @NoArgsConstructor
    public static final class LogoutEvent {}
}