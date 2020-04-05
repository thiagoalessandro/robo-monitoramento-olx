package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioSituacaoLancamento {

	PENDENTE("PENDENTE"),
	PAGO("PAGO");

	private String description;

	DominioSituacaoLancamento(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
