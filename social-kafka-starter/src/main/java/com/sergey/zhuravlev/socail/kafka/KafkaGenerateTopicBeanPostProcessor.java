package com.sergey.zhuravlev.socail.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

public class KafkaGenerateTopicBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private GenericApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (GenericApplicationContext) applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        if (targetClass.isAnnotationPresent(GenerateTopics.class)) {
            Arrays.stream(targetClass.getDeclaredFields())
                    .filter(field -> field.getType().isAssignableFrom(KafkaTopicConfiguration.class))
                    .forEach(field -> {
                        field.setAccessible(true);
                        Object value = ReflectionUtils.getField(field, bean);
                        if (value != null) {
                            KafkaTopicConfiguration configuration = (KafkaTopicConfiguration) value;
                            this.context.registerBeanDefinition(
                                    String.format("%sTopic", field.getName()),
                                    makeBeanDefinition(NewTopic.class, new Object[]{configuration.getName(), 1, (short) 1}));
                        } else {
                            throw new IllegalStateException("Annotation class contains nullable field. Field [" + field.getName() + "]");
                        }
                    });
        }
        return bean;
    }

    private static GenericBeanDefinition makeBeanDefinition(Class<?> clazz, Object[] constructorArguments) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(clazz);
        ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
        setConstructorArguments(argumentValues, constructorArguments);
        return beanDefinition;
    }

    private static void setConstructorArguments(ConstructorArgumentValues topicConstructor, Object... constructorArguments) {
        for (int i = 0; i < constructorArguments.length; ++i) {
            topicConstructor.addIndexedArgumentValue(i, constructorArguments[i]);
        }
    }

}
