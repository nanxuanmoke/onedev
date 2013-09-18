package com.pmease.gitop.product;

import java.io.File;
import java.util.Properties;

import com.google.inject.name.Names;
import com.pmease.commons.bootstrap.Bootstrap;
import com.pmease.commons.jetty.ServerConfigurator;
import com.pmease.commons.jetty.ServletContextConfigurator;
import com.pmease.commons.loader.AbstractPluginModule;
import com.pmease.commons.loader.AppName;
import com.pmease.commons.util.FileUtils;
import com.pmease.gitop.core.setting.ServerConfig;

public class ProductModule extends AbstractPluginModule {

    @Override
	protected void configure() {
		super.configure();
		
		bindConstant().annotatedWith(AppName.class).to("Gitop");
		
		Properties hibernateProps = FileUtils.loadProperties(
				new File(Bootstrap.installDir, "conf/hibernate.properties")); 
		bind(Properties.class).annotatedWith(Names.named("hibernate")).toInstance(hibernateProps);
		
		Properties serverProps = FileUtils.loadProperties(
				new File(Bootstrap.installDir, "conf/server.properties")); 
		bind(Properties.class).annotatedWith(Names.named("server")).toInstance(serverProps);
		
		bind(ServerConfig.class).to(DefaultServerConfig.class);
		
		contribute(ServerConfigurator.class, GitopServerConfigurator.class);
		contribute(ServletContextConfigurator.class, GitopServletContextConfigurator.class);
	}

}
