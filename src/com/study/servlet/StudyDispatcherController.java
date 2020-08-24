package com.study.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet.handler.UrlHandlerMapping;

public class StudyDispatcherController extends HttpServlet {
	private UrlHandlerMapping handlerMapping;

	@Override
	public void init() throws ServletException {
		// 서블릿의 초기화 메서드(init)에서 설정 프로퍼티를 읽고 HandlerMapping 객체를 생성한다.
		String contextConfigLocation = getInitParameter("contextConfigLocation");
		try {
			handlerMapping = new UrlHandlerMapping(getServletContext(), contextConfigLocation);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	} // init

	// 1. 클라이언트의 요청을 처리하려면 (service, doGet, doPost 등) 재정의 한다.
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청 처리 전 공통적인 기능이 필요하면 기술한다.( or Filter)
		req.setCharacterEncoding("utf-8");
		// 2. 요청을 분석한다.( 명령 파라미터 or URI )
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		System.out.printf("요청 URI = %s\n", uri);
		try {
			String viewPage = null;
			IController controller = null;
			controller = handlerMapping.getHandler(uri);
			if (controller != null) {
				// 3. 요청에 따른 기능 수행
				// 4. 기능 수행에 따른 결과(모델)를 속성에 저장한다.
				// 각각의 컨트롤(커맨드) 객체는 3., 4. 의 기능을 위임 받아 수행하고 뷰정보를 리턴한다.
				viewPage = controller.process(req, resp);

				// 5. 알맞은 뷰로 이동 (forward or redirect)
				if (viewPage != null) {
					if (viewPage.startsWith("redirect:")) {
						// 아래 코드는 Redirect 요청이 절대경로 형식으로 작성되었다고 가정함
						resp.sendRedirect(req.getContextPath() + viewPage.substring("redirect:".length()));
					} else {
						// RequestDispatcher는 인클루드, 포워드를 전담하는 객체
						RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
						dispatcher.forward(req, resp);
					}
				}
				// 현재 뷰페이지가 널인 경우 처리 안함 (요청 URI 기반 뷰처리 or 예외 처리)
			} else {
				// controller가 널이면 요청에 대한 정보가 없는 것이므로
				System.out.printf("uri=[%s] 에 해당하는 컨트롤러가 존재하지 않습니다.", uri);
				resp.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
			}
		} catch (Exception e) {
			e.printStackTrace();
			// resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			throw new ServletException(e);
		}
	} // service
}
