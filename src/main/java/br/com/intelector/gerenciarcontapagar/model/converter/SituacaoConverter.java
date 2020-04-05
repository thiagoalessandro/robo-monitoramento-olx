package br.com.intelector.gerenciarcontapagar.model.converter;

import br.com.intelector.gerenciarcontapagar.domain.DominioSituacaoRegistro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoConverter implements AttributeConverter<DominioSituacaoRegistro, String> {

	@Override
	public String convertToDatabaseColumn(DominioSituacaoRegistro attribute) {
		return attribute.getDescription();
	}

	@Override
	public DominioSituacaoRegistro convertToEntityAttribute(String dbData) {
		return DominioSituacaoRegistro.convertStringToEnum(dbData);
	}

}
