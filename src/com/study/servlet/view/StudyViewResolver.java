package com.study.servlet.view;

public class StudyViewResolver {
	private String prefix = "";
	private String suffix = "";

	/**
	 * 뷰의 이름을 기반으로 적절한 View 객체를 리턴한다. <br>
	 * 
	 * @param viewName
	 * @return View
	 */
	public View resolveViewName(String viewName) {
		View view = null;
		if (viewName == null) {
			view = new RequestToViewNameTranslator(this);
		} else if (viewName.equals("jsonView")) {
			view = new JsonView();
		} else if (viewName.startsWith("redirect:")) {
			view = new RedirectView(viewName);
		} else {
			view = new JstlView(this, viewName);
		}
		return view;
	}
	// 맴버변수 get/set 생성 (단, setter 는 Method Chaining 으로 구성 )

	public String getPrefix() {
		return prefix;
	}

	public StudyViewResolver setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	public String getSuffix() {
		return suffix;
	}

	public StudyViewResolver setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

}
