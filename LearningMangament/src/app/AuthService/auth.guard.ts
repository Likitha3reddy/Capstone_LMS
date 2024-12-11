import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService); // Inject AuthService
  const router = inject(Router); // Inject Router

  const requiredRole = route.data?.['role']; // Retrieve required role from route data
  const userRole = authService.getRole(); // Get the user's role

  if (authService.isAuthenticated() && userRole === requiredRole) {
    return true; // Allow access
  }

  // Redirect to login page if not authenticated or role doesn't match
  router.navigate(['/login']);
  return false;
};
