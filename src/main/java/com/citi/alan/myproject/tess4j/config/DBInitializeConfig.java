package com.citi.alan.myproject.tess4j.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBInitializeConfig {

	@Autowired
	private DataSource dataSource;
	
	
	@PostConstruct
	public void initializeData(){
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			createRoleData(statement);
			createMerchantData(statement);
			statement.close();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    private void createRoleData(Statement statement) throws SQLException {
        statement.executeUpdate("delete from role");
        statement.executeUpdate("insert into role values (1, 'ADMIN')");
        statement.executeUpdate("insert into role values (2, 'USER')");
    }
    
    private void createMerchantData(Statement statement) throws SQLException {
        statement.executeUpdate("delete from merchant");
        statement.executeUpdate("insert into merchant values ('160557220163','得利家用A')");
        statement.executeUpdate("insert into merchant values ('160504990139','尚卫三A')");
        statement.executeUpdate("insert into merchant values ('160559400019','通盛摩托老')");
        statement.executeUpdate("insert into merchant values ('160506010109','飞亚餐馆')"); 
        statement.executeUpdate("insert into merchant values ('160507010084','宾客来旅馆')");
        statement.executeUpdate("insert into merchant values ('160550940049','如意金店')");
        statement.executeUpdate("insert into merchant values ('160509010007','国际娱乐会所')");
        statement.executeUpdate("insert into merchant values ('160501160091','方芳家电')");
        statement.executeUpdate("insert into merchant values ('160511030002','远航商务服务中心')");
        statement.executeUpdate("insert into merchant values ('160507010070','盛隆旅馆')");
        statement.executeUpdate("insert into merchant values ('160570320002','通盛摩托新')");
        statement.executeUpdate("insert into merchant values ('160504070052','东升百货老')");
        statement.executeUpdate("insert into merchant values ('160559620007','旅行社')");
        statement.executeUpdate("insert into merchant values ('160509010006','金色年代')");
        statement.executeUpdate("insert into merchant values ('160558010005','太平洋电影')");
    }
}
