import { getPrismaClient } from '../../infrastructure/prismaClient';

export interface TripPlanningContextData {
  id?: string;
  tripId: string;
  version: number;
  inputsSnapshot: Record<string, unknown>;
  coverageSnapshot: Record<string, unknown>[];
  createdAt?: Date;
}

export class TripPlanningContext {
  id?: string;
  tripId: string;
  version: number;
  inputsSnapshot: Record<string, unknown>;
  coverageSnapshot: Record<string, unknown>[];
  createdAt?: Date;

  constructor(data: TripPlanningContextData) {
    this.id = data.id;
    this.tripId = data.tripId;
    this.version = data.version;
    this.inputsSnapshot = data.inputsSnapshot;
    this.coverageSnapshot = data.coverageSnapshot;
    this.createdAt = data.createdAt;
  }

  async save(): Promise<TripPlanningContext> {
    const prisma = getPrismaClient();

    const result = await prisma.tripPlanningContext.create({
      data: {
        tripId: this.tripId,
        version: this.version,
        inputsSnapshot: this.inputsSnapshot as never,
        coverageSnapshot: this.coverageSnapshot as never,
      },
    });

    return new TripPlanningContext(result as unknown as TripPlanningContextData);
  }

  static async findByTripId(tripId: string): Promise<TripPlanningContext[]> {
    const prisma = getPrismaClient();

    const results = await prisma.tripPlanningContext.findMany({
      where: { tripId },
      orderBy: { version: 'desc' },
    });

    return results.map((r) => new TripPlanningContext(r as unknown as TripPlanningContextData));
  }

  static async getNextVersionForTrip(tripId: string): Promise<number> {
    const prisma = getPrismaClient();

    const result = await prisma.tripPlanningContext.aggregate({
      where: { tripId },
      _max: { version: true },
    });

    return (result._max.version ?? 0) + 1;
  }
}
