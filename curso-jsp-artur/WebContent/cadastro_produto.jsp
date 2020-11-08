<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="resources/css/cadastro_produto.css">
<link rel="stylesheet" type="text/css" href="resources/css/menu.css">

<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery.maskMoney.min.js" type="text/javascript"></script>

<title>Cadastro de produtos</title>

</head>
<body>
	<div>
		<%@ include file="/menu.jsp"%>
	</div>

	<h1 id="Titulo">Cadastro de Produtos</h1>
	<h3>${msg}</h3>

	<form class="form1" action="ProdutoServlet" method="post" id="formProduto"
		onsubmit="return validarDados()? true : false">	
		<table>
			<tr>
				<td>Código:</td>
				<td><input type="text" readonly="readonly" id="id" name="id"
					value="${produto.id}"></td>
			</tr>
			<tr>
				<td>Nome:</td>
				<td><input type="text" id="nome" name="nome"
					value="${produto.nome}"></td>
			</tr>
			<tr>
				<td>Quantidade:</td>
				<td><input type="number" id="qtd" name="quantidade" maxlength="10"
					value="${produto.quantidade}"></td>
			</tr>
			<tr>
				<td>Valor R$:</td>				
				<td><input maxlength="12" type="text" id="valor" name="valor" data-thousands="." data-precision="2" data-decimal="," value="${produto.valorEmTexto}"/></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Cadastrar"> <input
					type="submit" value="Cancelar"
					onclick="document.getElementById('formProduto').action='ProdutoServlet?acao=reset' "></td>
			</tr>
		</table>
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>Lista de produtos cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>valor</th>
				<th>Excluir</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td><c:out value="${produto.id}"></c:out></td>
					<td><c:out value="${produto.nome}"></c:out></td>
					<td><c:out value="${produto.quantidade}"></c:out></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${produto.valor}" /></td>
					<td><a
						href="ProdutoServlet?acao=delete&id=${produto.id}&nome=${produto.nome}"><img
							width="15px" height="15px" title="Excluir" alt="Excluir"
							src="resources/img/excluir.png"> </a></td>
					<td><a
						href="ProdutoServlet?acao=editar&id=${produto.id}&nome=${produto.nome}"><img
							width="15px" title="Editar" alt="Editar"
							src="resources/img/editar.png"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>


	<script type="text/javascript">
		
	
	
		  $(function() {
		    $('#valor').maskMoney();
		  })
	
	
		function validarDados(){
			
			var nome = document.getElementById("nome").value;
			var quantidade = document.getElementById("qtd").value;
			var valor = document.getElementById("valor").value;
			
			if(nome == ''){
				alert('Informe o nome!!!')
				return false;
			}else if(quantidade == ''){
				alert('Informe a quantidade!!!')
				return false;
			}else if(valor == ''){
				alert('Informe o valor!!!')
				return false;
			}else {				
				return true;
			}
			
		}
	</script>
</body>
</html>