<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Insert title here</title>

</head>

<body>

User列表（${list.size()}）

<hr>
<c:forEach  var="user" items="${list}" >
    <p>
        ID：${user.id}
            登录名：${user.loginName}
        名称：${user.name}
    </p>
</c:forEach>


</body>

</html>