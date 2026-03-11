import { Router } from 'express';
import { healthRouter } from './healthRoutes';
import { tripRouter } from './tripRoutes';
import { destinationRouter } from './destinationRoutes';

/**
 * Main router aggregator.
 *
 * Combines all feature routers under the /api prefix.
 *
 * Route structure:
 * - /api/health        - Health check endpoints
 * - /api/trips         - Trip management endpoints
 * - /api/destinations  - City catalog endpoints
 */
const apiRouter = Router();

apiRouter.use('/health', healthRouter);
apiRouter.use('/trips', tripRouter);
apiRouter.use('/destinations', destinationRouter);

export { apiRouter };
