import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ReviewsService {
  private reviewsApi = 'http://localhost:8091/api/reviews/course'; // Reviews API
  private userApi = 'http://localhost:8091/api/user'; // User API

  constructor(private http: HttpClient) {}

  // Fetch reviews by courseId
  getReviewsByCourseId(courseId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.reviewsApi}/${courseId}`);
  }

  // Fetch user details by userId
  getUserById(userId: number): Observable<any> {
    return this.http.get<any>(`${this.userApi}/${userId}`);
  }

  // Combine reviews with user details
  getReviewsWithUserDetails(courseId: number): Observable<any[]> {
    return this.getReviewsByCourseId(courseId).pipe(
      map((reviews) =>
        reviews.map((review) => ({
          ...review,
          user$: this.getUserById(review.userId), // Attach observable for user data
        }))
      )
    );
  }
}
