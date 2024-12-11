import { Routes } from '@angular/router';
import { HomeComponent } from './shared/home/home.component';
import { LoginComponent } from './shared/login/login.component';
import { SignupComponent } from './shared/signup/signup.component';
import { DashboardComponent } from './shared/dashboard/dashboard.component';
import { StudentDashboardComponent } from './Student/student-dashboard/student-dashboard.component';
import { InstructorDashboardComponent } from './Instructor/instructor-dashboard/instructor-dashboard.component';
import { authGuard } from './AuthService/auth.guard';
import { CoursesComponent } from './Student/courses/courses.component';
import { GradesComponent } from './Student/grades/grades.component';
import { ProfileComponent } from './Student/profile/profile.component';
import { ModuleComponent } from './Student/module/module.component';
import { InstructorCoursesComponent } from './Instructor/instructor-courses/instructor-courses.component';
import { InstructorPerformanceComponent } from './Instructor/instructor-performance/instructor-performance.component';
import { InstructorProfileComponent } from './Instructor/instructor-profile/instructor-profile.component';
import { CreateCourseComponent } from './Instructor/create-course/create-course.component';
import { AddModulesComponent } from './Instructor/add-modules/add-modules.component';
import { ViewModulesComponent } from './Instructor/view-modules/view-modules.component';
import { CreateAssignmentComponent } from './Instructor/create-assignment/create-assignment.component';
import { AssignmentComponent } from './Student/assignment/assignment.component';
import { ModuleLayoutComponent } from './Student/module-layout/module-layout.component';
import { SubmittedAssignmentsComponent } from './Instructor/submitted-assignments/submitted-assignments.component';
import { CourseoperationsComponent } from './Instructor/courseoperations/courseoperations.component';
import { CreateQuizComponent } from './Instructor/create-quiz/create-quiz.component';
export const routes: Routes = [
    {
        path:"",
        component:HomeComponent
    },
    {
        path:"login",
        component:LoginComponent
    },
    {
        path:"signup",
        component:SignupComponent
    },
    
    {
        path:"student-dashboard",
        component:StudentDashboardComponent,
        children:[
            {
                path:'profile',
                component:ProfileComponent
            },
            {
                path:'courses',
                component:CoursesComponent
            },
            {
                path:'grades',
                component:GradesComponent
            },
            {
                path:'course/:courseId/modules',
                // component:ModuleComponent
                component:ModuleLayoutComponent
            },
            {
                path:'assignments',
                component:AssignmentComponent
            },
            {
                path:'videos',
                component:ModuleComponent
            },
        ],
        canActivate: [authGuard],
        data: { role: 'student' },
    },
    {
        path:"instructor-dashboard",
        component:InstructorDashboardComponent,
        children:[
            {
                path:'profile',
                component:InstructorProfileComponent
            },
            {
                path:'courses',
                component:InstructorCoursesComponent,
                
            },
            {
                path:'courses/:courseId',
                component:CourseoperationsComponent,
                children:[
                    {
                        path: '',
                        redirectTo: 'addModule',
                        pathMatch: 'full', // Redirect to Add Modules by default
                    },
                    {
                        path:'addModule',
                        component:AddModulesComponent
                    },
                    {
                        path:'viewModule',
                        component:ViewModulesComponent
                    },
                    {
                        path:'createAssignment',
                        component:CreateAssignmentComponent
                    },
                    {
                        path:'submittedAss',
                        component:SubmittedAssignmentsComponent
                    }
                ]
            },
            {
                path:'createCourse',
                component:CreateCourseComponent
            },
            {
                path:'performance',
                component:InstructorPerformanceComponent
            },
            {
                path:':courseId/:moduleId/quiz',
                component:CreateQuizComponent
            }
        ],
        canActivate: [authGuard],
        data: { role: 'instructor' },
    },
    { path: '**', redirectTo: '/' },

];
