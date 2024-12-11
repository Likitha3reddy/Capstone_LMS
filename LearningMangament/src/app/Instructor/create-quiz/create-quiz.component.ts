import { Component, OnInit } from '@angular/core';
import { AssignmentServiceService } from '../../services/assignment-service.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-create-quiz',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './create-quiz.component.html',
  styleUrls: ['./create-quiz.component.scss']
})
export class CreateQuizComponent implements OnInit{
  quizTitle: string = '';
  questions: any[] = [
    { questionText: '', options: ['', '', '', ''], correctOption: 1 }
  ];
  courseId!: number;
  moduleId!: number;
  instructorId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: AssignmentServiceService
  ) {}

  ngOnInit(): void {
    // Extract courseId and moduleId from route parameters
    this.route.params.subscribe((params) => {
      this.courseId = +params['courseId'];
      this.moduleId = +params['moduleId'];
    });
  
    // Get userId from local storage
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.instructorId = +userId; // Parse userId to a number
      console.log('Instructor ID:', this.instructorId); // Log to verify
    } else {
      console.error('Instructor ID not found in local storage.');
      alert('Instructor ID is missing. Please log in again.');
      this.router.navigate(['/login']); // Redirect to login if userId is missing
    }
  }
  
  addQuestion(): void {
    this.questions.push({ questionText: '', options: ['', '', '', ''], correctOption: 1 });
  }

  createQuiz(): void {
    if (!this.instructorId) {
      alert('Instructor ID is missing. Please log in again.');
      this.router.navigate(['/login']);
      return;
    }

    const quizData = this.questions.map((q) => ({
      quizTitle: this.quizTitle,
      instructorId: this.instructorId,
      courseId: this.courseId,
      moduleId: this.moduleId,
      questionText: q.questionText,
      option1: q.options[0],
      option2: q.options[1],
      option3: q.options[2],
      option4: q.options[3],
      correctOption: q.correctOption
    }));

    this.quizService.createQuiz(quizData).subscribe(
      () => {
        console.log('Quiz data sent to backend:', quizData);
        alert(this.courseId);
        alert('Quiz created successfully!');
        this.router.navigate(['/instructor-dashboard/courses']);
      },
      (error) => {
        console.error('Error creating quiz:', error);
      }
    );
  }
}
