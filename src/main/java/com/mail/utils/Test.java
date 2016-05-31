package com.mail.utils;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.methods.HttpPost;


public class Test {

	public static void main(String[] args) {
		HttpClient client = new HttpClient();
		DefaultMethodRetryHandler retryhandler = new DefaultMethodRetryHandler();
		retryhandler.setRequestSentRetryEnabled(false);
		retryhandler.setRetryCount(3);
		GetMethod method = new GetMethod();
		method.setMethodRetryHandler(retryhandler);
		try {
			client.executeMethod(method);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
