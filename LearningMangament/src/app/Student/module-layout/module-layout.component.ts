import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, RouterOutlet } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { ProgressService } from '../../services/progress.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-module-layout',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './module-layout.component.html',
  styleUrl: './module-layout.component.scss'
})
export class ModuleLayoutComponent {
  modules: any[] = [];
  moduleContent: { [key: number]: any[] } = {};
  expandedModuleId: number | null = null;
  selectedContent: any | null = null;
  isLoading: boolean = true;
  isQuizSelected: boolean = false;
  questions: any[] = [];
  answers: { questionId: number; selectedOption: number }[] = [];
  results: { questionId: number; isCorrect: boolean }[] = [];
  progressData: { [key: number]: number } = {};


  totalCorrect: number = 0;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private sanitizer: DomSanitizer,
    private progressService: ProgressService
  ) {}

  ngOnInit(): void {
    const courseId = Number(this.route.snapshot.paramMap.get('courseId'));
    this.getModules(courseId);
  }

  getModules(courseId: number): void {
    console.log(courseId);
    this.isLoading = true;
    this.http
      .get<any[]>(`http://localhost:8091/modules/course/${courseId}`)
      .subscribe({
        next: (data) => {
          this.modules = data;
          this.generateRandomProgressForModules();
          data.forEach((module) => this.getContentByModuleId(module.moduleId));
          this.isLoading = false;
          console.log("-------"+this.modules);
          if (this.modules.length > 0) {
            this.expandedModuleId = this.modules[0].moduleId;
            this.selectFirstDocument(this.modules[0].moduleId);
          }
        },
        error: (err) => {
          console.error('Error fetching modules:', err);
          this.isLoading = false; // Stop loading on error
        },
        
      });
  }

  getContentByModuleId(moduleId: number): void {
    this.http
      .get<any[]>(`http://localhost:8091/modules/contents/module/${moduleId}`)
      .subscribe({
        next: (data) => {
          this.moduleContent[moduleId] = data.map((content) => {
            if (content.contentType === 'VIDEO') {
              content.urlOrPath = this.sanitizer.bypassSecurityTrustResourceUrl(
                `data:video/mp4;base64,${content.urlOrPath}`
              );
            } else if (content.contentType === 'PDF') {
              content.urlOrPath = this.sanitizer.bypassSecurityTrustResourceUrl(
                `data:application/pdf;base64,${content.urlOrPath}`
              );
            }
            return content;
          });
          // If this module is the first one, select its first document
          if (this.expandedModuleId === moduleId) {
            this.selectFirstDocument(moduleId);
          }
        },
        error: (err) =>
          console.error(`Error fetching content for module ${moduleId}:`, err),
      });
  }
  generateRandomProgressForModules(): void {
    const courseId=Number(this.route.snapshot.paramMap.get('courseId'));;
    const userId=+localStorage.getItem('userId')!;
    this.modules.forEach((module) => {
      const randomProgress = Math.floor(Math.random() * 91) + 10; // Generate a random number between 10 and 100
      this.progressData[module.moduleId] = randomProgress;

      // Save the random progress to the backend
      this.progressService
        .saveProgress(userId, courseId, module.moduleId, randomProgress)
        .subscribe({
          next: () =>
            console.log(
              `Progress ${randomProgress}% saved for module ${module.moduleId}`
            ),
          error: (err) =>
            console.error(
              `Error saving progress for module ${module.moduleId}:`,
              err
            ),
        });
    });
  }

  toggleModule(moduleId: number): void {
    this.expandedModuleId =
      this.expandedModuleId === moduleId ? null : moduleId;
      if (this.expandedModuleId) {
        this.selectFirstDocument(moduleId); // Automatically select the first document (PDF) when a module is expanded
      } else {
        this.selectedContent = null; // Clear selected content
      }
      this.isQuizSelected = false;
  }
  selectFirstDocument(moduleId: number): void {
    const contents = this.moduleContent[moduleId];
    if (contents && contents.length > 0) {
      // Find the first document (PDF) in the module's content
      const firstDocument = contents.find(
        (content) => content.contentType === 'PDF'
      );
      this.selectedContent = firstDocument || contents[0]; // If no PDF is found, fallback to the first content
    }
  }

  onContentSelect(content: any): void {
    this.selectedContent = content;
    this.isQuizSelected = false;
  }
  onQuizSelect(moduleId: number): void {
    const courseId = Number(this.route.snapshot.paramMap.get('courseId')); // Get courseId from route params
    this.isQuizSelected = true;
    this.selectedContent = true;
    this.questions = []; // Reset questions before fetching new data
    this.answers = []; // Reset answers
  
    console.log(courseId);
    console.log(moduleId);
    // Fetch quiz questions for the specific courseId and moduleId
    this.http.get<any[]>(`http://localhost:8091/api/quiz/${courseId}/${moduleId}`).subscribe({
      next: (data) => {
        if (data) {
          this.questions = data; // Store questions
          this.answers = this.questions.map((q) => ({ questionId: q.id, selectedOption: 0 })); // Initialize answers
        } else {
          this.questions = []; // Handle no data scenario
          console.warn('No questions found for this module');
        }
      },
      error: (err) => {
        console.error('Error fetching questions:', err);
        this.questions = []; // Ensure questions is not null in case of error
      },
    });
  }
  
  selectAnswer(questionId: number, selectedOption: number): void {
    const answer = this.answers.find((a) => a.questionId === questionId);
    if (answer) {
      answer.selectedOption = selectedOption;
    }
  }
  submitQuiz(): void {
    this.results = this.questions.map((q) => {
      const userAnswer = this.answers.find((a) => a.questionId === q.id);
      const isCorrect = !!(userAnswer && userAnswer.selectedOption === q.correctOption); // Ensure isCorrect is boolean
      return { questionId: q.id, isCorrect };
    });
  
    this.totalCorrect = this.results.filter((r) => r.isCorrect).length; // Calculate total correct answers
  }
  
  

}
