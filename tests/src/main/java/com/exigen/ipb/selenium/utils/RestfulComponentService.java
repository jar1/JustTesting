
package com.exigen.ipb.selenium.utils;

import com.exigen.ipb.components.domain.ConcreteUserComponent;
import com.exigen.ipb.e2e.support.data.EnvironmentRepresentation;
import com.exigen.ipb.e2e.support.rs.util.RSWebClientBuilder;

/**
 *
 * @author ggrazevicius
 * @since 3.9
 */
public class RestfulComponentService {
	public static final String COMPONENT_SERVICE_PATH = "services/PfRestful/componentServices/userComponent/";
	
	private EnvironmentRepresentation env;
	
	public RestfulComponentService(EnvironmentRepresentation env) {
		this.env = env;
	}
	
	public ConcreteUserComponent findConcreteUserComponent(String name, double version) {
		return RSWebClientBuilder.createWebClient(env, env.getAdminApplicationUrl())
				.path(buildUserComponentServicePath(name, version)).get(ConcreteUserComponent.class);
	}
	
	public void removeConcreteUserComponent(String name, double version) {
		RSWebClientBuilder.createWebClient(env, env.getAdminApplicationUrl())
				.path(buildUserComponentServicePath(name, version)).delete();
	}
	
	private String buildUserComponentServicePath(String name, double version) {
		return (new StringBuilder(COMPONENT_SERVICE_PATH)).append(name).append("/").append(version).toString();
	}
}
