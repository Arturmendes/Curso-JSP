package beans;

public class BeanTelefone {

	private Long id;
	private String tipo;
	private String numero;
	private Long idUsuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		String s = "Telefone: ";
		s += "Id: " + id + "\n";
		s += "Tipo: " + tipo + "\n";
		s += "Número: " + numero + "\n";
		s += "Id Usuário: " + idUsuario;

		return s;
	}

}
