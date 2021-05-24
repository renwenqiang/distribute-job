package com.distribute.job.core.thread;

import com.distribute.job.core.log.XxlJobFileAppender;
import com.distribute.job.core.util.DateUtil;
import com.distribute.job.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author renwq
 * @date 2021/5/21 14:19
 */
public class JobLogFileCleanThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLogFileCleanThread.class);
    private static JobLogFileCleanThread instance = new JobLogFileCleanThread();
    public static JobLogFileCleanThread getInstance() {
        return instance;
    }

    private Thread localThread;
    private volatile boolean toStop = false;

    public void start(final long logRetentionDays) {
        if (logRetentionDays < 3) {
            return;
        }
        localThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!toStop) {
                    try {
                        File[] fileList = new File(XxlJobFileAppender.getLogPath()).listFiles();
                        if (null != fileList && fileList.length > 0) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            Date nowDate = calendar.getTime();
                            for (File file: fileList) {
                                if (!file.isDirectory()) {
                                    continue;
                                }
                                if (file.getName().indexOf("-") == -1) {
                                    continue;
                                }
                                Date logFileCreateDate = DateUtil.parseDate(file.getName());
                                if (null != logFileCreateDate &&
                                        nowDate.getTime() - logFileCreateDate.getTime() >= logRetentionDays * 24 * 60 * 60 * 1000) {
                                    FileUtil.deleteRecursively(file);
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                    try {
                        TimeUnit.DAYS.sleep(1);
                    } catch (InterruptedException e) {
                        if (!toStop) {
                            LOGGER.info(">>>>>>>>>>> xxl-job, executor JobLogFileCleanThread thread destory.");
                        }
                    }
                }
            }
        });
        localThread.setDaemon(true);
        localThread.setName("xxl-job, executor JobLogFileCleanThread");
        localThread.start();
    }

    public void stop() {
        toStop = true;
        if (null == localThread) {
            return;
        }
        localThread.interrupt();
        try {
            localThread.join();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
