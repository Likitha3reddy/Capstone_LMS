import { Component, OnInit } from '@angular/core';
import { AssignmentServiceService } from '../../services/assignment-service.service';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-submitted-assignments',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './submitted-assignments.component.html',
  styleUrl: './submitted-assignments.component.scss'
})
export class SubmittedAssignmentsComponent  implements OnInit{
  assignments: any[] = [];
  submissions: any[] = [];
  instructorId =0; // Example instructor ID
  isLoading = false;

  constructor(private assignmentService: AssignmentServiceService,private http:HttpClient) {}

  ngOnInit(): void {
    this.instructorId=+localStorage.getItem('userId')!;
    this.loadSubmissions();
  }

  loadSubmissions(): void {
    this.isLoading = true;
    this.assignmentService.getSubmissionsByInstructorId(this.instructorId).subscribe({
      next: (data) => {
        this.submissions = data;
        console.log(this.submissions);
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching submissions:', err);
        this.isLoading = false;
      },
    });
  }
  updateGrade(submissionId: number, grade: string): void {
    // Construct the URL with the grade as a query parameter
    const apiUrl = `http://localhost:8091/api/submissions/${submissionId}/grade?grade=${grade}`;
  
    // Make the PUT request
    this.http.put(apiUrl, {}).subscribe({
      next: (response) => {
        console.log('Grade updated successfully:', response);
        alert('Grade updated successfully');
      },
      error: (error) => {
        console.error('Error updating grade:', error);
        alert('Failed to update grade. Please check the input and try again.');
      },
    });
  }
  
  
}
