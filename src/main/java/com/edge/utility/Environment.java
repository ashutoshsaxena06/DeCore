package com.edge.utility;


/**
 * Enumerated environment types used for Test Services testing.
 * 
 * @author keith.homco
 *
 */
public enum Environment {
	ITV1("ITV1", "Test Env 1"), ITV2("ITV2", "Test Env 2"), ITV3("ITV3", "Test Env 3"), E2E("E2E", "End to End"), 
	INT1("INT1", "Dev Env 1"), INT2("INT2", "Dev Env 2"), PROD("PROD", "Production");
	
	private String key;
	private String name;
	
	/**
	 * @param name
	 */
	private Environment(String key, String name) {
		this.key = key;
		this.name = name;
	}
	
	/**
	 * @return instance key
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * @return instance name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @param from
	 * 			String form of the environment (can be short or long form of name)
	 * 
	 * @return EnvironmentType representing the environment of the test execution
	 */
	public static Environment fromString(String from) {
		if (from != null) {
			for ( Environment e : values() ) {
				if(e.key.toLowerCase().equals(from.toLowerCase()) || e.name.toLowerCase().equals(from.toLowerCase())) {
					return e;
				}
			}
		}
		return null;
	}
	
	public String toString() {
		return this.name;
	}
}
