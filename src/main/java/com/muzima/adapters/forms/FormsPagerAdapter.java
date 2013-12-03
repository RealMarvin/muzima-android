/**
 * Copyright 2012 Muzima Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.muzima.adapters.forms;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.muzima.MuzimaApplication;
import com.muzima.adapters.MuzimaPagerAdapter;
import com.muzima.controller.FormController;
import com.muzima.view.forms.AllAvailableFormsListFragment;
import com.muzima.view.forms.CompleteFormsListFragment;
import com.muzima.view.forms.DownloadedFormsListFragment;
import com.muzima.view.forms.FormsListFragment;
import com.muzima.view.forms.IncompleteFormsListFragment;

/**
 * Responsible to display all the form fragments under form section.
 */
public class FormsPagerAdapter extends MuzimaPagerAdapter implements TagsListAdapter.TagsChangedListener {
    public static final int TAB_All = 0;
    public static final int TAB_DOWNLOADED = 1;
    public static final int TAB_INCOMPLETE = 2;
    public static final int TAB_COMPLETE = 3;

    public FormsPagerAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    public void onFormMetadataDownloadStart(){
        ((AllAvailableFormsListFragment)pagers[TAB_All].fragment).onFormMetaDataDownloadStart();
    }

    public void onFormMetadataDownloadFinish(){
        ((AllAvailableFormsListFragment)pagers[TAB_All].fragment).onFormMetaDataDownloadFinish();
    }

    public void onFormTemplateDownloadFinish() {
        ((AllAvailableFormsListFragment)pagers[TAB_All].fragment).onFormTemplateDownloadFinish();
    }

    public void onFormUploadFinish() {
        ((CompleteFormsListFragment)pagers[TAB_COMPLETE].fragment).onFormUploadFinish();
    }

    @Override
    public void onTagsChanged() {
        ((FormsListFragment)pagers[TAB_All].fragment).tagsChanged();
    }

    @Override
    public void initPagerViews() {
        pagers = new PagerView[4];
        FormController formController = ((MuzimaApplication) context.getApplicationContext()).getFormController();

        AllAvailableFormsListFragment allAvailableFormsListFragment = AllAvailableFormsListFragment.newInstance(formController);
        DownloadedFormsListFragment downloadedFormsListFragment = DownloadedFormsListFragment.newInstance(formController);
        CompleteFormsListFragment completeFormsListFragment = CompleteFormsListFragment.newInstance(formController);
        IncompleteFormsListFragment incompleteFormsListFragment = IncompleteFormsListFragment.newInstance(formController);

        allAvailableFormsListFragment.setTemplateDownloadCompleteListener(downloadedFormsListFragment);

        pagers[TAB_All] = new PagerView("All", allAvailableFormsListFragment);
        pagers[TAB_DOWNLOADED] = new PagerView("Downloaded", downloadedFormsListFragment);
        pagers[TAB_COMPLETE] = new PagerView("Complete", completeFormsListFragment);
        pagers[TAB_INCOMPLETE] = new PagerView("Incomplete", incompleteFormsListFragment);
    }

    public void endActionMode() {
        ((AllAvailableFormsListFragment)pagers[TAB_All].fragment).endActionMode();
    }

    public void unselectList() {
        pagers[TAB_All].fragment.unselectAllItems();
    }
}
