package com.mail.utils;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件发送类
 * 
 * @author kanjc
 * @version 2.0, 2015年11月23日
 */
public class MailSender {

	private static Logger logger = LoggerFactory.getLogger(MailSender.class);
	private static Executor executor = Executors.newCachedThreadPool();

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
		logger.info("[*********收到同步邮件发送请求************]");
		return request(setting, message);
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
	public static void sendAsyncMail(final SMTPSetting setting, final MailMessage message) {
		logger.info("[*********收到异步邮件发送请求************]");
		executor.execute(new Runnable() {
			@Override
			public void run() {
				request(setting, message);
			}
		});
	}

	private static boolean request(SMTPSetting setting, MailMessage message) {
		logger.info("[**********邮件发送开始**********]");
		long start = System.currentTimeMillis();
		try {
			Message sendMessage = message.toMailMessage();
			sendMessage.setFrom(new InternetAddress(setting.getUserName(), setting.getSendNickname()));
			sendMessage.saveChanges();
			Transport transport = mailSenderSession(setting).getTransport(setting.getProtocol().getValue());
			transport.connect(setting.getSmtpHost(), setting.getPort(), setting.getUserName(), setting.getPassword());
			transport.sendMessage(sendMessage, sendMessage.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[**********邮件发送异常： " + e + "**********]");
			return Boolean.FALSE;
		}
		logger.info("[**********邮件发送结束，耗时：" + (System.currentTimeMillis() - start) + "**********]");
		return Boolean.TRUE;
	}

	private static final Session normalSMTPSession = Session.getInstance(new Properties());; // 普通邮件发送
	private static final Session sslSMTPSession = Session.getInstance(new Properties()); // SSL加密方式的邮件发送
	private static final Session tlsSMTPSession = Session.getInstance(new Properties()); // TLS加密方式的邮件发送

	static {
		normalSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		normalSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");

		sslSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		sslSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");

		tlsSMTPSession.getProperties().setProperty("mail.smtp.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtps.auth", "true");
		tlsSMTPSession.getProperties().setProperty("mail.smtp.starttls.enable", "true");
	}

	/**
	 * 邮件协议
	 */
	private static Session mailSenderSession(SMTPSetting setting) {
		if (SMTPSetting.ProtocolEnum.SSL.equals(setting.getProtocol())) {
			return sslSMTPSession;
		}
		if (SMTPSetting.ProtocolEnum.TLS.equals(setting.getProtocol())) {
			return tlsSMTPSession;
		}
		return normalSMTPSession;
	}

	public static Session getNormalsmtpsession() {
		return normalSMTPSession;
	}

	public static Session getSslsmtpsession() {
		return sslSMTPSession;
	}

	public static Session getTlssmtpsession() {
		return tlsSMTPSession;
	}

}
