import { Request, Response, NextFunction } from 'express';
import { UnauthorizedError } from '../domain/errors';
import { getPrismaClient } from '../infrastructure/prismaClient';

/** Seeded dev user used by the JWT stub when any Bearer token is sent. */
const STUB_USER_EMAIL = 'test@jaarvi.app';

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

  // STUB: attach the seeded test user (id from DB, not a fixed UUID)
  void (async () => {
    try {
      const user = await getPrismaClient().user.findUnique({
        where: { email: STUB_USER_EMAIL },
      });

      if (!user) {
        next(
          new UnauthorizedError(
            `Test user ${STUB_USER_EMAIL} not found. Run database seed (see deploy guide §8.8).`
          )
        );
        return;
      }

      (req as Request & { user: { id: string; email: string } }).user = {
        id: user.id,
        email: user.email,
      };

      next();
    } catch (error) {
      next(error);
    }
  })();
}
