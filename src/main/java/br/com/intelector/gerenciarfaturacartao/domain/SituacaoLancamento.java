package br.com.intelector.gerenciarfaturacartao.domain;

public enum SituacaoLancamento {

	PENDENTE("PENDENTE"),
	ULTIMA("ULTIMA"),
	PAGO("PAGO");

	private String description;

	SituacaoLancamento(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
