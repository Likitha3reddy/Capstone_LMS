import { CommonModule } from '@angular/common';
import { Component,input,output } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../AuthService/auth.service';

@Component({
  selector: 'app-left-sidebar-student',
  standalone: true,
  imports: [RouterModule,CommonModule],
  templateUrl: './left-sidebar-student.component.html',
  styleUrl: './left-sidebar-student.component.scss'
})
export class LeftSidebarStudentComponent {
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
      this.r.navigate(['/login']);
    } // Redirect to login page
  }
}
