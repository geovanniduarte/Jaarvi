import cors, { CorsOptions } from 'cors';
import { RequestHandler } from 'express';
import { getLogger } from '../infrastructure/logger';

/**
 * Creates CORS middleware with configuration from environment.
 *
 * For mobile apps (React Native/Expo), CORS is typically not needed
 * since mobile apps don't run in a browser. However, we configure it for:
 * - Expo development server debugging
 * - Future web client support
 * - API testing tools
 *
 * @param allowedOrigins - Array of allowed origin URLs
 * @returns Configured CORS middleware
 */
export function createCorsMiddleware(allowedOrigins: string[]): RequestHandler {
  const logger = getLogger();

  const corsOptions: CorsOptions = {
    origin: (origin, callback) => {
      // Allow requests with no origin (mobile apps, Postman, curl, etc.)
      if (!origin) {
        callback(null, true);
        return;
      }

      // Check if origin is in allowed list
      if (allowedOrigins.includes(origin)) {
        callback(null, true);
        return;
      }

      // Log rejected origins for debugging
      logger.debug('CORS rejected origin', { origin, allowedOrigins });
      callback(new Error('Not allowed by CORS'));
    },
    credentials: true,
    methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization', 'X-Requested-With'],
    exposedHeaders: ['X-Total-Count', 'X-Page-Count'],
    maxAge: 86400, // 24 hours
  };

  logger.debug('CORS configured', { allowedOrigins });

  return cors(corsOptions);
}

/**
 * Default CORS middleware using environment configuration.
 * Should be called after config is initialized.
 */
export function getCorsMiddleware(): RequestHandler {
  const allowedOriginsEnv = process.env.ALLOWED_ORIGINS ?? '';
  const allowedOrigins = allowedOriginsEnv
    .split(',')
    .map((origin) => origin.trim())
    .filter((origin) => origin.length > 0);

  return createCorsMiddleware(allowedOrigins);
}
