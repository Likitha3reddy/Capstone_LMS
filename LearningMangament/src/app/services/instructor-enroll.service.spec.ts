import { TestBed } from '@angular/core/testing';

import { InstructorEnrollService } from './instructor-enroll.service';

describe('InstructorEnrollService', () => {
  let service: InstructorEnrollService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InstructorEnrollService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
