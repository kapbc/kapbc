package com.kapcb.ccc.encryption.configuration;

import com.kapcb.ccc.encryption.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <a>Title: DecryptConfiguration </a>
 * <a>Author: kapcb <a>
 * <a>Description: DecryptConfiguration <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2022/10/26 22:06
 * @since 1.0
 */
@Slf4j
@Configuration
public class DecryptConfiguration implements EnvironmentAware, BeanFactoryPostProcessor, Ordered {

    // 加密配置前缀标志
    private String decryptPrefix = "Enc[";

    // 加密配置后缀标志
    private String decryptSuffix = "]";

    // 加密解密密钥
    private String secretKey = "kapcb";

    private ConfigurableEnvironment configurableEnvironment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        log.info("[ begin to decrypt ]");
        // 使用 environment 获取到所有的属性文件信息
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();

        // 使用 lambda 表达式进行刷选出满足条件的
        StreamSupport.stream(propertySources.spliterator(), false)
                .filter(ps -> ps instanceof OriginTrackedMapPropertySource)
                .collect(Collectors.toList())
                // 对满足条件的属性文件进行替换处理
                .forEach(ps -> convertPropertySource(propertySources, (PropertySource<Map>) ps));

        log.info("[ finish decrypt ]");
    }

    /**
     * 解密相关属性
     *
     * @param mutablePropertySources {@link MutablePropertySources}
     * @param propertySource         {@link OriginTrackedMapPropertySource}
     */
    private void convertPropertySource(MutablePropertySources mutablePropertySources, PropertySource<Map> propertySource) {
        Map source = propertySource.getSource();
        setDecryptProperties(source);

        HashMap<Object, Object> newMap = new HashMap<>(2 << 3);

        source.forEach((k, v) -> {
            String value = String.valueOf(v);
            if (!value.startsWith(decryptPrefix) || !value.endsWith(decryptSuffix)) {
                newMap.put(k, value);
                return;
            }

            String cipherText = value.replace(decryptPrefix, "").replace(decryptSuffix, "");
            String clearText = EncryptUtil.decrypt(secretKey, cipherText);
            // 这里要注意, 早期是支持直接source.put(k,clearText)替换的, 现在的版本不支持的, 底层使用了Collections$UnmodifiableMap,
            // 因为UnmodifiableMap不可进行put/remove操作, 否则会抛出异常UnsupportedOperationException
            // 不能source.put(k,clearText);这么写, 因为UnmodifiableMap不可进行put/remove操作, 否则会抛出异常UnsupportedOperationException
            newMap.put(k, clearText);
        });

        mutablePropertySources.replace(propertySource.getName(), new OriginTrackedMapPropertySource(propertySource.getName(), newMap));

    }

    /**
     * set decrypt properties
     *
     * @param source {@link Map}
     */
    private void setDecryptProperties(Map source) {
        decryptPrefix = source.get("decrypt.prefix") == null ? decryptPrefix : String.valueOf(source.get("decrypt.prefix"));
        decryptSuffix = source.get("decrypt.suffix") == null ? decryptSuffix : String.valueOf(source.get("decrypt.suffix"));
        secretKey = source.get("decrypt.secret.key") == null ? secretKey : String.valueOf(source.get("decrypt.secret.key"));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.configurableEnvironment = (ConfigurableEnvironment) environment;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }

}
