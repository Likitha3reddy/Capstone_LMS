import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-courseoperations',
  standalone: true,
  imports: [RouterModule,CommonModule,FormsModule],
  templateUrl: './courseoperations.component.html',
  styleUrl: './courseoperations.component.scss'
})
export class CourseoperationsComponent {

}
