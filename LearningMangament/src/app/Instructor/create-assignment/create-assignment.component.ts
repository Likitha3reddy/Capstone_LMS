import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AssignmentServiceService } from '../../services/assignment-service.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-create-assignment',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './create-assignment.component.html',
  styleUrls: ['./create-assignment.component.scss']
})
export class CreateAssignmentComponent implements OnInit {
  assignment = {
    title: '',
    description: '',
    courseId: 0,
    instructorId: 0,
    dueDate: null
  };

  constructor(
    private assignmentService: AssignmentServiceService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Fetch courseId from route params
    // this.route.params.subscribe((params) => {
    //   this.assignment.courseId = +params['courseId']; // Ensure courseId is a number
    // });
    this.assignment.courseId = Number(this.route.parent?.snapshot.paramMap.get('courseId'));

    // Retrieve instructorId from localStorage
    const instructorId = localStorage.getItem('userId');
    if (instructorId) {
      this.assignment.instructorId = +instructorId; // Ensure instructorId is a number
    } else {
      alert('Instructor not logged in!');
    }
  }

  onSubmit(): void {
    this.assignmentService.createAssignment(this.assignment).subscribe({
      next: (response) => alert('Assignment created successfully!'),
      error: (err) => alert('Error creating assignment.')
    });
  }
  
}
