package com.sco.pokedex.back.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.filter.FilterFactory;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class IntegerFilterDescDto implements FilterDesc {

    @JsonProperty("type")
    public final FilterTypeEnum type = FilterTypeEnum.INTEGER;

    @JsonProperty("propertyName")
    private String propertyName;

    @JsonProperty("operationType")
    private OperationTypeEnum operationType;

    @JsonProperty("value")
    private int value;

    public IntegerFilterDescDto() {}

    public IntegerFilterDescDto(String propertyName, OperationTypeEnum operationType, int value) {
        this.propertyName = propertyName;
        this.operationType = operationType;
        this.value = value;
    }

    @Override
    public FilterTypeEnum getType() {
        return type;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Predicate<PokemonDto> getFilter(FilterFactory factory) {
        return factory.getFilter(this);
    }

    public enum OperationTypeEnum {
        EQUALS(Integer::equals),
        GREATER_THAN((i, filter) -> i - filter >= 0),
        LOWER_THAN((i, filter) -> i - filter <= 0);

        private final BiPredicate<Integer, Integer> operation;

        OperationTypeEnum(BiPredicate<Integer, Integer> operation) {
            this.operation = operation;
        }

        public BiPredicate<Integer, Integer> getOperation() {
            return operation;
        }
    }

}
