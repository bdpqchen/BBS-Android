package com.twtstudio.bbs.bdpqchen.bbs.commons.support.job;

import com.evernote.android.job.Job;

/**
 * Created by bdpqchen on 17-6-7.
 */

public class JobCreator implements com.evernote.android.job.JobCreator {

    @Override
    public Job create(String s) {
        switch (s) {
            case JobHelper.JOB_TAG:
                return new JobHelper();
            default:
                return null;
        }
    }
}
