<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de usuários</title>
<link rel="stylesheet" type="text/css" href="resources/css/cadastro.css">
<link rel="stylesheet" type="text/css" href="resources/css/menu.css">

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js" 
		integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" 
		crossorigin="anonymous"></script>
		
		 
</head>
<body>
	<div>
		<%@ include file="/menu.jsp"  %>	
	</div>
	<h1>Cadastro de usuário</h1>
	<h3>${msg}</h3>
	
	<form action="CadastrarUsuario" method="post" id="formUser" enctype="multipart/form-data" onsubmit="return validarDados() ? true : false">	
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Id:</td>
						<td><input type="text" readonly="readonly" id="id" name="id" value="${usuario.id}"> </td>
						
						<td>CEP:</td>
						<td><input style="width: 185px;" type="text" id="cep" name="cep" value="${usuario.cep}" size="9" maxlength="9" onblur="consultaCep();" placeholder="Informe o cep"></td>	
						
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado" size="2" value="${usuario.estado}"> </td>		
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" value="${usuario.nome}" placeholder="Digite seu nome"> </td>	
						
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" value="${usuario.rua}"> </td>			
					</tr>
					<tr>
						<td>Fone:</td>
						<td><input type="text" id="fone" name="telefone" value="${usuario.telefone}" placeholder="Digite o telefone"> </td>	
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" value="${usuario.numero}"> </td>				
					</tr>						
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" value="${usuario.login}" placeholder="Digite um login" maxlength="10"> </td>	
						
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade" value="${usuario.cidade}"> </td>				
					</tr>
					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha" value="${usuario.senha}" placeholder="Informe a senha" maxlength="10"> </td>
						
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro" value="${usuario.bairro}"> </td>
						
											
					</tr>
					<tr> 
						<td>Foto:</td>
						<td><input type="file" name="foto" value="foto"> <input type="text" style="display: none;"  id="fotoTemp" name="fotoTemp" value="${usuario.fotoBase64}"> <input type="text" style="display: none;"  id="fotoContentTemp" name="fotoContentTemp" value="${usuario.contentType}"> </td>	
						
						<td>Currículo:</td>
						<td><input type="file" name="curriculo" value="curriculo"> <input type="text" style="display: none;" id="curriculoTemp" name="curriculoTemp" value="${usuario.curriculoBase64}"> <input type="text" style="display: none;" id="curriculoContentTemp" name="curriculoContentTemp" value="${usuario.contentTypeCurriculo}"></td>					
					</tr>										
					<tr>
						<td> </td>
						<td>
							<input type="submit" value="Cadastrar" > 
							<input type="submit" value="Cancelar" onclick="cancelar()">
						</td>				
					</tr>	
				</table>
			</li>
		</ul>	
	</form>
	<div class="container">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
				<tr>
					<th>Foto</th>
					<th>Currículo</th>
					<th>Id</th>
					<th>Login</th>
					<th>Nome</th>
					<th>Fone</th>
					<th>Telefones</th>
					<th>Excluir</th>	
					<th>Editar</th>	
									
				</tr>
				<c:forEach items="${usuarios}" var="user">
					<tr>
						<!--<td> <img alt="Foto" src="${user.tempFotoUser}" width="32px" height="32px"> </td>-->
						<c:if test="${user.fotoBase64Miniatura != null}">
							<td><a href="CadastrarUsuario?acao=downloadFoto&id=${user.id}"><img alt="Foto" src='<c:out value="${user.fotoBase64Miniatura}"></c:out>' width="32px" height="32px"></a></td>
						</c:if>
						<c:if test="${user.fotoBase64Miniatura == null}">
							<td><img alt="FotoVazia" src="resources/img/SemFoto.png" width="32px" height="32px"></td>
						</c:if>
						
						<c:if test="${user.curriculoBase64 != null}">
							<td><a href="CadastrarUsuario?acao=downloadCurriculo&id=${user.id}"><img alt="Currículo" src="resources/img/pdf.png" width="32px" height="32px"></a></td>
						</c:if>
						<c:if test="${user.curriculoBase64 == null}">
							<td><img alt="Currículo" src="resources/img/SemCurriculo.png" width="32px" height="32px" onMouseOver="alert('Não possui currículo!')"></td>
						</c:if>
						
						<td> <c:out value="${user.id}"></c:out> </td>
						<td> <c:out value="${user.login}"></c:out> </td>
						<td> <c:out value="${user.nome}"></c:out> </td>
						<td> <c:out value="${user.telefone}"></c:out> </td>
						<td> <a href="TelefonesUsuariosServlet?acao=carregar&idUser=${user.id}"><img width="15px" height="15px" title="Telefones" alt="Telefones" src="resources/img/telefone.jpg"></a> </td>
						<td> <a href="CadastrarUsuario?acao=delete&id=${user.id}&login=${user.login}"><img width="15px" height="15px" title="Excluir" alt="Excluir" src="resources/img/excluir.png"> </a> </td>
						<td> <a href="CadastrarUsuario?acao=editar&id=${user.id}&login=${user.login}"><img width="15px" title="Editar" alt="Editar" src="resources/img/editar.png"></a> </td>
						
					</tr>		
				</c:forEach>	
		</table>
	</div>
	
	<script type="text/javascript">
		function validarDados(){
			var nome = document.getElementById("nome").value;
			var telefone = document.getElementById("fone").value;
			var login = document.getElementById("login").value;
			var senha = document.getElementById("senha").value;
			var cep = document.getElementById("cep").value;
			var numero = document.getElementById("numero").value;
			
			if(nome == ''){
				alert('Informe o nome!!!')
				return false;
			}else if(telefone == ''){
				alert('Informe o telefone!!!')
				return false;
			}else if(login == ''){
				alert('Informe o login!!!')				
				return false;
			}else if(senha == ''){
				alert('Informe a senha!!!')
				return false;
			}else if(cep == ''){
				alert('Informe o CEP!!!')
				return false;
			}else if(numero == ''){
				alert('Informe o número!!!')
				return false;
			}else {				
				return true;
			}			
		}
		
		
		function consultaCep() {

			//var cep = $("#cep").val();

			var cep = $("#cep").val().replace(/\D/g, '');

			if (cep != "") {
				var validacep = /^[0-9]{8}$/;

				if (validacep.test(cep)) {
					$.getJSON("https://viacep.com.br/ws/" + cep
							+ "/json/?callback=?", function(dados) {
						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#numero").val("");
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
						} //end if.
						else {
							//CEP pesquisado não foi encontrado.  
							limpaFormCep();
							alert("CEP não encontrado.");
						}
					});//fim getJSON	

				} else {
					limpaFormCep();
					alert("Cep inválido...")
				}
			} else {
				limpaFormCep();
			}
		}//fim consultaCep()

		function limpaFormCep() {
			$("#rua").val("");
			$("#numero").val("");
			$("#bairro").val("");
			$("#cidade").val("");
			$("#estado").val("");
		}
		
		function cancelar(){
			document.getElementById("formUser").action="CadastrarUsuario?acao=reset" ;
			document.getElementById("formUser").onsubmit="";
		}
	</script>
	
</body>
</html>