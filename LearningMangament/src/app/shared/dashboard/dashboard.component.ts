import { CommonModule } from '@angular/common';
import { Component,input,computed } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SideNavbarInstructorComponent } from '../../Instructor/side-navbar-instructor/side-navbar-instructor.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule,CommonModule,FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  isLeftSidebarCollapsed = input.required<boolean>();
  screenWidth = input.required<number>();
  sizeClass = computed(() => {
    const isLeftSidebarCollapsed = this.isLeftSidebarCollapsed();
    if (isLeftSidebarCollapsed) {
      return '';
    }
    return this.screenWidth() > 768 ? 'body-trimmed' : 'body-md-screen';
  });

}
