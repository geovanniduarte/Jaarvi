import { Country } from '../../domain/models/Country';
import { City } from '../../domain/models/City';
import { isValidUUID } from '../validator';
import { ValidationError } from '../../domain/errors';

export class DestinationService {
  async getCountries(): Promise<Country[]> {
    return Country.findAll();
  }

  async getCities(countryId?: string): Promise<City[]> {
    if (countryId !== undefined) {
      if (!isValidUUID(countryId)) {
        throw new ValidationError('Destination validation failed', {
          countryId: ['countryId must be a valid UUID'],
        });
      }
    }
    return City.findAll(countryId);
  }
}

let destinationServiceInstance: DestinationService | null = null;

export function getDestinationService(): DestinationService {
  if (destinationServiceInstance === null) {
    destinationServiceInstance = new DestinationService();
  }
  return destinationServiceInstance;
}

export function resetDestinationService(): void {
  destinationServiceInstance = null;
}
