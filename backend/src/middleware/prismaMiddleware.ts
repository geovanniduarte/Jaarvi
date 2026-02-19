import { Request, Response, NextFunction } from 'express';
import { PrismaClient } from '@prisma/client';
import { getPrismaClient } from '../infrastructure/prismaClient';

/**
 * Extends Express Request to include Prisma client
 */
declare module 'express-serve-static-core' {
  interface Request {
    prisma: PrismaClient;
  }
}

/**
 * Prisma middleware.
 *
 * Injects the Prisma client singleton into each request,
 * making it available as `req.prisma` in controllers.
 *
 * This follows the Dependency Injection pattern, allowing:
 * - Consistent database access across all handlers
 * - Easy mocking in tests
 * - Efficient connection pooling via singleton
 *
 * @example
 * // In a controller:
 * const users = await req.prisma.user.findMany();
 */
export function prismaMiddleware(req: Request, _res: Response, next: NextFunction): void {
  req.prisma = getPrismaClient();
  next();
}
