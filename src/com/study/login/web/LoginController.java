package com.study.login.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.common.util.CookieUtils;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.service.ILoginService;
import com.study.login.service.LoginServiceImpl;
import com.study.login.vo.UserVO;
import com.study.servlet.IController;

public class LoginController implements IController {

	private ILoginService loginService = new LoginServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 요청 메서드가 GET: login 화면, POST: loginCheck를 할거임
		if ("GET".equals(req.getMethod())) {
			return "/WEB-INF/views/login/login.jsp";
		} else if ("POST".equals(req.getMethod())) {
			// 로그인 체크
			UserVO vo = new UserVO();
			vo.setUserId(req.getParameter("userId"));
			vo.setUserPass(req.getParameter("userPass"));

			try {
				UserVO userVO = loginService.loginCheck(vo);
				HttpSession session = req.getSession();

				String remember = req.getParameter("rememberMe");
				if ("Y".equals(remember)) {
					Cookie cookie = CookieUtils.createCookie("SAVE_ID", vo.getUserId(), "/", 60 * 60 * 24 * 31);
					resp.addCookie(cookie);
				} else {
					resp.addCookie(CookieUtils.createCookie("SAVE_ID", "", "/", 0));
				}
				// 현재 사용자 정보(UserVO)를 세션에 저장
				System.out.println("세션에 정보 저장: " + userVO);
				session.setAttribute("USER_INFO", userVO); // key: USER_INFO
				return "redirect:/";
			} catch (BizNotFoundException | BizPasswordNotMatchedException e) {
				e.printStackTrace();
				ResultMessageVO message = new ResultMessageVO();
				message.setResult(false).setTitle("로그인 실패").setMessage("회원이 존재하지 않거나 비밀번호가 틀립니다.");
				req.setAttribute("messageVO", message);
				return "/WEB-INF/view/common/message.jsp";
			}
		} else {
			// GET / POST 아니면 에러
			throw new ServletException("지원하지 않는 메서드 요청입니다.");
		}
	}

}
