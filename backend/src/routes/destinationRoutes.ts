import { Router, Request, Response, NextFunction } from 'express';
import { authenticateJWT } from '../middleware/authMiddleware';
import { getDestinationController } from '../presentation/controllers/destinationController';

const destinationRouter = Router();

destinationRouter.get(
  '/countries',
  authenticateJWT,
  (req: Request, res: Response, next: NextFunction) => {
    void getDestinationController().getCountries(req, res, next);
  }
);

destinationRouter.get(
  '/cities',
  authenticateJWT,
  (req: Request, res: Response, next: NextFunction) => {
    void getDestinationController().getCities(req, res, next);
  }
);

export { destinationRouter };
