import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{

  user: any; // This will hold the user data

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Retrieve the userId from localStorage
    const userId = localStorage.getItem('userId');

    if (userId) {
      // Make an API call to fetch user details
      this.http.get(`http://localhost:8091/api/user/${userId}`).subscribe(
        (data: any) => {
          this.user = data;
          console.log(this.user);
        },
        (error) => {
          console.error('Error fetching user details:', error);
        }
      );
    } else {
      console.error('No userId found in localStorage.');
    }
  }
}
