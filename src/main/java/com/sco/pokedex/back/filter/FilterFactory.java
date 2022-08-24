package com.sco.pokedex.back.filter;

import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.dto.filter.IntegerFilterDescDto;
import com.sco.pokedex.back.dto.filter.StringFilterDescDto;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

@Component
public class FilterFactory {

    public StringFilter getFilter(StringFilterDescDto desc) {
        return new StringFilter(
                (Function<PokemonDto, String>) getPropertyValue(desc.getPropertyName()),
                desc.getValue());
    }

    public IntegerFilter getFilter(IntegerFilterDescDto desc) {
        return new IntegerFilter(
                (Function<PokemonDto, Integer>) getPropertyValue(desc.getPropertyName()),
                desc.getOperationType().getOperation(),
                desc.getValue());
    }

    private Function<PokemonDto, ?> getPropertyValue(String propertyName) {
        Method propertyReadMethod = Arrays.stream(PropertyUtils.getPropertyDescriptors(PokemonDto.class))
                .filter(pd -> pd.getName().equals(propertyName))
                .findFirst()
                .map(PropertyDescriptor::getReadMethod)
                .orElseThrow();

        return p -> {
            try {
                return propertyReadMethod.invoke(p);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
