import { Component,HostListener, OnInit, signal } from '@angular/core';
import { AuthService } from '../../AuthService/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EnrollmentService } from '../../services/enrollmentservice.service';
import { HttpClient } from '@angular/common/http';
import { DashboardComponent } from '../../shared/dashboard/dashboard.component';
import { LeftSidebarStudentComponent } from '../left-sidebar-student/left-sidebar-student.component';

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule,DashboardComponent,LeftSidebarStudentComponent],
  templateUrl: './student-dashboard.component.html',
  styleUrl: './student-dashboard.component.scss'
})
export class StudentDashboardComponent implements OnInit{
  courses: any[] = [];
  errorMessage: string = '';
  /*

  here adding 
  */
  isLeftSidebarCollapsed = signal<boolean>(false);
  screenWidth = signal<number>(window.innerWidth);

  @HostListener('window:resize')
  onResize() {
    this.screenWidth.set(window.innerWidth);
    if (this.screenWidth() < 768) {
      this.isLeftSidebarCollapsed.set(true);
    }
  }


  changeIsLeftSidebarCollapsed(isLeftSidebarCollapsed: boolean): void {
    this.isLeftSidebarCollapsed.set(isLeftSidebarCollapsed);
  }

  // Directly specify the backend URL here
  constructor(private authService:AuthService,private r:Router,private enrollmentService:EnrollmentService,private http:HttpClient){}

  logout(): void {
    this.authService.logout();
    this.r.navigate(['/login']); // Redirect to login page
  }
  ngOnInit(): void {
    this.isLeftSidebarCollapsed.set(this.screenWidth() < 768);
    const userId = Number(localStorage.getItem('userId')); // Fetch userId from localStorage

    if (!userId) {
      this.errorMessage = 'User not logged in.';
      return;
    }
    console.log("before api ");
    console.log(userId);
    this.http.get<any[]>(`http://localhost:8091/api/enrollments/courses/1`).subscribe({
      next: (data) => {
        console.log('Data:', data);
        this.courses = data; // Assign data to the courses array
      },
      error: (err) => {
        console.error('Error:', err);
        this.errorMessage = 'Failed to load courses.';
      },
    });
  }
}