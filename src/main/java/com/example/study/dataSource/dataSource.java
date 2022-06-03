// package com.example.study.dataSource;

// import javax.activation.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
// import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
// import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

// import org.springframework.jdbc.datasource.init.*;
// import org.springframework.jdbc.datasource.lookup.*;

// public class dataSource {

// @Bean
// DataSource dataSource() {
// return new EmbeddedDatabaseBuilder()
// .setType(javax.sql.DataSource)
// .addScript("schema.sql")
// .build();

// }
// }
