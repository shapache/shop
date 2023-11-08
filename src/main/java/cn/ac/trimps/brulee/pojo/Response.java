package cn.ac.trimps.brulee.pojo;

import lombok.Data;

@Data
public class Response {

	int code = 0;
	String message = "";
	Object data = null;

	public Response(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static Response success() {
		return new Response(0, "", null);
	}

	public static Response success(String message) {
		return new Response(0, message, null);
	}

	public static Response success(Object data) {
		return new Response(0, "", data);
	}

	public static Response error(int code, String message) {
		return new Response(code, message,null);
	}

}
