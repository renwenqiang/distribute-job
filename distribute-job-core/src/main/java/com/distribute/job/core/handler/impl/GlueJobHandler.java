package com.distribute.job.core.handler.impl;

import com.distribute.job.core.handler.IJobHandler;

/**
 * @author renwq
 * @date 2021/5/21 15:59
 */
public class GlueJobHandler extends IJobHandler {

    private long glueUpdateTime;
    private IJobHandler jobHandler;

    public GlueJobHandler(IJobHandler jobHandler, long glueUpdateTime) {
        this.jobHandler = jobHandler;
        this.glueUpdateTime = glueUpdateTime;
    }

    public long getGlueUpdateTime() {
        return glueUpdateTime;
    }

    @Override
    public void execute() throws Exception {
        jobHandler.execute();
    }

    @Override
    public void init() throws Exception {
        jobHandler.init();
    }

    @Override
    public void destroy() throws Exception {
        jobHandler.destroy();
    }
}
