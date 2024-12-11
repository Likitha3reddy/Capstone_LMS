import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CoursesService {
  private apiUrl = 'http://localhost:8091/courses'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  getAllCourses(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getModules(courseId: number): Observable<any> {
    return this.http.get(`http://localhost:8091/modules/course/${courseId}`);
  }
  
}
