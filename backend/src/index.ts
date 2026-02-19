import express, { Application } from 'express';
import helmet from 'helmet';
import { getConfig } from './infrastructure/config';
import { getLogger } from './infrastructure/logger';
import { disconnectPrisma } from './infrastructure/prismaClient';
import { apiRouter } from './routes';
import { errorHandler, notFoundHandler } from './middleware/errorHandler';
import { requestLogger } from './middleware/requestLogger';
import { getCorsMiddleware } from './middleware/corsMiddleware';
import { prismaMiddleware } from './middleware/prismaMiddleware';

/**
 * Creates and configures the Express application.
 *
 * @returns Configured Express application
 */
export function createApp(): Application {
  const app = express();

  // Security middleware
  app.use(helmet());

  // CORS middleware
  app.use(getCorsMiddleware());

  // Body parsing middleware
  app.use(express.json());
  app.use(express.urlencoded({ extended: true }));

  // Request logging middleware
  app.use(requestLogger);

  // Prisma client injection
  app.use(prismaMiddleware);

  // API routes
  app.use('/api', apiRouter);

  // 404 handler for unmatched routes
  app.use(notFoundHandler);

  // Global error handler (must be last)
  app.use(errorHandler);

  return app;
}

/**
 * Starts the server and sets up graceful shutdown.
 */
function startServer(): void {
  const config = getConfig();
  const logger = getLogger();

  const app = createApp();

  const server = app.listen(config.server.port, config.server.host, () => {
    logger.info('Server started', {
      host: config.server.host,
      port: config.server.port,
      environment: config.server.nodeEnv,
      url: `http://${config.server.host}:${config.server.port}`,
    });
    logger.info('Health check available at /api/health');
  });

  // Graceful shutdown handlers
  const shutdown = (signal: string): void => {
    logger.info(`Received ${signal}, starting graceful shutdown...`);

    server.close(() => {
      logger.info('HTTP server closed');

      disconnectPrisma()
        .then(() => {
          logger.info('Graceful shutdown completed');
          process.exit(0);
        })
        .catch((error) => {
          logger.error('Error during shutdown', {
            error: error instanceof Error ? error.message : 'Unknown error',
          });
          process.exit(1);
        });
    });

    // Force exit after timeout
    setTimeout(() => {
      logger.warn('Forced shutdown after timeout');
      process.exit(1);
    }, 10000);
  };

  process.on('SIGTERM', () => void shutdown('SIGTERM'));
  process.on('SIGINT', () => void shutdown('SIGINT'));

  // Handle uncaught exceptions
  process.on('uncaughtException', (error) => {
    logger.error('Uncaught exception', {
      error: error.message,
      stack: error.stack,
    });
    process.exit(1);
  });

  // Handle unhandled promise rejections
  process.on('unhandledRejection', (reason, promise) => {
    logger.error('Unhandled rejection', {
      reason: reason instanceof Error ? reason.message : String(reason),
      promise: String(promise),
    });
  });
}

// Start server if this is the main module
if (require.main === module) {
  startServer();
}

// Export for testing
export { startServer };
