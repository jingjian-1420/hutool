package com.xiaoleilu.hutool.http;

import java.io.IOException;
import java.io.InputStream;

import com.xiaoleilu.hutool.CharsetUtil;
import com.xiaoleilu.hutool.StrUtil;
import com.xiaoleilu.hutool.exceptions.HttpException;

/**
 * Http响应类
 * 
 * @author Looly
 *
 */
public class HttpResponse extends HttpBase<HttpResponse> {

	/**
	 * 读取响应信息
	 * 
	 * @param httpConnection Http连接对象
	 * @return HttpResponse
	 */
	public static HttpResponse readResponse(HttpConnection httpConnection) {
		final HttpResponse httpResponse = new HttpResponse();
		
		try {
			httpResponse.status = httpConnection.responseCode();
			httpResponse.headers =  httpConnection.headers();
			httpResponse.charset = httpConnection.charset();
			httpResponse.readBody(httpConnection.getInputStream());
		} catch (IOException e) {
			throw new HttpException(e.getMessage(), e);
		}
		
		return httpResponse;
	}

	/** 响应状态码 */
	private int status;

	public HttpResponse() {
	}

	/**
	 * 获取状态码
	 * 
	 * @return 状态码
	 */
	public int getStatus() {
		return status;
	}
	
	// ---------------------------------------------------------------- Body start
	/**
	 * 获取响应流字节码
	 * @return byte[]
	 */
	public byte[] bodyBytes() {
		if (body == null) {
			return null;
		}
		return StrUtil.bytes(body, charset);
	}

	/**
	 * 获取响应主体
	 * @return String
	 */
	public String body() {
		return body;
	}
	// ---------------------------------------------------------------- Body end
	
	// ---------------------------------------------------------------- Private method start
	/**
	 * 读取主体
	 * @param in 输入流
	 * @return 自身
	 * @throws IOException
	 */
	private void  readBody(InputStream in) throws IOException{
		this.body = HttpUtil.getString(in, CharsetUtil.UTF_8, charset == null);
	}
	// ---------------------------------------------------------------- Private method end
}
