/**
 * Base class for domain-specific errors.
 * Provides consistent error structure across the application.
 */
export abstract class DomainError extends Error {
  public readonly code: string;
  public readonly statusCode: number;

  constructor(message: string, code: string, statusCode: number) {
    super(message);
    this.name = this.constructor.name;
    this.code = code;
    this.statusCode = statusCode;

    // Maintains proper stack trace for where error was thrown
    Error.captureStackTrace(this, this.constructor);
  }
}

/**
 * Error thrown when a requested resource is not found.
 * Maps to HTTP 404 Not Found.
 */
export class NotFoundError extends DomainError {
  constructor(message = 'Resource not found') {
    super(message, 'NOT_FOUND', 404);
  }
}

/**
 * Error thrown when input validation fails.
 * Maps to HTTP 400 Bad Request.
 */
export class ValidationError extends DomainError {
  public readonly details: Record<string, string[]>;

  constructor(message = 'Validation failed', details: Record<string, string[]> = {}) {
    super(message, 'VALIDATION_ERROR', 400);
    this.details = details;
  }
}

/**
 * Error thrown when authentication fails.
 * Maps to HTTP 401 Unauthorized.
 */
export class UnauthorizedError extends DomainError {
  constructor(message = 'Unauthorized') {
    super(message, 'UNAUTHORIZED', 401);
  }
}

/**
 * Error thrown when the user lacks permission for an action.
 * Maps to HTTP 403 Forbidden.
 */
export class ForbiddenError extends DomainError {
  constructor(message = 'Access forbidden') {
    super(message, 'FORBIDDEN', 403);
  }
}

/**
 * Error thrown when a conflict occurs (e.g., duplicate resource).
 * Maps to HTTP 409 Conflict.
 */
export class ConflictError extends DomainError {
  constructor(message = 'Resource conflict') {
    super(message, 'CONFLICT', 409);
  }
}

/**
 * Error thrown for internal server errors.
 * Maps to HTTP 500 Internal Server Error.
 */
export class InternalError extends DomainError {
  constructor(message = 'Internal server error') {
    super(message, 'INTERNAL_ERROR', 500);
  }
}

/**
 * Type guard to check if an error is a DomainError
 */
export function isDomainError(error: unknown): error is DomainError {
  return error instanceof DomainError;
}
