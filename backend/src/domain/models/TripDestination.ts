import { Prisma } from '@prisma/client';
import { getPrismaClient } from '../../infrastructure/prismaClient';
import { ConflictError } from '../errors';

export interface TripDestinationData {
  id?: string;
  tripId: string;
  cityId: string;
  dayOrder: number;
  daysCount: number;
  notes: string | null;
  createdAt?: Date;
  updatedAt?: Date;
  city?: unknown;
}

export class TripDestination {
  id?: string;
  tripId: string;
  cityId: string;
  dayOrder: number;
  daysCount: number;
  notes: string | null;
  createdAt?: Date;
  updatedAt?: Date;
  city?: unknown;

  constructor(data: TripDestinationData) {
    this.id = data.id;
    this.tripId = data.tripId;
    this.cityId = data.cityId;
    this.dayOrder = data.dayOrder;
    this.daysCount = data.daysCount;
    this.notes = data.notes;
    this.createdAt = data.createdAt;
    this.updatedAt = data.updatedAt;
    this.city = data.city;
  }

  async save(): Promise<TripDestination> {
    const prisma = getPrismaClient();

    try {
      const result = await prisma.tripDestination.create({
        data: {
          tripId: this.tripId,
          cityId: this.cityId,
          dayOrder: this.dayOrder,
          daysCount: this.daysCount,
          notes: this.notes,
        },
      });
      return new TripDestination(result as TripDestinationData);
    } catch (error) {
      if (
        error instanceof Prisma.PrismaClientKnownRequestError &&
        error.code === 'P2002'
      ) {
        throw new ConflictError('Destination with this day order already exists');
      }
      throw error;
    }
  }

  static async findByTripId(tripId: string): Promise<TripDestination[]> {
    const prisma = getPrismaClient();

    const results = await prisma.tripDestination.findMany({
      where: { tripId },
      include: {
        city: {
          include: { country: true },
        },
      },
      orderBy: { dayOrder: 'asc' },
    });

    return results.map((r) => new TripDestination(r as unknown as TripDestinationData));
  }

  static async findByTripIdAndOrder(
    tripId: string,
    dayOrder: number
  ): Promise<TripDestination | null> {
    const prisma = getPrismaClient();

    const result = await prisma.tripDestination.findFirst({
      where: { tripId, dayOrder },
      include: {
        city: {
          include: { country: true },
        },
      },
    });

    if (!result) return null;
    return new TripDestination(result as unknown as TripDestinationData);
  }
}
