package com.etf.test.imageLab.filters;

import java.lang.annotation.*;

/**
 * Created by patrick on 30/05/17.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
public @interface Category {
    public String value();
}