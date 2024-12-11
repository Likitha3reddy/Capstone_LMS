import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../AuthService/auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule, RouterModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signupForm: FormGroup;
  private apiUrl = 'http://localhost:8091/api/user/register';

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router,private authService:AuthService) {
    this.signupForm = this.fb.group({
      userName: ['', [Validators.required, Validators.minLength(3)]],
      passwordHash: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      role: ['student'], 
      isActive: [true]   
    });
  }

  login() {
    this.router.navigate(['/login']);
  }
  onSubmit() {
    if (this.signupForm.invalid) {
      alert('Please fill in all required fields correctly.');
      return;
    }
  
    // Create a FormData object
    const formData = new FormData();
    formData.append('userName', this.signupForm.value.userName);
    formData.append('passwordHash', this.signupForm.value.passwordHash);
    formData.append('role', this.signupForm.value.role);
    formData.append('email', this.signupForm.value.email);
    formData.append('phoneNumber', this.signupForm.value.phoneNumber);
    formData.append('isActive', this.signupForm.value.isActive ? 'true' : 'false');
  
    console.log('FormData being sent:', formData);
  
    // Set responseType to 'text'
    this.http.post(this.apiUrl, formData, { responseType: 'text' }).subscribe({
      next: (res) => {
        // console.log('Response:', res); // Log plain text response
        alert("Please enter valid details"); // Show the plain text message
        this.login();
      },
      error: (err) => {
        console.error('Error during registration:', err);
        alert('An error occurred: ' + (err.error || 'Please try again.'));
      }
    });
  }
  
  
}
