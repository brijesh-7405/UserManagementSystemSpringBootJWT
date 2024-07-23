<#import "spring.ftl" as spring />
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="<@spring.url 'assets/library/bootstrap/css/bootstrap.min.css' />"
		  rel="stylesheet" >
<link href="<@spring.url 'assets/css/custom.css' />"
		  rel="stylesheet" >
<link href="<@spring.url 'assets/css/responsive.css' />"
		  rel="stylesheet" >
</head>
<body>
<#include "Header.ftl">
<#if USER ??>
<<h3 class="title-name">Welcome ${USER.firstname} ${USER.lastname}</h3>
</#if>
<span class="buttons">
   <a href="userDetails" class="btn button-dg">EditProfile</a>
 </span>
<#include "Footer.ftl"> 
</body>
</html>