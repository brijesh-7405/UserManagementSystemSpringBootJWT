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
		<nav class="navbar-fixed-top">
			<div class="container-fluid default-header">
			    <div class="row">
				   <div class="col-md-10 col-sm-10 set-data"><p class="userheading"><i>User Management System</i></p></div>
				   <#if !USER??>
				   		<div class="col-md-1 col-sm-1 set-data"><a href="index" class="header-tag">Home</a></div>
				   		<div class="col-md-1 col-sm-1 set-data"><a href="registration" class="header-tag">Signup</a></div>
				   <#else>
				   		<div class="col-md-1 col-sm-1 set-data"></div>
				   		<div class="col-md-1 col-sm-1 set-data"><a href="logOut" class="btn btn-danger">LogOut</a></div>
				   </#if>
				</div>
				</div>
		</nav>
</body>
</html>