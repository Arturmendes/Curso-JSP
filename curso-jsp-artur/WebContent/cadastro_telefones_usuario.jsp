<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Telefones</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/cadastro_telefone_usuario.css">
<link rel="stylesheet" type="text/css" href="resources/css/menu.css">

</head>
<body>
	<div>
		<%@ include file="/menu.jsp"%>
	</div>
	<h1>Telefones</h1>
	<h3>${msg}</h3>

	<form action="TelefonesUsuariosServlet" method="post"
		id="formTefonesUsuario" onsubmit="return validarDados() ? true : false" >
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Id Usuario:</td>
						<td><input type="text" readonly="readonly" id="idUser"
							name="idUser" value="${usuarioSessao.id}" ></td>

						<td>Id:</td>
						<td><input type="text" readonly="readonly" id="idTelefone"
							name="idTelefone" value="${telefone.id}" ></td>
					</tr>
					<tr>
						<td>Nome Usuario:</td>
						<td><input type="text" id="nomeUsuario" name="nomeUsuario"
							value="${usuario.nome}" readonly="readonly"></td>
							
						<td>Tipo</td>
						<td>
							<select id="tipo" name="tipo" >
								<option>Casa</option>
								<option>Contato</option>
								<option>Celular</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Login Usuario:</td>
						<td><input type="text" id="loginUsuario" name="login"
							value="${usuario.login}" readonly="readonly"></td>
							
						<td>Número:</td>
						<td><input type="text" id="numeroTelefone" name="numeroTelefone"
							value="${telefone.numero}" ></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="Cadastrar"> 
							<input type="submit" value="Cancelar" onclick="cancelar()">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>Telefones Cadastrados</caption>
			<tr>
				<th>Id Usuário</th>
				<th>Nome Usuário</th>
				<th>Login do Usuário</th>
				<th>Id</th>
				<th>Tipo</th>
				<th>Número</th>
				<th>Excluir</th>
				<th>Editar</th>

			</tr>			
				<c:forEach items="${telefones}" var="fone">
					<tr>
						<td><c:out value="${usuarioSessao.id}"></c:out></td>
						<td><c:out value="${usuarioSessao.nome}"></c:out></td>
						<td><c:out value="${usuarioSessao.login}"></c:out></td>
						<td><c:out value="${fone.id}"></c:out></td>
						<td><c:out value="${fone.tipo}"></c:out></td>
						<td><c:out value="${fone.numero}"></c:out></td>
						<td><a
							href="TelefonesUsuariosServlet?acao=delete&idFone=${fone.id}"><img
								width="15px" height="15px" title="Excluir" alt="Excluir"
								src="resources/img/excluir.png"> </a></td>
						<td><a
							href="TelefonesUsuariosServlet?acao=editar&idFone=${fone.id}"><img
								width="15px" title="Editar" alt="Editar"
								src="resources/img/editar.png"></a></td>
					</tr>
				</c:forEach>			
		</table>
	</div>

	<script type="text/javascript">
		function validarDados(){
			var numero = document.getElementById("numeroTelefone").value;
			var tipo = document.getElementById("tipo").value;
			
			
			if(numero == ''){
				alert('Informe o número')
				return false;			
			}else {				
				return true;
			}
			
			
		}		
		function cancelar(){
			document.getElementById("formTefonesUsuario").action="TelefonesUsuariosServlet?acao=reset";
			document.getElementById("formTefonesUsuario").onsubmit="";
		}
		
	</script>

</body>
</html>