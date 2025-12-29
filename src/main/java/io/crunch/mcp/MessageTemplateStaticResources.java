package io.crunch.mcp;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import jakarta.enterprise.event.Observes;

/**
 * Registers a static resources route for the application.
 *
 * <p>
 * This CDI-managed type contributes a listener method that is invoked during
 * Quarkus startup. When the application starts it installs a Vert.x route that
 * serves files from the local {@code static/} directory at the HTTP path
 * {@code /static/*}.
 * </p>
 *
 * <p>Behavior</p>
 * <ul>
 *   <li>A call to {@code installRoute} is made by the CDI container on the
 *       {@code StartupEvent} lifecycle event.</li>
 *   <li>The route uses Vert.x {@link StaticHandler} created with the static
 *       filesystem path {@code static/} and matches requests under
 *       {@code /static/*}.</li>
 *   <li>The class itself is stateless and side-effecting only by registering
 *       the route on the provided {@link Router} instance.</li>
 * </ul>
 *
 * <p>Configuration &amp; Extension</p>
 * <p>
 * The static directory and request path are hard-coded in this implementation.
 * To change the served path or file location, update the call to
 * {@link StaticHandler#create(String)} or the route path. For more advanced
 * behavior (caching, index pages, security), configure or replace the
 * {@link StaticHandler} instance.
 * </p>
 *
 * @see io.vertx.ext.web.Router
 * @see io.vertx.ext.web.handler.StaticHandler
 * @see io.quarkus.runtime.StartupEvent
 */
public class MessageTemplateStaticResources {

	/**
	 * Installs the static resource route on the supplied {@link Router}.
	 *
	 * <p>
	 * This method observes the Quarkus {@link StartupEvent} and is executed once
	 * during application startup. It registers a handler that serves files from
	 * the local {@code static/} directory under the HTTP path {@code /static/*}.
	 * </p>
	 *
	 * @param startupEvent the startup event emitted by the Quarkus runtime;
	 *                     observed to trigger route installation
	 * @param router the Vert.x router to which the static handler will be added
	 * @implNote the route path and directory are hard-coded; change the
	 *           arguments to {@link StaticHandler#create(String)} and
	 *           {@code router.route().path(...)} to modify behavior
	 */
	void installRoute(@Observes StartupEvent startupEvent, Router router) {
		router.route()
				.path("/static/*")
				.handler(StaticHandler.create("static/"));
	}
}
