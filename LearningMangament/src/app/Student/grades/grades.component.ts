import { Component, OnInit } from '@angular/core';
import { EnrollmentService } from '../../services/enrollmentservice.service';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProgressService } from '../../services/progress.service';

@Component({
  selector: 'app-grades',
  standalone: true,
  imports: [RouterModule,CommonModule,FormsModule],
  templateUrl: './grades.component.html',
  styleUrl: './grades.component.scss'
})
export class GradesComponent implements OnInit{
  courseCount: number = 0;
  errorMessage: string = '';
  progressData: { [videoId: string]: number } = {};
  averagePercentage: number = 0;
  averageGrade: number = 0;

  noSubmissions: boolean = false;

  constructor(private http: HttpClient,private progressService:ProgressService) {}


  ngOnInit(): void {
    this.progressService.progress$.subscribe((data) => {
      this.progressData = data;
    });
    
    const userId = Number(localStorage.getItem('userId')); // Fetch userId from localStorage

    if (!userId) {
      this.errorMessage = 'User not logged in.';
      return;
    }

    // Fetch courses and count them
    this.http.get<any[]>(`http://localhost:8091/api/enrollments/courses/${userId}`).subscribe({
      next: (data) => {
        this.courseCount = data.length;
      },
      error: (err) => {
        console.error('Error fetching course count:', err);
        this.errorMessage = 'Failed to fetch course count.';
      },
    });

    this.http.get<number>(`http://localhost:8091/api/progress-reports/average/${userId}`)
      .subscribe((data: number) => {
        this.averagePercentage = data;
      });


      this.http
      .get<any>(`http://localhost:8091/api/submissions/average-grade/user/${userId}`)
      .subscribe({
        next: (data) => {
          if (typeof data === 'number') {
            this.averageGrade = data;
          } else if (typeof data === 'string' && data === 'No submissions available') {
            this.averageGrade = 0; // Default value for no submissions
            this.noSubmissions = true; // Add a flag to handle this case in the UI
          }
        },
        error: (err) => {
          console.error('Error fetching average grade:', err);
        },
      });
  
  }
}


