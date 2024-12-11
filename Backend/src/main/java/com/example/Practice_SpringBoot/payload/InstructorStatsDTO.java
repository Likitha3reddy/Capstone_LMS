package com.example.Practice_SpringBoot.payload;

public class InstructorStatsDTO {
    private long coursesCreated;
    private long studentsEnrolled;

    public InstructorStatsDTO(long coursesCreated, long studentsEnrolled) {
        this.coursesCreated = coursesCreated;
        this.studentsEnrolled = studentsEnrolled;
    }

    public long getCoursesCreated() {
        return coursesCreated;
    }

    public long getStudentsEnrolled() {
        return studentsEnrolled;
    }
}
