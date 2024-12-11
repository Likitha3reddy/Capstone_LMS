import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-instructor-courses',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './instructor-courses.component.html',
  styleUrl: './instructor-courses.component.scss'
})
export class InstructorCoursesComponent {
  
  courses: any; // This will hold the user data

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Retrieve the userId from localStorage
    const userId = localStorage.getItem('userId');

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
  }

}
