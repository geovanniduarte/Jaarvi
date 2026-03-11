import { getPrismaClient } from '../../infrastructure/prismaClient';

export interface TripData {
  id?: string;
  ownerId: string;
  name: string | null;
  startDate: Date;
  endDate: Date;
  status: string;
  createdAt?: Date;
  updatedAt?: Date;
  destinations?: unknown[];
}

export class Trip {
  id?: string;
  ownerId: string;
  name: string | null;
  startDate: Date;
  endDate: Date;
  status: string;
  createdAt?: Date;
  updatedAt?: Date;
  destinations?: unknown[];

  constructor(data: TripData) {
    this.id = data.id;
    this.ownerId = data.ownerId;
    this.name = data.name;
    this.startDate = data.startDate;
    this.endDate = data.endDate;
    this.status = data.status;
    this.createdAt = data.createdAt;
    this.updatedAt = data.updatedAt;
    this.destinations = data.destinations;
  }

  async save(): Promise<Trip> {
    const prisma = getPrismaClient();

    if (this.id) {
      const result = await prisma.trip.update({
        where: { id: this.id },
        data: {
          name: this.name,
          startDate: this.startDate,
          endDate: this.endDate,
          status: this.status,
        },
      });
      return new Trip(result as TripData);
    }

    const result = await prisma.trip.create({
      data: {
        ownerId: this.ownerId,
        name: this.name,
        startDate: this.startDate,
        endDate: this.endDate,
        status: this.status,
      },
    });
    return new Trip(result as TripData);
  }

  static async findById(id: string): Promise<Trip | null> {
    const prisma = getPrismaClient();

    const result = await prisma.trip.findUnique({
      where: { id },
      include: {
        destinations: {
          include: {
            city: {
              include: { country: true },
            },
          },
          orderBy: { dayOrder: 'asc' },
        },
      },
    });

    if (!result) return null;
    return new Trip(result as unknown as TripData);
  }

  static async findByOwnerId(ownerId: string, status?: string): Promise<Trip[]> {
    const prisma = getPrismaClient();

    const results = await prisma.trip.findMany({
      where: {
        ownerId,
        ...(status ? { status } : {}),
      },
      orderBy: { startDate: 'desc' },
    });

    return results.map((r) => new Trip(r as TripData));
  }
}
