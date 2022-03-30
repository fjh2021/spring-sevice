package com.fjh.mp;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/fjh?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai",
            "root", "123456");

    public static void main(String[] args) {
        String basePath = "E:projectName/src/main/";
        String basePackage = "com.fjh";
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称")) // 设置作者
                        .fileOverride() // 覆盖已生成文件
                        .enableSwagger() // 开启 swagger 模式
                        .outputDir(basePath + "java/")  // 指定输出目录
                )
                .packageConfig((scanner, builder) -> {
                    builder.parent(basePackage) // 设置父包名
                            .moduleName(scanner.apply("请输入模块名")) //设置模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, basePath + "resources/mapper/")); // 设置mapperXml生成路径
                })
                // 策略配置

                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("create_time", FieldFill.INSERT),
                                new Column("update_time", FieldFill.INSERT_UPDATE)
                        )
                        .build())
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
