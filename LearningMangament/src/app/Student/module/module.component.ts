import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { ProgressService } from '../../services/progress.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-module',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './module.component.html',
  styleUrls: ['./module.component.scss'],
})
export class ModuleComponent implements OnInit {
  feedback = {
    rating: 0,
    comment: '',
    courseId: 0,
    userId: 0,
  };
  isLoading: boolean = true;
  userId!: number;
  courseId!: number;

  modules: any[] = [];
  moduleContent: { [key: number]: any[] } = {};
  videoProgress: { [key: number]: { [key: number]: number } } = {};
  progressData: { [key: number]: number } = {};

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private sanitizer: DomSanitizer,
    private progressService: ProgressService
  ) {}

  ngOnInit(): void {
    this.courseId = Number(this.route.snapshot.paramMap.get('courseId'));
    const userId = localStorage.getItem('userId');

    if (userId) {
      this.userId = +userId;
      this.feedback.userId = this.userId;
    } else {
      console.error('User ID not found in localStorage');
    }

    this.feedback.courseId = this.courseId;

    this.getModules();
  }

  getModules(): void {
    this.isLoading = true; 
    this.http
      .get<any[]>(`http://localhost:8091/modules/course/${this.courseId}`)
      .subscribe({
        next: (data) => {
          this.modules = data;
          this.generateRandomProgressForModules();
          console.log(this.modules);
          data.forEach((module) => this.getContentByModuleId(module.moduleId));
          this.isLoading = false;
          console.log(this.modules);
          
        },
        error: (err) => {
          console.error('Error fetching modules:', err);
          this.isLoading = false;
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
        },
        error: (err) =>
          console.error(`Error fetching content for module ${moduleId}:`, err),
      });
  }
  getSafeContent(content: any): any {
    if (content.contentType === 'PDF') {
      return this.sanitizer.bypassSecurityTrustResourceUrl(`data:application/pdf;base64,${content.urlOrPath}`);
    } else if (content.contentType === 'VIDEO') {
      return this.sanitizer.bypassSecurityTrustResourceUrl(`data:video/mp4;base64,${content.urlOrPath}`);
    }
    return null;
  }

  trackVideoProgress(event: any, moduleId: number, contentId: number): void {
    const videoElement = event.target;
    const currentTime = videoElement.currentTime;
    const duration = videoElement.duration;

    const percentage = Math.floor((currentTime / duration) * 100);

    if (!this.videoProgress[moduleId]) {
      this.videoProgress[moduleId] = {};
    }

    this.videoProgress[moduleId][contentId] = percentage;

    // Save the updated progress to the backend
    this.calculateModuleProgress(moduleId);
  }
  markVideoComplete(moduleId: number, contentId: number): void {
    if (!this.videoProgress[moduleId]) {
      this.videoProgress[moduleId] = {};
    }

    this.videoProgress[moduleId][contentId] = 100; // Mark as complete
    this.calculateModuleProgress(moduleId);
  }

  calculateModuleProgress(moduleId: number): void {
    const moduleVideos = this.moduleContent[moduleId]?.filter(
      (content) => content.contentType === 'VIDEO'
    );
  
    if (!moduleVideos || moduleVideos.length === 0) return;
  
    const totalVideos = moduleVideos.length;
    let totalProgress = 0;
  
    moduleVideos.forEach((video) => {
      totalProgress += this.videoProgress[moduleId]?.[video.contentId] || 0;
    });
  
    const moduleProgressPercentage = Math.floor(totalProgress / totalVideos);
    this.progressData[moduleId] = moduleProgressPercentage;
    console.log(moduleId);
  
    // Save the module progress to the backend
    this.progressService.saveProgress(this.userId, this.courseId, moduleId, moduleProgressPercentage).subscribe({
      next: () => console.log(`Progress for module ${moduleId} saved successfully.`),
      error: (err: any) => console.error(`Error saving progress for module ${moduleId}:`, err), // Fixed the typing issue here
    });
  }


  generateRandomProgressForModules(): void {
    this.modules.forEach((module) => {
      const randomProgress = Math.floor(Math.random() * 91) + 10; // Generate a random number between 10 and 100
      this.progressData[module.moduleId] = randomProgress;

      // Save the random progress to the backend
      this.progressService
        .saveProgress(this.userId, this.courseId, module.moduleId, randomProgress)
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
  submitFeedback(): void {
    if (!this.feedback.rating || !this.feedback.comment.trim()) {
      alert('Please provide a rating and comment.');
      return;
    }

    this.http.post('http://localhost:8091/api/reviews', this.feedback).subscribe({
      next: () => {
        alert('Thank you for your feedback!');
        this.resetForm();
      },
      error: (err) => {
        console.error('Error submitting feedback:', err);
        alert('Failed to submit feedback. Please try again.');
      },
    });
  }

  resetForm(): void {
    this.feedback = {
      rating: 0,
      comment: '',
      courseId: this.courseId,
      userId: this.userId,
    };
  }
}
