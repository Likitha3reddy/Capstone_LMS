import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { ReviewsService } from '../../services/reviews.service';
import { forkJoin } from 'rxjs';
import { MatProgressSpinner, MatProgressSpinnerModule } from '@angular/material/progress-spinner';
@Component({
  selector: 'app-view-modules',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule,MatProgressSpinner,MatProgressSpinnerModule],
  templateUrl: './view-modules.component.html',
  styleUrl: './view-modules.component.scss'
})
export class ViewModulesComponent implements OnInit {
  modules: any[] = [];
  moduleContent: { [key: number]: any[] } = {};
  expandedModuleId: number | null = null;
  courseId!: number;
  isPopupVisible = false;
  selectedModuleId!: number;
  uploadedFile: File | null = null;
  reviews: any[] = [];
  isLoading:boolean=false;

  constructor(private http: HttpClient,private route:ActivatedRoute,private sanitizer:DomSanitizer,private reviewsService:ReviewsService) {}

  ngOnInit(): void {
    this.courseId = Number(this.route.parent?.snapshot.paramMap.get('courseId'));
    this.getModulesByInstructorId(this.courseId); 
    this.loadReviews();
  }

  loadReviews(): void {
    this.reviewsService.getReviewsWithUserDetails(this.courseId).subscribe({
      next: (reviewsWithUsers) => {
        // Resolve all user observables
        forkJoin(
          reviewsWithUsers.map((review) => review.user$)
        ).subscribe((userDetails) => {
          // Merge user details into reviews
          this.reviews = reviewsWithUsers.map((review, index) => ({
            ...review,
            user: userDetails[index], // Replace user$ observable with actual user data
            
          }));
          console.log(this.reviews);
        });
      },
      error: (err) => {
        console.error('Error loading reviews:', err);
      },
    });
  }
  getModulesByInstructorId(courseId: number): void {
    this.isLoading=true;
    console.log(this.courseId);
    this.http
      .get<any[]>(`http://localhost:8091/modules/course/${this.courseId}`)
      .subscribe((data) => {
        this.modules = data;
        // Preload module content for each module
        console.log(data);
        data.forEach((module) => this.getContentByModuleId(module.moduleId));
        this.isLoading=false;
        
      });
  }

  getContentByModuleId(moduleId: number): void {
    this.http
      .get<any[]>(`http://localhost:8091/modules/contents/module/${moduleId}`)
      .subscribe({
        next: (data) => {
          this.moduleContent[moduleId] = data.map((content) => {
            // Use DomSanitizer to trust the URL
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
          console.log('Content fetched for module:', moduleId, this.moduleContent[moduleId]);
        },
        error: (err) => console.error(`Error fetching content for module ${moduleId}:`, err),
      });
  }
  
  getSafeContent(content: any): any {
    if (content.contentType === 'PDF') {
      // Add 'data:application/pdf;base64,' prefix for PDF content
      return this.sanitizer.bypassSecurityTrustResourceUrl(`data:application/pdf;base64,${content.urlOrPath}`);
    } else if (content.contentType === 'VIDEO') {
      // Add 'data:video/mp4;base64,' prefix for video content
      return this.sanitizer.bypassSecurityTrustResourceUrl(`data:video/mp4;base64,${content.urlOrPath}`);
    }
    return null; // Unsupported content type
  }
  toggleContent(moduleId: number): void {
    this.expandedModuleId = this.expandedModuleId === moduleId ? null : moduleId;
  }

  // toggleContent(moduleId: number): void {
  //   this.expandedModuleId =
  //     this.expandedModuleId === moduleId ? null : moduleId;
  // }
  
  openAddContentPopup(moduleId: number): void {
    this.isPopupVisible = true;
    this.selectedModuleId = moduleId;
  }

  closePopup(): void {
    this.isPopupVisible = false;
    this.uploadedFile = null;
  }

  handleFileUpload(event: any): void {
    this.uploadedFile = event.target.files[0];
  }

  submitContent(): void {
  if (this.uploadedFile) {
    const formData = new FormData();
    formData.append('file', this.uploadedFile);
    formData.append('moduleId', this.selectedModuleId.toString());

    const contentType = this.uploadedFile.type.includes('video') ? 'VIDEO' : 'PDF';
    formData.append('contentType', contentType);

    this.http
      .post(`http://localhost:8091/modules/${this.selectedModuleId}/contents`, formData)
      .subscribe(
        () => {
          alert('Content successfully added!');
          this.getContentByModuleId(this.selectedModuleId); // Refresh module content
          this.closePopup();
        },
        (error) => {
          console.error('Error adding content:', error);
          alert('Failed to add content. Please try again.');
        }
      );
  } else {
    alert('Please upload a file before submitting.');
  }

  
}

}



