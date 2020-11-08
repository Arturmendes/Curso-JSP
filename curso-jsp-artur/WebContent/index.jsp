<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="resources/css/index.css">
</head>
<body>
	<div class="container">
		<div class="msg">${msg}</div>
	</div>
	<div class="containerForm">
		<div class="formulario">
			<form action="LoginServlet" method="post" autocomplete="on"
				class="form_login">
				<table>
					<tr>
						<td><label>Login:</label></td>
						<td><input type="text" id="login" name="login"
							value="${login}"></td>
					</tr>
					<tr>
						<td><label>Senha:</label></td>
						<td><input type="password" id="senha" name="senha"
							value="${senha}"></td>
					</tr>
					<tr>
						<td colspan="2"><button type="submit" value="Logar">Logar</button></td>
					</tr>
				</table>
			</form>
		</div>


	</div>

</body>
</html>