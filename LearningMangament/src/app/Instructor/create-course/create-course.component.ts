import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-create-course',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './create-course.component.html',
  styleUrl: './create-course.component.scss'
})
export class CreateCourseComponent {
  userId:any;
  course = {
    title: '',
    description: '',
    category: '',
    instructorId: localStorage.getItem('userId'), // Example: Static Instructor ID
    imageUrl: '',
    status: 'Active', // Default status
    createdDate: new Date().toISOString(), // Auto-populated
  };

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit(): void {
    console.log(this.course);
    const apiUrl = 'http://localhost:8091/courses'; // Update with your backend API URL
    this.http.post(apiUrl, this.course).subscribe({
      next: (response) => {
        // console.log('Course added successfully:', response);
        alert('Course added successfully!');
        this.router.navigate(['/instructor-dashboard/courses']); // Redirect after submission
      },
      error: (err) => {
        console.error('Error adding course:', err);
        alert('Failed to add course.');
      },
    });
  }

}
