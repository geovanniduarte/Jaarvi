import { Request, Response, NextFunction } from 'express';
import { getDestinationService } from '../../application/services/destinationService';

export class DestinationController {
  async getCountries(_req: Request, res: Response, next: NextFunction): Promise<void> {
    try {
      const countries = await getDestinationService().getCountries();
      res.status(200).json({ success: true, data: countries });
    } catch (error) {
      next(error);
    }
  }

  async getCities(req: Request, res: Response, next: NextFunction): Promise<void> {
    try {
      const countryId = typeof req.query.countryId === 'string' ? req.query.countryId : undefined;
      const cities = await getDestinationService().getCities(countryId);
      res.status(200).json({ success: true, data: cities });
    } catch (error) {
      next(error);
    }
  }
}

let destinationControllerInstance: DestinationController | null = null;

export function getDestinationController(): DestinationController {
  if (destinationControllerInstance === null) {
    destinationControllerInstance = new DestinationController();
  }
  return destinationControllerInstance;
}

export function resetDestinationController(): void {
  destinationControllerInstance = null;
}
