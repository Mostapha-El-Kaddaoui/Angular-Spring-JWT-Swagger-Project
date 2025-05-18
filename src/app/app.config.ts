import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppHttpInterceptor } from './interceptors/app-http.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideRouter(routes),
    provideHttpClient(
      withInterceptorsFromDi() // Enable DI-based interceptors
    ),
    importProvidersFrom(ReactiveFormsModule), 
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true }
  ]
};