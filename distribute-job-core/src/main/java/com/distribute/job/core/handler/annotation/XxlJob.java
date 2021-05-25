package com.distribute.job.core.handler.annotation;

/**
 * @author renwq
 * @date 2021/5/21 15:55
 */
public @interface XxlJob {

    String value();

    String init() default "";

    String destroy() default "";
}
