import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstructorEnrollService {
  private apiUrl = 'http://localhost:8091/api/combined';

  constructor(private http: HttpClient) {}

  getCoursesWithEnrollmentCounts(instructorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/instructor/${instructorId}`);
  }
}
