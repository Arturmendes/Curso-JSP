package beans;

public class BeanProduto {
	
	private Long id;
	private String nome;
	private double quantidade;
	private double valor;
	private String valorEmTexto;
	
	
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
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getValorEmTexto() {
		String valorEmTexto = Double.toString(valor).replace('.', ',');
		return valorEmTexto;
		
		
	}
		

}
