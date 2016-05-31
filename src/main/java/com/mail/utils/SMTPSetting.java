package com.mail.utils;

import java.io.Serializable;

/**
 * 发送者邮箱及协议设置
 * 
 * @author kanjc
 * @version 2.0, 2015年11月23日
 */
public class SMTPSetting implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 邮件显示名
	 */
	private String sendNickname;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 发送邮件服务器
	 */
	private String smtpHost;

	/**
	 * 端口
	 */
	private int port = 25;

	/**
	 * 默认协议
	 */
	private ProtocolEnum protocol = ProtocolEnum.NORMAL;

	/**
	 * 设置邮件昵称
	 * 
	 * @param sendNickname
	 * @return
	 * @see
	 */
	public SMTPSetting setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
		return this;
	}

	/**
	 * 设置发件人用户名
	 * 
	 * @param userName
	 * @return
	 * @see
	 */
	public SMTPSetting setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	/**
	 * 设置发件人邮件密码
	 * 
	 * @param password
	 * @return
	 * @see
	 */
	public SMTPSetting setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * 设置邮件服务器
	 * 
	 * @param smtpHost
	 * @return
	 * @see
	 */
	public SMTPSetting setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
		return this;
	}

	/**
	 * 设置服务器端口
	 * 
	 * @param port
	 *            端口，默认为25
	 * @return
	 * @see
	 */
	public SMTPSetting setPort(int port) {
		this.port = port;
		return this;
	}

	/**
	 * 设置协议类型
	 * 
	 * @param protocol
	 *            默认为smtp
	 * @return
	 * @exception SMTPSetting.Protocol
	 * @see
	 */
	public SMTPSetting setProtocol(ProtocolEnum protocol) {
		this.protocol = protocol;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public int getPort() {
		return port;
	}

	public ProtocolEnum getProtocol() {
		return protocol;
	}

	public String getSendNickname() {
		return sendNickname;
	}

	/**
	 * 协议类型
	 * 
	 * @version 2.0, 2015年11月23日
	 */
	public enum ProtocolEnum {
		NORMAL("smtp"), SSL("smtps"), TLS("smtp");

		private String value;

		private ProtocolEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
