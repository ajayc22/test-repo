package org.vn.medc.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class IVRDBManager {

	private BasicDataSource dataSource = null;

	private static IVRDBManager dbMgr = null;

	private IVRDBManager() {
		
	}
	
	public void InitDB(String host, Integer port, String uName, String pswd, String dbName, Integer noOfConn) {
		try {

			String dbUrl = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbName+";useUnicode=true&characterEncoding=UTF-8";

			this.InitDB(dbUrl, uName, pswd, noOfConn);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void InitDB(String dbUrl, String uName, String pswd, Integer noOfConn) {
		try {

			if(dataSource == null) {
				dataSource = new BasicDataSource();
				dataSource.setUrl(dbUrl);
				dataSource.setUsername(uName);
				dataSource.setPassword(pswd);
				dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				dataSource.setMaxTotal(noOfConn);
				

			}else if ( !dataSource.getUrl().equalsIgnoreCase(dbUrl)
					|| !dataSource.getUsername().equalsIgnoreCase(uName)
					|| !dataSource.getPassword().equalsIgnoreCase(pswd) || (dataSource.getMaxTotal() != noOfConn)) {
				
				dataSource.close();
				
				dataSource = new BasicDataSource();
				dataSource.setUrl(dbUrl);
				dataSource.setUsername(uName);
				dataSource.setPassword(pswd);
				dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				dataSource.setMaxTotal(noOfConn);

			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public synchronized static IVRDBManager This() {

		if (dbMgr == null) {
			dbMgr = new IVRDBManager();
		}

		return dbMgr;
	}

	public Connection getConnection() throws Exception {
		return this.dataSource.getConnection();
	}
	
	public void CloseDBConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
