package health.utils;

import health.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mai
 * Date: 2021-03-14
 * Time: 19:00
 */
public final class LoggerUtils {
    public static final Logger getLogger(Class c){
       return LoggerFactory.getLogger(c);
    }
}
