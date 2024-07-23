<#import "spring.ftl" as spring />
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
<link href="<@spring.url 'assets/library/bootstrap/css/bootstrap.min.css' />"
		  rel="stylesheet" >
<link href="<@spring.url 'assets/css/custom.css' />"
		  rel="stylesheet" >
<link href="<@spring.url 'assets/css/responsive.css' />"
		  rel="stylesheet" >
</head>
<body>
<#include "Header.ftl">
<main>
	 <section>
		<div class="container">
			<div class="row">
				<div class="col-md-3 col-sm-3"></div>
				<div class="col-md-4 col-sm-4 login">
					<h1 class="heading" style="text-decoration: underline">Login Page</h1>
					<form action="/login" method="POST">
					<#if errorMessage ??>
						<span style="color:red">${errorMessage!""}</span>
					</#if>
						<div class="form-group label-name">
						Email :<input type="email" placeholder="Enter Email-ID" id="username" class="form-control" name="username" required>
						</div>
						<div class="form-group label-name">
						Password:<input type="password" placeholder="Enter Password" id="password"  class="form-control" name="password" required>
						</div>
						<div class="form-group">
						<input type="submit" class="form-control login-btn" id="submit" value="Login">
						</div>
						<div class="a-tag">
							<a href="forgotpwd" class="label-name" style="text-decoration: underline">Forgot Password</a>
							<a href="registration" class="user-gap label-name" style="text-decoration: underline">New User?SignUp</a>
						</div>
					</form>
				 </div>
				 <div class="col-md-5 col-sm-5"></div>	 
			</div>
		</div>
	</section>
</main>
<#include "Footer.ftl">
</body>
</html>