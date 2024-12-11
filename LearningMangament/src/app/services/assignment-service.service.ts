import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AssignmentServiceService {
  private apiUrl = 'http://localhost:8091/api/assignments';
  private baseUrl='http://localhost:8091/api/submissions'

  constructor(private http: HttpClient) {}

  createAssignment(assignment: any): Observable<any> {
    return this.http.post(this.apiUrl, assignment);
  }

  // getAssignmentsByCourse(courseId: number): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.apiUrl}/course/${courseId}`);
  // }

  // getAssignmentsByInstructor(instructorId: number): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.apiUrl}/instructor/${instructorId}`);
  // }
  /* after adding to upgrade the grade */
   // Fetch assignments by instructor ID
   getAssignmentsByInstructorId(instructorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/assignments/instructor/${instructorId}`);
  }

  // Fetch submissions by instructor ID
  getSubmissionsByInstructorId(instructorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/instructor/${instructorId}`);
  }

  updateGrade(submissionId: number, grade: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/${submissionId}/grade`, { grade });
  }


  createQuiz(quizData: any[]): Observable<any> {
    return this.http.post(`http://localhost:8091/api/quiz/create`, quizData,{ responseType: 'text' });
  }
  
}
