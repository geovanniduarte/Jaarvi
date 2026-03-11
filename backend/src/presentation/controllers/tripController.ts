import { Request, Response, NextFunction } from 'express';
import { getTripService, CreateTripData, AddDestinationData, PlanningContextPreferences } from '../../application/services/tripService';

interface AuthenticatedRequest extends Request {
  user: { id: string; email: string };
}

export class TripController {
  async createTrip(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
    try {
      const trip = await getTripService().createTrip(req.user.id, req.body as CreateTripData);
      res.status(201).json({ success: true, data: trip });
    } catch (error) {
      next(error);
    }
  }

  async getUserTrips(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
    try {
      const status = typeof req.query.status === 'string' ? req.query.status : undefined;
      const trips = await getTripService().getUserTrips(req.user.id, status);
      res.status(200).json({ success: true, data: trips });
    } catch (error) {
      next(error);
    }
  }

  async getTripById(req: AuthenticatedRequest, res: Response, next: NextFunction): Promise<void> {
    try {
      const trip = await getTripService().getTripById(req.params['id'] ?? '', req.user.id);
      res.status(200).json({ success: true, data: trip });
    } catch (error) {
      next(error);
    }
  }

  async addDestination(
    req: AuthenticatedRequest,
    res: Response,
    next: NextFunction
  ): Promise<void> {
    try {
      const destination = await getTripService().addDestination(
        req.params['id'] ?? '',
        req.user.id,
        req.body as AddDestinationData
      );
      res.status(201).json({ success: true, data: destination });
    } catch (error) {
      next(error);
    }
  }

  async savePlanningContext(
    req: AuthenticatedRequest,
    res: Response,
    next: NextFunction
  ): Promise<void> {
    try {
      const context = await getTripService().savePlanningContext(
        req.params['id'] ?? '',
        req.user.id,
        req.body as PlanningContextPreferences
      );
      res.status(201).json({ success: true, data: context });
    } catch (error) {
      next(error);
    }
  }
}

let tripControllerInstance: TripController | null = null;

export function getTripController(): TripController {
  if (tripControllerInstance === null) {
    tripControllerInstance = new TripController();
  }
  return tripControllerInstance;
}

export function resetTripController(): void {
  tripControllerInstance = null;
}
