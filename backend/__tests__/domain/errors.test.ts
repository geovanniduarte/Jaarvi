import {
  NotFoundError,
  ValidationError,
  UnauthorizedError,
  ForbiddenError,
  ConflictError,
  InternalError,
  isDomainError,
} from '../../src/domain/errors';

describe('Domain Errors', () => {
  describe('UnauthorizedError', () => {
    it('should have default message and status 401', () => {
      const err = new UnauthorizedError();
      expect(err.message).toBe('Unauthorized');
      expect(err.statusCode).toBe(401);
      expect(err.code).toBe('UNAUTHORIZED');
    });

    it('should accept custom message', () => {
      const err = new UnauthorizedError('Token expired');
      expect(err.message).toBe('Token expired');
    });
  });

  describe('InternalError', () => {
    it('should have default message and status 500', () => {
      const err = new InternalError();
      expect(err.message).toBe('Internal server error');
      expect(err.statusCode).toBe(500);
      expect(err.code).toBe('INTERNAL_ERROR');
    });

    it('should accept custom message', () => {
      const err = new InternalError('Database failure');
      expect(err.message).toBe('Database failure');
    });
  });

  describe('isDomainError', () => {
    it('should return true for domain errors', () => {
      expect(isDomainError(new NotFoundError())).toBe(true);
      expect(isDomainError(new ValidationError())).toBe(true);
      expect(isDomainError(new UnauthorizedError())).toBe(true);
      expect(isDomainError(new ForbiddenError())).toBe(true);
      expect(isDomainError(new ConflictError())).toBe(true);
      expect(isDomainError(new InternalError())).toBe(true);
    });

    it('should return false for non-domain errors', () => {
      expect(isDomainError(new Error('generic'))).toBe(false);
      expect(isDomainError(null)).toBe(false);
      expect(isDomainError('string error')).toBe(false);
    });
  });
});
