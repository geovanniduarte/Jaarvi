import { Request, Response, NextFunction } from 'express';
import { DomainError, isDomainError, ValidationError } from '../domain/errors';
import { getLogger } from '../infrastructure/logger';

/**
 * Standard error response structure
 */
interface ErrorResponse {
  success: false;
  error: {
    message: string;
    code: string;
    details?: Record<string, string[]>;
  };
}

/**
 * Creates a standardized error response
 */
function createErrorResponse(
  message: string,
  code: string,
  details?: Record<string, string[]>
): ErrorResponse {
  const response: ErrorResponse = {
    success: false,
    error: {
      message,
      code,
    },
  };

  if (details && Object.keys(details).length > 0) {
    response.error.details = details;
  }

  return response;
}

/**
 * Global error handling middleware.
 *
 * Catches all errors thrown in request handlers and returns
 * consistent error responses. Logs errors appropriately and
 * ensures internal error details are not exposed to clients.
 *
 * @param err - The error object
 * @param _req - Express request object
 * @param res - Express response object
 * @param _next - Express next function
 */
export function errorHandler(
  err: Error,
  _req: Request,
  res: Response,
  _next: NextFunction
): void {
  const logger = getLogger();

  // Handle domain errors (known, expected errors)
  if (isDomainError(err)) {
    const domainError: DomainError = err;

    // Log validation errors at debug level, others at warn
    if (domainError.statusCode === 400) {
      logger.debug('Validation error', {
        code: domainError.code,
        message: domainError.message,
      });
    } else {
      logger.warn('Domain error', {
        code: domainError.code,
        message: domainError.message,
        statusCode: domainError.statusCode,
      });
    }

    // Include validation details if present
    const details = err instanceof ValidationError ? err.details : undefined;

    res.status(domainError.statusCode).json(
      createErrorResponse(domainError.message, domainError.code, details)
    );
    return;
  }

  // Handle unexpected errors (bugs, system errors)
  logger.error('Unexpected error', {
    name: err.name,
    message: err.message,
    stack: err.stack,
  });

  // Don't expose internal error details in production
  const isProduction = process.env.NODE_ENV === 'production';
  const message = isProduction ? 'Internal server error' : err.message;

  res.status(500).json(createErrorResponse(message, 'INTERNAL_ERROR'));
}

/**
 * Middleware to handle 404 Not Found for unmatched routes
 */
export function notFoundHandler(req: Request, res: Response): void {
  const logger = getLogger();

  logger.debug('Route not found', {
    method: req.method,
    path: req.path,
  });

  res.status(404).json(
    createErrorResponse(`Cannot ${req.method} ${req.path}`, 'NOT_FOUND')
  );
}
