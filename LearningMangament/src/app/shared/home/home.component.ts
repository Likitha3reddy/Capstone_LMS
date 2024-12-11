import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../AuthService/auth.service';
import { CoursesService } from '../../services/courses.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EnrollmentService } from '../../services/enrollmentservice.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  authenticate:boolean=false;
  filteredCourses: any[] = []; // Store filtered courses
  searchTerm: string = ''; // Bind to the search input field

  constructor(private r:Router,private authService:AuthService,private coursesService:CoursesService,private enrollmentService:EnrollmentService,private http:HttpClient){
  }
  login(){
    this.r.navigate(['login'])
  }
  
  courses: any[] = []; // Store fetched courses
  selectedCourseId: number | null = null;

  ngOnInit(): void {
    this.fetchCourses();
    if(localStorage.getItem('userId')){
      this.authenticate=true;
    }
  }

  fetchCourses(): void {
    this.coursesService.getAllCourses().subscribe(
      (data) => {
        this.courses = data; // Assign fetched courses to the local array
        this.filteredCourses = [...this.courses]; 
      },
      (error) => {
        console.error('Error fetching courses:', error);
      }
    );
  }
  onSearchChange(): void {
    if (this.searchTerm.length > 3) {
      // Filter courses based on the search term
      this.filteredCourses = this.courses.filter((course) =>
        course.title.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      // Reset to show all courses if search term is less than 4 characters
      this.filteredCourses = [...this.courses];
    }
  }
  enroll(courseId: number): void {
    const userId = this.authService.getUserId();
  
    if (!userId) {
      alert('You need to log in to enroll in a course.');
      this.r.navigate(['/login']);
      return;
    }
  
    const canEnroll = Math.random() < 0.5; // Generate a random boolean

    if (canEnroll) {
      // Proceed with enrollment
      const enrollmentData = {
        userId: userId,
        courseId: courseId,
        status: 'Enrolled', // Set status to 'Enrolled' when canEnroll is true
      };

      this.http.post<{ message: string }>('http://localhost:8091/api/enrollments', enrollmentData).subscribe({
        next: (response) => {
          alert(response.message);
        },
        error: (err) => {
          console.error('Error during enrollment:', err);
          const errorMessage =
            typeof err.error === 'string'
              ? err.error
              : err.error?.message || 'An unexpected error occurred.';
          alert(errorMessage);
        },
      });
    } else {
      // Reject enrollment
      alert('Enrollment request is rejected. Please contact the admin for further assistance.');
    }
  }
}