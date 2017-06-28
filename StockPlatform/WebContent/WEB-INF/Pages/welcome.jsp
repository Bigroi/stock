<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome page</title>
</head>
<body>
   <p> WELCOME !!! ${user.login}</p>
   <p><a href="LoginPage.spr">LOGIN</a> </p>
   <p><a href="RegistrationPage.spr">Registration</a> </p>
   <p><a href="AccounPageAuth.spr">Account</a> </p>
   
    <ul>
    	<li><a href="ProductListPage.spr">Proluct list</a>
    		<form action="ProductForm.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="Add product"></p>
			</form>
		</li>
        <li><a href="MyLotListAuth.spr">My lots list</a>
        	<form action="LotFormAuth.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="Add lot"></p>	
			</form>
        </li>
        <li><a href="MyTenderListAuth.spr">My tenders list</a>
        	<form action="TenderFormAuth.spr">
				<input type="hidden" name="id" value="-1" />
				<p><input type="submit" value="Add tender"></p>
			</form>
        </li>
	</ul>
</body>
</html>