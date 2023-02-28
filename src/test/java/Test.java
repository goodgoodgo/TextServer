import org.textin.util.MailUtils;
import org.textin.util.StringUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:25
 */
public class Test {
    public static void main(String[] args) {
        MailUtils.sendEmail(StringUtil.generateCode(),"1771662778@qq.com");
    }
}
