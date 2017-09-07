package com.duyp.architecture.mvp.dagger.component;

import com.duyp.architecture.mvp.dagger.module.CustomViewModule;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;

import dagger.Subcomponent;

/**
 * Created by phamd on 8/3/2017.
 *
 */

@PerFragment
@Subcomponent(
        modules = CustomViewModule.class
)
public interface CustomViewComponent {

}
