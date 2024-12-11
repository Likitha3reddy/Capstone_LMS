import { CommonModule } from '@angular/common';
import { Component,input,output } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../AuthService/auth.service';

@Component({
  selector: 'app-side-navbar-instructor',
  standalone: true,
  imports: [RouterModule,CommonModule],
  templateUrl: './side-navbar-instructor.component.html',
  styleUrl: './side-navbar-instructor.component.scss'
})
export class SideNavbarInstructorComponent {

  isLeftSidebarCollapsed = input.required<boolean>();
  changeIsLeftSidebarCollapsed = output<boolean>();
  constructor(private authService:AuthService,private r:Router){}

  toggleCollapse(): void {
    this.changeIsLeftSidebarCollapsed.emit(!this.isLeftSidebarCollapsed());
  }

  closeSidenav(): void {
    this.changeIsLeftSidebarCollapsed.emit(true);
  }

  logout(): void {
    this.authService.logout();
    if(!localStorage.getItem('userId')){
      this.r.navigate(['/login']); // Redirect to login page
    }
    
  }
}
