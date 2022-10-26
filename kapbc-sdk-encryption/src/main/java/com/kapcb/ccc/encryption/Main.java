package com.kapcb.ccc.encryption;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <a>Title: ${NAME} </a>
 * <a>Author: kapcb <a>
 * <a>Description: ${NAME} <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date ${DATE} ${TIME}
 * @since 1.0
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Main.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}