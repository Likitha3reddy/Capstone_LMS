import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  private apiUrl = 'http://localhost:8091/api/enrollments'; // Adjust to your backend endpoint

  
  private apiurl = 'http://localhost:8091/api/enrollments/courses';
  constructor(private http: HttpClient) {}

  private courseCount: number = 0;

  createEnrollment(enrollmentData: { userId: number; courseId: number; status: string }): Observable<any> {
    return this.http.post(this.apiUrl, enrollmentData);
  }
  getCoursesByUserId(userId: number): Observable<any> {
    return this.http.get(`${this.apiurl}/${userId}`);
  }
  
/*
*/
  setCourseCount(count: number): void {
    this.courseCount = count;
  }

  getCourseCount(): number {
    return this.courseCount;
  }
}
