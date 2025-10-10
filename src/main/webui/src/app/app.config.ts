import {ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";
import {provideClientHydration, withEventReplay} from '@angular/platform-browser';
import {provideHttpClient, withFetch} from '@angular/common/http';
import {providePrimeNG} from "primeng/config";
import Aura from "@primeuix/themes/aura";

/**
 * Global application configuration object.
 *
 * The `ApplicationConfig` interface is used to declare all dependency injection
 * providers available app-wide. These providers are automatically picked up
 * during the bootstrap process (via `bootstrapApplication(AppComponent, appConfig)`).
 */
export const appConfig: ApplicationConfig = {
    providers: [
        /**
         * Registers global error listeners at the browser level.
         * - Captures unhandled exceptions and unhandled promise rejections.
         * - Integrates with Angular’s error handling pipeline.
         */
        provideBrowserGlobalErrorListeners(),
        /**
         * Configures Angular’s Zone.js change detection behavior.
         * - `eventCoalescing: true` merges multiple DOM events into a single
         *   change detection cycle, improving performance by reducing unnecessary re-renders.
         */
        provideZoneChangeDetection({eventCoalescing: true}),
        /**
         * Sets up the Angular Router with the defined application routes.
         * The `routes` array is imported from `app.routes.ts`.
         */
        provideRouter(routes),
        /**
         * Enables client-side hydration for server-side rendered (SSR) applications.
         * - `withEventReplay()` ensures that user events triggered before hydration
         *   are replayed afterward, maintaining UX continuity.
         * - Even if SSR isn’t used initially, this enables compatibility.
         */
        provideClientHydration(withEventReplay()),
        /**
         * Provides the Angular HTTP client configured to use the Fetch API.
         * - `withFetch()` switches the default HTTP backend to `fetch` for modern browsers.
         * - Improves performance and simplifies cross-platform usage.
         */
        provideHttpClient(withFetch()),
        /**
         * Enables asynchronous animations support.
         * - This variant loads the animation module asynchronously to improve
         *   startup performance in modern Angular builds.
         */
        provideAnimationsAsync(),
        /**
         * Configures PrimeNG’s global UI settings and theming.
         * - The `Aura` theme (from PrimeUIX) is used as the global visual preset.
         * - The theme controls component appearance such as colors, typography, spacing, etc.
         */
        providePrimeNG({
            theme: {
                preset: Aura,
            },
        })
    ]
};
