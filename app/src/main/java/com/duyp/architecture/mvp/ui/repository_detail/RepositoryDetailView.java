package com.duyp.architecture.mvp.ui.repository_detail;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.data.model.Repository;

/**
 * Created by duypham on 9/17/17.
 */

public interface RepositoryDetailView extends BaseView {
    void populateData(Repository repository);
}
