package io.crunch.mcp;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import jakarta.enterprise.event.Observes;

/**
 * This class sets up a route to serve static resources from the local "static" directory.
 */
public class MessageTemplateStaticResources {

	void installRoute(@Observes StartupEvent startupEvent, Router router) {
		router.route()
				.path("/static/*")
				.handler(StaticHandler.create("static/"));
	}
}
