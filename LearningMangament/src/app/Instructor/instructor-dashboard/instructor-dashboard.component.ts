import { CommonModule } from '@angular/common';
import { Component,input,computed,HostListener,signal, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../AuthService/auth.service';
import { DashboardComponent } from '../../shared/dashboard/dashboard.component';
import { SideNavbarInstructorComponent } from '../side-navbar-instructor/side-navbar-instructor.component';

@Component({
  selector: 'app-instructor-dashboard',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule,DashboardComponent,SideNavbarInstructorComponent],
  templateUrl: './instructor-dashboard.component.html',
  styleUrl: './instructor-dashboard.component.scss'
})
export class InstructorDashboardComponent implements OnInit {
  isLeftSidebarCollapsed = signal<boolean>(false);
  screenWidth = signal<number>(window.innerWidth);

  @HostListener('window:resize')
  onResize() {
    this.screenWidth.set(window.innerWidth);
    if (this.screenWidth() < 768) {
      this.isLeftSidebarCollapsed.set(true);
    }
  }

  ngOnInit(): void {
    this.isLeftSidebarCollapsed.set(this.screenWidth() < 768);
  }

  changeIsLeftSidebarCollapsed(isLeftSidebarCollapsed: boolean): void {
    this.isLeftSidebarCollapsed.set(isLeftSidebarCollapsed);
  }

}
