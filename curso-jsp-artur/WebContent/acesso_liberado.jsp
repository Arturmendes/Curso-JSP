<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Acesso Liberado</title>
</head>
<body>
	<div>
		<%@ include file="/menu.jsp"%>
	</div>

	<h3>Seja bem vindo ao sistema em jsp</h3>
	<%= application.getInitParameter("estado") %>
	<br />
	<br />

	<a href="CadastrarUsuario?acao=carregar" ><img width="80px" height="80px" title="Cadastro Usuário" alt="Cadastro Usuário" src="resources/img/icone_isabela_usuarios.png"> </a>
	<a href="ProdutoServlet?acao=carregar" ><img width="80px" height="80px" title="Cadastro Produto" alt="Cadastro Produto" src="resources/img/icone_isabela_produtos.png"> </a>


</body>
</html>