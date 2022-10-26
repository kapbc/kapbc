package com.kapcb.ccc.encryption.po.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <a>Title: MyDatasource </a>
 * <a>Author: kapcb <a>
 * <a>Description: MyDatasource <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2022/10/26 22:05
 * @since 1.0
 */
@Setter
@Getter
@ToString
@Configuration
public class MyDatasource {

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

}
