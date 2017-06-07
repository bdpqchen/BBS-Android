package com.twtstudio.bbs.bdpqchen.bbs.commons.support.job;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

/**
 * Created by bdpqchen on 17-6-7.
 */

public class JobHelper extends Job {

    public static final String JOB_TAG = "job_tag";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {


        return Result.SUCCESS;
    }

    private void scheduleJob(){
        new JobRequest.Builder(JobHelper.JOB_TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .build()
                .schedule();
    }

}
