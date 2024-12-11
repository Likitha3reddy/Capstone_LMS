import { Component, OnInit } from '@angular/core';
import { StudentserviceService } from '../../services/studentservice.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-assignment',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.scss'], // Fixed property name from "styleUrl" to "styleUrls"
})
export class AssignmentComponent implements OnInit {
  assignments: any[] = [];
  userId: number;
  fileMap: { [assignmentId: number]: File | null } = {};

  constructor(private studentService: StudentserviceService) {
    this.userId = +localStorage.getItem('userId')!;
  }

  ngOnInit(): void {
    // Fetch assignments for the logged-in user
    this.studentService.getAssignments(this.userId).subscribe((data) => {
      this.assignments = data.filter((assignment) => assignment.status !== 'SUBMITTED');
    });
  }

  onFileSelected(event: any, assignmentId: number): void {
    const file = event.target.files[0];
    if (file) {
      const fileType = file.type;

      // Validate file type (allow only PDF and Word documents)
      if (
        fileType === 'application/pdf' ||
        fileType === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' ||
        fileType === 'application/msword'
      ) {
        this.fileMap[assignmentId] = file;
      } else {
        alert('Only PDF or Word documents are allowed!');
        event.target.value = ''; // Reset file input if the file is not valid
      }
    }
  }

  submitAssignment(assignmentId: number, courseId: number): void {
    const file = this.fileMap[assignmentId];
    if (!file) {
      alert('Please upload a file before submitting.');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('assignmentId', assignmentId.toString());
    formData.append('courseId', courseId.toString());
    formData.append('userId', this.userId.toString());
    formData.append('status', 'SUBMITTED');

    console.log('FormData content:');
    formData.forEach((value, key) => console.log(`${key}: ${value}`));

    this.studentService.submitAssignment(formData).subscribe({
      next: () => {
        alert('Assignment submitted successfully!');
        // Remove the submitted assignment from the list
        this.assignments = this.assignments.filter((a) => a.assignmentId !== assignmentId);
      },
      error: (err) => {
        console.error('Submission error:', err);
        alert('Error submitting assignment.');
      },
    });
  }
}
