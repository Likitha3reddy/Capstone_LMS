import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class StudentserviceService {
  private apiUrl = 'http://localhost:8091/api/students';
  private apiurl="http://localhost:8091/api/submissions"

  constructor(private http: HttpClient) {}

  getAssignments(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${userId}/assignments`);
  }

  submitAssignment(submission: any): Observable<any> {
    return this.http.post(`${this.apiurl}/submit`, submission);
  }
}
