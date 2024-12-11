import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { InstructorEnrollService } from '../../services/instructor-enroll.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-instructor-performance',
  standalone: true,
  imports: [CommonModule,RouterModule,FormsModule],
  templateUrl: './instructor-performance.component.html',
  styleUrl: './instructor-performance.component.scss'
})
export class InstructorPerformanceComponent implements OnInit {
  courses: any; // This will hold the user data
  coursesWithEnrollments: any[] = [];
  instructorId:number=0;
  userProgressData: { userName: string; averageProgress: number }[] = [];
  

  constructor(private http: HttpClient,private instructorEnrollService:InstructorEnrollService) {}

  ngOnInit(): void {
    // Retrieve the userId from localStorage
    const userId = localStorage.getItem('userId');
    this.instructorId=+localStorage.getItem('userId')!;
    this.fetchProgressData();
    
    
    if (userId) {
      // Make an API call to fetch user details
      this.http.get(`http://localhost:8091/courses/instructor/${userId}`).subscribe(
        (data: any) => {
          this.courses = data;
          // console.log(this.courses);
        },
        (error) => {
          console.error('Error fetching user details:', error);
        }
      );
    } else {
      console.error('No userId found in localStorage.');
    }

    this.instructorEnrollService
      .getCoursesWithEnrollmentCounts(this.instructorId)
      .subscribe((data) => {
        this.coursesWithEnrollments = data;
      });
      console.log(this.coursesWithEnrollments);
  }
    
  fetchProgressData(): void {
    const courseId = 9; // Replace with the dynamic courseId if needed
    this.http
      .get<{ userName: string; averageProgress: number }[]>(
        `http://localhost:8091/courses/${courseId}/average`
      )
      .subscribe({
        next: (data) => {
          this.userProgressData = data;
          console.log('Progress Data:', this.userProgressData);
        },
        error: (err) => {
          console.error('Error fetching progress data:', err);
        },
      });
    }
}


