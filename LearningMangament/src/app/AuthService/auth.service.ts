import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  logout(): void {
    const msg=confirm("Are you sure to logout?");
    if(msg){
      localStorage.removeItem('userId');
      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('role');
      alert("You have been successfully logged out.");
    }
  }

  storeUserId(userId: number,role:string): void {
    localStorage.setItem('userId', userId.toString());
    localStorage.setItem('role',role.toString());
    localStorage.setItem('isLoggedIn', 'true');
    
  }
  isAuthenticated(): boolean {
    return localStorage.getItem('isLoggedIn') === 'true';
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }
  getUserId(): number | null {
    const storedId = localStorage.getItem('userId');
    return storedId ? parseInt(storedId, 10) : null;
  }
}
