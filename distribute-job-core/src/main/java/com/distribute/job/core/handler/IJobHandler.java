package com.distribute.job.core.handler;

/**
 * @author renwq
 * @date 2021/5/21 15:43
 */
public abstract class IJobHandler {

    public abstract void execute() throws Exception;

    public void init() throws Exception {

    }

    public void destroy() throws Exception {

    }

}
