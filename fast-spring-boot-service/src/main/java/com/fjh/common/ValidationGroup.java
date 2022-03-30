package com.fjh.common;

import javax.validation.GroupSequence;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/3/30 10:28
 */
public class ValidationGroup {

    public interface Insert {
    }

    public interface Update {
    }

    @GroupSequence({Insert.class, Update.class})
    public interface all {
    }

    ;
}
