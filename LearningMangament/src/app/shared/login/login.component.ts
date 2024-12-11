import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../AuthService/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  passwordInputType: string = 'password'; // For toggling password visibility
  private apiUrl = 'http://localhost:8091/api/user/login'; // Backend login API

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router,private authService:AuthService) {}

  ngOnInit() {
    // Initialize the form group
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      passwordHash: ['', Validators.required]
    });
  }

  // Navigate to login route
  loginNav() {
    this.router.navigate(['/login']);
  }
  
  // Toggle password visibility
  togglePasswordVisibility(event: any) {
    this.passwordInputType = event.target.checked ? 'text' : 'password';
  }

  // Handle form submission
  loginToDashboard() {
    if (this.loginForm.invalid) {
      alert('Please fill in all required fields.');
      return;
    }
  
    // Prepare form data
    const formData = new FormData();
    formData.append('userName', this.loginForm.value.userName);
    formData.append('passwordHash', this.loginForm.value.passwordHash);
  
    // Make POST request
    this.http.post(this.apiUrl, formData).subscribe({
      next: (response: any) => {
        // Extract userId and role from the response
        const userId = response.userId;
        const role = response.role.toLowerCase();
  
        // Store userId and role in localStorage
        this.authService.storeUserId(userId, role);
  
        alert('Login successful!');
  
        // Redirect to appropriate dashboard
        if (role === 'student') {
          // console.log("is this going into");
          this.router.navigate(['/student-dashboard/courses']);
        } else if (role === 'instructor') {
          this.router.navigate(['/instructor-dashboard/courses']);
        } else {
          this.router.navigate(['/']); // Redirect to home for unknown roles
        }
      },
      error: (err) => {
        console.error('Error during login:', err);
        alert('Invalid credentials . Please try again.');
      },
    });
  }
}
