package com.mail.utils;

import java.util.Arrays;

/**
 * 邮件发送类
 * 
 * @author kanjc
 * @version 2.0, 2015年11月25日
 */
public class MailSenderFactory {

	/**
	 * 同步发送邮件
	 * 
	 * @param setting
	 *            发送者信息设置
	 * @param message
	 *            邮件信息
	 * @return true：发送成功， false：发送失败
	 * @throws Exception
	 * @see
	 */
	public static boolean sendMail(SMTPSetting setting, MailMessage message) {
		return MailSender.sendMail(setting, message);
	}

	/**
	 * 同步发送邮件
	 * 
	 * @param userName
	 *            发送人邮箱用户名
	 * @param password
	 *            发送人邮箱密码
	 * @param smtpHost
	 *            发送SMTP服务器
	 * @param title
	 *            邮件标题
	 * @param content
	 *            邮件内容
	 * @param to
	 *            收件人(一个或多个)
	 * @return
	 * @see
	 */
	public static boolean sendMail(String userName, String password, String smtpHost, String title, String content, String... to) {
		SMTPSetting setting = new SMTPSetting().setUserName(userName).setPassword(password).setSmtpHost(smtpHost);
		MailMessage message = new MailMessage();
		message.setSubject(title);
		message.setBody(content);
		message.setTo(Arrays.asList(to));
		return MailSender.sendMail(setting, message);
	}

	/**
	 * 异步发送邮件
	 * 
	 * @param setting
	 *            发送者信息设置
	 * @param message
	 *            邮件信息
	 * @throws Exception
	 * @see
	 */
	public static void sendAsyncMail(SMTPSetting setting, MailMessage message) {
		MailSender.sendAsyncMail(setting, message);
	}

	/**
	 * 异步发送邮件
	 * 
	 * @param setting
	 *            发送者信息设置
	 * @param message
	 *            邮件信息
	 * @throws Exception
	 * @see
	 */
	public static void sendAsyncMail(String userName, String password, String smtpHost, String title, String content, String... to) {
		SMTPSetting setting = new SMTPSetting().setUserName(userName).setPassword(password).setSmtpHost(smtpHost);
		MailMessage message = new MailMessage();
		message.setSubject(title);
		message.setBody(content);
		message.setTo(Arrays.asList(to));
		MailSender.sendAsyncMail(setting, message);
	}

}
