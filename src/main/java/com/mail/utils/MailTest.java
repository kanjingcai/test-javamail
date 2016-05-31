package com.mail.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MailTest {

	public static void main(String[] args) {

		SMTPSetting setting = new SMTPSetting();
		setting.setUserName("kanjc@dh.com");
		setting.setPassword("123456");
		setting.setSendNickname("Hello World!");
		setting.setSmtpHost("smtp.exmail.qq.com");
		/*
		 * setting.setUserName("kanjingcai@163.com"); setting.s
		 * etPassword("19901025"); setting.setSendNickname("Hello World!");
		 * setting.setSmtpHost("smtp.163.com");
		 */
		// setting.setPort(465);
		setting.setProtocol(SMTPSetting.ProtocolEnum.NORMAL);

		MailMessage message = new MailMessage();
		Collection<String> to = new ArrayList<String>();
		to.add("haha@dh.com");
		message.setTo(to);
		// message.setContentTypeEnum(ContentTypeEnum.HTML);
		message.setBody("<html><body><a>呵呵</a> aaa</body></html>");
		message.setSubject("Hello Java，你好Java!");
		 System.out.println(MailSenderFactory.sendMail("kanjc@dh.com","123456","smtp.exmail.qq.com","标题", "<html><body><a>呵呵</a> aaa</body></html>", "haha@dh.com"));
	}
}
