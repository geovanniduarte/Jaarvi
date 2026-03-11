import { Router, Request, Response, NextFunction } from 'express';
import { authenticateJWT } from '../middleware/authMiddleware';
import { getTripController } from '../presentation/controllers/tripController';

interface AuthenticatedRequest extends Request {
  user: { id: string; email: string };
}

const tripRouter = Router();

tripRouter.post('/', authenticateJWT, (req: Request, res: Response, next: NextFunction) => {
  void getTripController().createTrip(req as AuthenticatedRequest, res, next);
});

tripRouter.get('/', authenticateJWT, (req: Request, res: Response, next: NextFunction) => {
  void getTripController().getUserTrips(req as AuthenticatedRequest, res, next);
});

tripRouter.get('/:id', authenticateJWT, (req: Request, res: Response, next: NextFunction) => {
  void getTripController().getTripById(req as AuthenticatedRequest, res, next);
});

tripRouter.post(
  '/:id/destinations',
  authenticateJWT,
  (req: Request, res: Response, next: NextFunction) => {
    void getTripController().addDestination(req as AuthenticatedRequest, res, next);
  }
);

tripRouter.post(
  '/:id/planning-context',
  authenticateJWT,
  (req: Request, res: Response, next: NextFunction) => {
    void getTripController().savePlanningContext(req as AuthenticatedRequest, res, next);
  }
);

export { tripRouter };
