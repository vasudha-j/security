package org.jboss.seam.security.annotations.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies a list of permission actions for a class
 *
 * @author Shane Bryzak
 */
@Target({TYPE})
@Documented
@Retention(RUNTIME)
@Inherited
public @interface Permissions {
    Permission[] value() default {};
}
