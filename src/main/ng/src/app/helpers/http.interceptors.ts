import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { StorageService } from '../services/storage.service';
import { catchError } from 'rxjs/operators';



@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private storageService: StorageService) {
   }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const authToken = this.storageService.getUser()?.token;

    if (authToken) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`,
        },
      });
    }
    console.log(req);

    return next.handle(req).pipe(catchError(err => {
      if ([401].includes(err.status) && this.storageService.getUser().token) {
          this.storageService.clean();

          window.location.reload();
      }

      const error = err.error?.message || err.statusText;
      return throwError(() => error);
  }))
  }
}

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];