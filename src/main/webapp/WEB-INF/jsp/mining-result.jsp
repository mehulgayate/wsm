<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mining Result</title>
</head>
<body><div>
<strong>Mining From Clustered data  : </strong><br>
<strong>Time Taken : </strong>${clustredtakenTime}<br>
<textarea style="width: 800px; min-height: 500px;">${clusteredXmlResult}</textarea></div>


<strong>Mining From Non-Clustered data  : </strong><br>
<strong>Time Taken : </strong>${nonClustredtakenTime}<br>
<textarea style="width: 800px; min-height: 500px;">${nonClusteredData}</textarea></div>

</body>
</html>