package org.vn.medc.appdata;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class AppProps {

	private static Properties props; 
	
	public static void InitProperties(String configFile) {
		FileInputStream fis = null;
		try {
			
			File file = new File(configFile);
			
			if(file.exists()) {
				if(AppConsts.MODIFIED_DATE != file.lastModified()) {
					props = new Properties();
					fis = new FileInputStream(configFile); 
					props.load(fis);
				}
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				if(fis != null) {
					fis.close();
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static String getProps(String key) {
		
		String value = null;
		InitProperties(AppConsts.CONFIG_FILE);
		if(props.containsKey(key)) {
			value = props.getProperty(key);
		}
		return value;
	}
}
