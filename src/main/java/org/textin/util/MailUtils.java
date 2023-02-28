package org.textin.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-28 15:13
 */
@Slf4j
public class MailUtils {
    public static void sendEmail(String code,String email) {
        HtmlEmail send = new HtmlEmail();//创建一个HtmlEmail实例对象
        // 获取随机验证码
        String resultCode = code;
        try {
            send.setHostName("smtp.qq.com");
            send.setAuthentication("913809758@qq.com", "lnmpcxkstanwbbaj"); //第一个参数是发送者的QQEamil邮箱   第二个参数是刚刚获取的授权码

            send.setFrom("913809758@qq.com", "玛卡巴卡有限公司");//发送人的邮箱为自己的，用户名可以随便填  记得是自己的邮箱不是qq
            send.setSSLOnConnect(true); //开启SSL加密
            send.setCharset("utf-8");
            send.addTo(email);  //设置收件人    email为你要发送给谁的邮箱账户
            send.setSubject("验证码"); //邮箱标题
            send.setMsg("记账APP!<font color='red'>您的验证码:</font>   " + resultCode + " ，五分钟后失效"); //Eamil发送的内容
            send.send();  //发送
            log.info(email+"短信发送成功");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
