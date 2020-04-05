package br.com.intelector.gerenciarcontapagar.domain;

import br.com.intelector.gerenciarcontapagar.exception.DominioException;

public enum DominioCartao {

	NUBANK("NUBANK"),
	ITAUGOLD("ITAUGOLD"),
	ITAUICONTA("ITAUICONTA");

	private String description;

	DominioCartao(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public static DominioCartao convertStringToEnum(String situacao) throws DominioException {
		switch (situacao) {
			case "NUBANK" :
				return DominioCartao.NUBANK;
			case "ITAUGOLD" :
				return DominioCartao.ITAUGOLD;
			case "ITAUICONTA" :
				return DominioCartao.ITAUICONTA;
			default:
				throw new DominioException("Cartão não encontrado!");
		}
	}
	
	
}
