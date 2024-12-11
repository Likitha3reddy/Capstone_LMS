import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject,Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProgressService {
  private progressData = new BehaviorSubject<{ [videoId: string]: number }>({});
  progress$ = this.progressData.asObservable();
  private apiUrl = 'http://localhost:8091/api/progress-reports';
  constructor(private http:HttpClient){}

  // Update progress for a specific video
  updateProgress(videoId: string, percentage: number): void {
    const currentData = this.progressData.value;
    this.progressData.next({ ...currentData, [videoId]: percentage });
  }

  // Get progress for a specific video
  getProgress(videoId: string): number {
    return this.progressData.value[videoId] || 0;
  }
  saveProgress(
    userId: number,
    courseId: number,
    moduleId: number,
    progressPercentage: number
  ): Observable<any> {
    const progressData = {
      userId,
      courseId,
      moduleId,
      progressPercentage,
    };

    return this.http.post(`${this.apiUrl}/save`, progressData);
  }
}
