package com.fjh.rocketapi.config;

import com.github.alenfive.rocketapi.datasource.DataSourceDialect;
import com.github.alenfive.rocketapi.datasource.DataSourceManager;
import com.github.alenfive.rocketapi.datasource.MySQLDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2021/12/7 21:48
 */
@Component
public class DefaultDataSourceManager extends DataSourceManager {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {

        Map<String, DataSourceDialect> dialects = new HashMap<>();
        //通过MysqlDataSource的第二个参数为`true`来表示生成的API信息所存储的库，有且仅有一个为true
        dialects.put("mysql", new MySQLDataSource(dataSource, true));
        super.setDialectMap(dialects);
    }
}
