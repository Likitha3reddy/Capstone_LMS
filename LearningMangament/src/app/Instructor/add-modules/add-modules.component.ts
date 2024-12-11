import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-add-modules',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './add-modules.component.html',
  styleUrl: './add-modules.component.scss'
})
export class AddModulesComponent {
  module = {
    title: '',
    description: '',
  };
  courseId!: number;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Extract courseId from the URL
    this.courseId = Number(this.route.parent?.snapshot.paramMap.get('courseId'));
    console.log('Course ID:', this.courseId);
  }

  onSubmit(form: any): void {
    // console.log(this.module);
    // const course=+this.route.snapshot.paramMap.get('courseId')!;
    if (form.valid) {
      const apiUrl = `http://localhost:8091/modules/${this.courseId}`;

      // Perform POST request
      this.http.post(apiUrl, this.module).subscribe(
        (response) => {
          alert('Module added successfully!');
          this.router.navigate(['/instructor-dashboard/courses']);
        },
        (error) => {
          console.error('Error adding module:', error);
        }
      );
    }
  }

}
