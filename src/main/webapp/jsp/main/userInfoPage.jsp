<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>User Info</title>
    <jsp:include page="../menu.jsp"/>
</head>
<body>

<h2>User Info Page</h2>

<b>${user.infoGet()}</b>

</body>
</html>
