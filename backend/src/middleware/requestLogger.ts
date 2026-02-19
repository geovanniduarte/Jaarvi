import { Request, Response, NextFunction } from 'express';
import { getLogger } from '../infrastructure/logger';

/**
 * Request logging middleware.
 *
 * Logs incoming HTTP requests with relevant metadata including:
 * - HTTP method
 * - Request path
 * - Response status code
 * - Response time in milliseconds
 *
 * Uses the 'http' log level for request logs.
 */
export function requestLogger(req: Request, res: Response, next: NextFunction): void {
  const logger = getLogger();
  const startTime = Date.now();

  // Log request received
  logger.http('Request received', {
    method: req.method,
    path: req.path,
    query: Object.keys(req.query).length > 0 ? req.query : undefined,
    ip: req.ip,
    userAgent: req.get('user-agent'),
  });

  // Capture response finish to log response details
  res.on('finish', () => {
    const duration = Date.now() - startTime;

    logger.http('Request completed', {
      method: req.method,
      path: req.path,
      statusCode: res.statusCode,
      duration: `${duration}ms`,
    });
  });

  next();
}
