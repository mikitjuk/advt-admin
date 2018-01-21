import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with jwt token if available
    let jwtToken =localStorage.getItem('jwt-token');
    console.log(jwtToken);
    if (jwtToken) {
      request = request.clone({
        setHeaders: {
          Authorization: `${jwtToken}`
        }
      });
    }

    return next.handle(request);
  }
}
