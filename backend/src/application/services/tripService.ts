import { Trip } from '../../domain/models/Trip';
import { TripDestination } from '../../domain/models/TripDestination';
import { TripPlanningContext } from '../../domain/models/TripPlanningContext';
import { City } from '../../domain/models/City';
import { NotFoundError, ForbiddenError } from '../../domain/errors';
import {
  validateCreateTrip,
  validateAddDestination,
  validatePlanningContext,
  isValidUUID,
} from '../validator';
import { ValidationError } from '../../domain/errors';

export interface CreateTripData {
  name?: string | null;
  startDate: string;
  endDate: string;
}

export interface AddDestinationData {
  cityId: string;
  dayOrder: number;
  daysCount: number;
}

export interface PlanningContextPreferences {
  travelStyle?: string | null;
  budget?: string | null;
  pace?: string | null;
  interests?: string[] | null;
  specialRequirements?: string | null;
}

export class TripService {
  async createTrip(ownerId: string, data: CreateTripData): Promise<Trip> {
    validateCreateTrip(data);

    const trip = new Trip({
      ownerId,
      name: data.name ?? null,
      startDate: new Date(data.startDate),
      endDate: new Date(data.endDate),
      status: 'draft',
    });

    return trip.save();
  }

  async getUserTrips(ownerId: string, status?: string): Promise<Trip[]> {
    return Trip.findByOwnerId(ownerId, status);
  }

  async getTripById(id: string, ownerId: string): Promise<Trip> {
    if (!isValidUUID(id)) {
      throw new ValidationError('Trip validation failed', { id: ['id must be a valid UUID'] });
    }

    const trip = await Trip.findById(id);

    if (!trip) {
      throw new NotFoundError('Trip not found');
    }

    if (trip.ownerId !== ownerId) {
      throw new ForbiddenError('Access denied to this trip');
    }

    return trip;
  }

  async addDestination(
    tripId: string,
    ownerId: string,
    data: AddDestinationData
  ): Promise<TripDestination> {
    validateAddDestination(data);

    const trip = await Trip.findById(tripId);
    if (!trip) {
      throw new NotFoundError('Trip not found');
    }
    if (trip.ownerId !== ownerId) {
      throw new ForbiddenError('Access denied to this trip');
    }

    const city = await City.findById(data.cityId);
    if (!city) {
      throw new NotFoundError('City not found');
    }

    const destination = new TripDestination({
      tripId,
      cityId: data.cityId,
      dayOrder: data.dayOrder,
      daysCount: data.daysCount,
      notes: null,
    });

    await destination.save();

    const saved = await TripDestination.findByTripIdAndOrder(tripId, data.dayOrder);
    return saved!;
  }

  async savePlanningContext(
    tripId: string,
    ownerId: string,
    preferences: PlanningContextPreferences
  ): Promise<TripPlanningContext> {
    validatePlanningContext(preferences);

    const trip = await Trip.findById(tripId);
    if (!trip) {
      throw new NotFoundError('Trip not found');
    }
    if (trip.ownerId !== ownerId) {
      throw new ForbiddenError('Access denied to this trip');
    }

    const { travelStyle, budget, pace, interests, specialRequirements } = preferences;
    const inputsSnapshot = { preferences: { travelStyle, budget, pace, interests, specialRequirements } };

    const destinations = await TripDestination.findByTripId(tripId);
    const coverageSnapshot = await Promise.all(
      destinations.map(async (dest) => {
        const city = await City.findById(dest.cityId);
        const coverage = city?.coverage ?? null;
        return {
          cityId: dest.cityId,
          cityName: city?.name ?? dest.cityId,
          level: coverage?.level ?? null,
          notes: coverage?.notes ?? null,
        };
      })
    );

    const version = await TripPlanningContext.getNextVersionForTrip(tripId);

    const context = new TripPlanningContext({
      tripId,
      version,
      inputsSnapshot,
      coverageSnapshot,
    });

    return context.save();
  }
}

let tripServiceInstance: TripService | null = null;

export function getTripService(): TripService {
  if (tripServiceInstance === null) {
    tripServiceInstance = new TripService();
  }
  return tripServiceInstance;
}

export function resetTripService(): void {
  tripServiceInstance = null;
}
