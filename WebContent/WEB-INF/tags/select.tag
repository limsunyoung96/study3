<%@tag import="com.sun.xml.internal.ws.api.ha.StickyFeature"%>
<%@tag import="java.util.Map"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="items" required="true" type="java.util.Collection"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="itemLabel" required="true"%>
<%@ attribute name="itemValue" required="true"%>
<%@ attribute name="value"%>
<%@ tag dynamic-attributes="attrMap"%>

<%
	Map<String, String> map = (Map) jspContext.getAttribute("attrMap");
	String strAttr = "";
	for (Map.Entry<String, String> en : map.entrySet()) {
	strAttr += " " + en.getKey() + "=\"" + en.getValue() + "\"";
	}
%>
<!-- 아래 코드를 위 코드로 변경하여 처리 해 주세요
	<c:forEach items="${attrMap}" var="attr"> ${attr.key}="${attr.value}"</c:forEach>
  -->

<select name="${name}"
	<c:forEach items="${attrMap}" var="attr"> ${attr.key}="${attr.value}"</c:forEach>>
	<jsp:doBody/>
	<c:forEach items="${items}" var="code">
		<option value="${code[itemValue]}"
			${code[itemValue] eq value ? "selected='selected'" : ""}>${code[itemLabel]}
		</option>
	</c:forEach>
</select>

