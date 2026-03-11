import { Trip } from '../models/Trip';
import { TripDestination } from '../models/TripDestination';
import { TripPlanningContext } from '../models/TripPlanningContext';

export interface ITripRepository {
  findById(id: string): Promise<Trip | null>;
  findByOwnerId(ownerId: string, status?: string): Promise<Trip[]>;
  save(trip: Trip): Promise<Trip>;
  addDestination(destination: TripDestination): Promise<TripDestination>;
  savePlanningContext(context: TripPlanningContext): Promise<TripPlanningContext>;
}
