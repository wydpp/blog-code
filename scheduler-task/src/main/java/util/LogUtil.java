package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dpp
 * @date 2025/1/23
 * @Description
 */
public class LogUtil {

    public static void log(String msg) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(simpleDateFormat.format(new Date()) + ":" + msg);
    }
}
