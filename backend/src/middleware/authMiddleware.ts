import { Request, Response, NextFunction } from 'express';
import { UnauthorizedError } from '../domain/errors';

/**
 * STUB: Authenticates JWT tokens from the Authorization header.
 * This will be replaced by the real implementation from mvp-02.
 * DO NOT merge this stub to main.
 *
 * In production, this middleware verifies the JWT and attaches
 * req.user = { id: string; email: string } to the request.
 */
export function authenticateJWT(req: Request, _res: Response, next: NextFunction): void {
  const authHeader = req.headers.authorization;

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    next(new UnauthorizedError('Missing or invalid authorization header'));
    return;
  }

  const token = authHeader.split(' ')[1];

  if (!token) {
    next(new UnauthorizedError('Missing token'));
    return;
  }

  // STUB: hardcode a test user that exists in the seeded DB for local development only
  (req as Request & { user: { id: string; email: string } }).user = {
    id: '2afb5526-d8fc-41ff-a313-f90bbe3c91bd',
    email: 'test@jaarvi.app',
  };

  next();
}
