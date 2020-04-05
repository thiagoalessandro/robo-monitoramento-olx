package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioTipoLancamento {

	CREDITO("CREDITO"),
	DEBITO("DEBITO");

	private String description;

	DominioTipoLancamento(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
