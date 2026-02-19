import { Router } from 'express';
import { healthRouter } from './healthRoutes';

/**
 * Main router aggregator.
 *
 * Combines all feature routers under the /api prefix.
 * New routes should be added here as features are implemented.
 *
 * Route structure:
 * - /api/health - Health check endpoints
 * - /api/auth   - Authentication endpoints (future)
 * - /api/users  - User management endpoints (future)
 * - /api/trips  - Trip management endpoints (future)
 */
const apiRouter = Router();

// Health check routes
apiRouter.use('/health', healthRouter);

// Future routes will be added here:
// apiRouter.use('/auth', authRouter);
// apiRouter.use('/users', userRouter);
// apiRouter.use('/trips', tripRouter);

export { apiRouter };
