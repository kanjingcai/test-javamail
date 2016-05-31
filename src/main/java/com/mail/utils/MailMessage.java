package com.mail.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.collections.CollectionUtils;

import com.dronline.common.framework.base.utils.EmptyUtil;

/**
 * 消息服务类
 * 
 * @author kanjc
 * @version 2.0, 2015年11月23日
 */
public class MailMessage implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 收件人列表
	 */
	private Collection<String> to;

	/**
	 * 抄送人列表
	 */
	private Collection<String> cc;

	/**
	 * 密送人列表
	 */
	private Collection<String> bcc;

	/**
	 * 日期
	 */
	private Date date;

	/**
	 * 邮件标题
	 */
	private String subject;

	/**
	 * 邮件正文
	 */
	private String body;

	/**
	 * 附件列表
	 */
	private List<String> filePath;

	/**
	 * 邮件内容类型
	 */
	private ContentTypeEnum contentTypeEnum = ContentTypeEnum.HTML; // 默认HTML邮件

	/**
	 * 优先级， 默认为中
	 */
	private int priority = PriorityEnum.MIDDLE.getCode();

	/**
	 * 设置收件人列表
	 * 
	 * @param to
	 * @see
	 */
	public void setTo(Collection<String> to) {
		this.to = to;
	}

	public Collection<String> getTo() {
		return to;
	}

	public Collection<String> getCc() {
		return cc;
	}

	/**
	 * 设置抄送人列表
	 * 
	 * @param cc
	 * @see
	 */
	public void setCc(Collection<String> cc) {
		this.cc = cc;
	}

	public Collection<String> getBcc() {
		return bcc;
	}

	/**
	 * 设置密送人列表
	 * 
	 * @param bcc
	 * @see
	 */
	public void setBcc(Collection<String> bcc) {
		this.bcc = bcc;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * 设置发送时间（默认系统时间）
	 * 
	 * @param date
	 * @see
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	/**
	 * 设置邮件标题
	 * 
	 * @param subject
	 * @see
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	/**
	 * 设置邮件内容
	 * 
	 * @param body
	 * @see
	 */
	public void setBody(String body) {
		this.body = body;
	}

	public int getPriority() {
		return priority;
	}

	/**
	 * 设置邮件优先级，默认为中（3）
	 * 
	 * @param priority
	 * @exception MailMessage.PriorityEnum
	 * @see
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ContentTypeEnum getContentTypeEnum() {
		return contentTypeEnum;
	}

	/**
	 * 邮件内容类型
	 * 
	 * @exception MailMessage.MailContentTypeEnum
	 * @param contentTypeEnum
	 * @see
	 */
	public void setContentTypeEnum(ContentTypeEnum contentTypeEnum) {
		this.contentTypeEnum = contentTypeEnum;
	}

	public List<String> getFilePath() {
		return filePath;
	}

	/**
	 * 设置文件附件
	 * 
	 * @param filePath  附件路径
	 * @see
	 */
	public void setFilePath(List<String> filePath) {
		this.filePath = filePath;
	}

	/**
	 * 封装邮件内容，可自定义
	 * 
	 * @return
	 * @throws Exception
	 * @see
	 */
	protected Message toMailMessage() throws Exception {
		Message message = new MimeMessage(MailSender.getNormalsmtpsession()); // 默认发送服务器
		message.setSubject(MimeUtility.encodeWord(this.subject, "UTF-8", "Q")); // 邮件标题
		message.setSentDate(date != null ? date : new Date()); // 发送时间
		message.setHeader("X-Priority", this.priority + ""); // 优先级
		message.setRecipients(Message.RecipientType.TO, parseArray(to)); // 收件人
		message.setRecipients(Message.RecipientType.CC, parseArray(cc)); // 抄送
		message.setRecipients(Message.RecipientType.BCC, parseArray(bcc)); // 密送

		Multipart multipart = new MimeMultipart();
		MimeBodyPart bodyPart = new MimeBodyPart();
		boolean contentType = ContentTypeEnum.HTML.equals(ContentTypeEnum.HTML);
		bodyPart.setContent(this.body, contentType ? ContentTypeEnum.HTML.getValue() : ContentTypeEnum.TEXT.getValue());
		if (ContentTypeEnum.HTML.equals(ContentTypeEnum.HTML)) { // 设置邮件内容格式
			bodyPart.setContent(this.body, this.contentTypeEnum.value);
		} else {
			bodyPart.setContent(this.body, this.contentTypeEnum.value);
		}
		multipart.addBodyPart(bodyPart);

		/**
		 * 判断是否存在附件
		 */
		if (EmptyUtil.isNotEmpty(filePath)) {
			for (String filename : filePath) {
				MimeBodyPart part = new MimeBodyPart();
				DataSource fileDataSource = new FileDataSource(filename);
				if (EmptyUtil.isEmpty(fileDataSource))
					continue;
				part.setDataHandler(new DataHandler(fileDataSource));
				part.setFileName(MimeUtility.encodeWord(fileDataSource.getName(), "GB2312", null));
				multipart.addBodyPart(part);
			}
		}

		message.setContent(multipart);
		return message;
	}

	/**
	 * 转成数组
	 * 
	 * @param mailAddress
	 *            邮件列表
	 * @return 邮件列表数组
	 * @throws AddressException
	 * @see
	 */
	protected InternetAddress[] parseArray(Collection<String> mailAddress) throws AddressException {
		if (CollectionUtils.isEmpty(mailAddress)) {
			return null;
		}
		InternetAddress address[] = new InternetAddress[mailAddress.size()];
		int i = 0;
		for (String str : mailAddress) {
			address[i] = new InternetAddress(str);
			i++;
		}
		return address;
	}

	/**
	 * 邮件优先级
	 * 
	 * @author kanjc
	 * @version 2.0, 2015年11月23日
	 * @since com.dongrongonline 2.0
	 */
	public enum PriorityEnum {

		HIGH(1, "高"), MIDDLE(3, "中"), LOW(5, "低");

		private final int code;

		private final String value;

		public Integer getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		private PriorityEnum(Integer code, String value) {
			this.code = code;
			this.value = value;
		}
	}

	/**
	 * 邮件内容类型
	 * 
	 * @author kanjc
	 * @version 2.0, 2015年11月23日
	 * @since com.dongrongonline 2.0
	 */
	public enum ContentTypeEnum {
		HTML("text/html; charset=utf-8"), TEXT("text/plain; charset=utf-8");

		private final String value;

		private ContentTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
