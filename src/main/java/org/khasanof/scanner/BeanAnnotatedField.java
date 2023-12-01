package org.khasanof.scanner;

import lombok.*;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.scanner
 * @since 11/26/2023 3:46 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BeanAnnotatedField {

    private String beanName;
    private Object instance;
    private List<Field> fields;
    private BeanDefinition beanDefinition;

    public BeanAnnotatedField beanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
        return this;
    }

    public BeanAnnotatedField beanName(String beanName) {
        this.beanName = beanName;
        return this;
    }

    public BeanAnnotatedField instance(Object instance) {
        this.instance = instance;
        return this;
    }

    public BeanAnnotatedField fields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

}
