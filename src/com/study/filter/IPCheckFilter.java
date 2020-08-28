package com.study.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;

public class IPCheckFilter implements Filter {

	private Map<String, String> denyIPMap;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 초기화
		denyIPMap = new HashedMap();

		// 거부 아이피(짝궁)
		denyIPMap.put("192.168.20.47", "Critical");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String ip = request.getRemoteAddr();
		System.out.println(ip);
		if (denyIPMap.containsKey(ip) ){
			System.out.println("하이");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<h2>접속 거부</h2>");
			out.print(
					"현재 접속하신 IP" + ip+ "는 거부된 아이피입니다.\n" + "문의사항이 있으시면 전화: 042-719-8850으로 연락주세요");
		}else {
			System.out.println("하이2");
			chain.doFilter(request, response);
		}

	}

}
