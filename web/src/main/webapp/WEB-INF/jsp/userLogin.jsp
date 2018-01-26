<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Titl1e</title>
</head>
<body>
filterChainDefinitionMap.put("/userLogin", "anon");
filterChainDefinitionMap.put("/toLogin", "anon");
<form action="/toLogin" method="post">
    <input type="text" name="username" value="">
    <input type="text" name="password" value="">
     <input type="submit" value="登录">
</form>
</body>
</html>
