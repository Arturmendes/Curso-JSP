package beans;

public class BeanUsuario {

	private Long id;
	private String nome;
	//private BeanTelefone[] telefones;
	private String telefone;
	private String cep;
	private String rua;
	private String bairro;
	private String numero;
	private String cidade;
	private String estado;
	private String login;
	private String senha;
	
	private String fotoBase64;
	private String fotoBase64Miniatura;
	private String contentType;
	private String curriculoBase64;
	private String contentTypeCurriculo;
	
	private String tempFotoUser;
	private String tempCurriculoUser;
	
	public String getTempFotoUser() {
		tempFotoUser = "data:" + contentType + ";base64," + fotoBase64;
		
		return tempFotoUser;
	}
	
	public String getTempCurriculoUser() {
		
		tempCurriculoUser = "data:" + contentTypeCurriculo + ";base64," + curriculoBase64;
		return tempCurriculoUser;
	}
	
	
	public String getCurriculoBase64() {
		return curriculoBase64;
	}

	public void setCurriculoBase64(String curriculoBase64) {
		this.curriculoBase64 = curriculoBase64;
	}

	public String getContentTypeCurriculo() {
		return contentTypeCurriculo;
	}

	public void setContentTypeCurriculo(String contentTypeCurriculo) {
		this.contentTypeCurriculo = contentTypeCurriculo;
	}

	







	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}	
	
	public String getFotoBase64() {
		return fotoBase64;
	}
	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}
	
	
	public String getFotoBase64Miniatura() {
		return fotoBase64Miniatura;
	}

	public void setFotoBase64Miniatura(String fotoBase64Miniatura) {
		this.fotoBase64Miniatura = fotoBase64Miniatura;
	}

	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		String s = "Usuário: \n";
		s += "Id: " + id + "\n";
		s += "Nome: " + nome + "\n";
		s += "Telefone: " + telefone + "\n";
		s += "CEP: " + cep + "\n";
		s += "Rua: " + rua + "\n";
		s += "Bairro: " + bairro + "\n";
		s += "Número: " + numero + "\n";
		s += "Cidade: " + cidade + "\n";
		s += "UF: " + estado + "\n";
		s += "Login: " + login + "\n";
		s += "Senha: " + senha + "\n";
		s += "Foto base 64: " + fotoBase64 + "\n";
		s += "Content Type: " + contentType + "\n";
				
				
		return s;
		
	}
}
