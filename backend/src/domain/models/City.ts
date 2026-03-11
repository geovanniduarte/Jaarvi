import { getPrismaClient } from '../../infrastructure/prismaClient';
import { Country } from './Country';

export interface CityData {
  id: string;
  countryId: string;
  name: string;
  timezone: string;
  createdAt: Date;
  country?: unknown;
  coverage?: { level: string; notes: string | null } | null;
}

export class City {
  id: string;
  countryId: string;
  name: string;
  timezone: string;
  createdAt: Date;
  country?: Country;
  coverage?: { level: string; notes: string | null } | null;

  constructor(data: CityData) {
    this.id = data.id;
    this.countryId = data.countryId;
    this.name = data.name;
    this.timezone = data.timezone;
    this.createdAt = data.createdAt;
    this.country = data.country ? new Country(data.country as ConstructorParameters<typeof Country>[0]) : undefined;
    this.coverage = data.coverage ?? null;
  }

  static async findAll(countryId?: string): Promise<City[]> {
    const prisma = getPrismaClient();

    const results = await prisma.city.findMany({
      where: countryId ? { countryId } : {},
      include: {
        country: true,
        coverage: true,
      },
      orderBy: { name: 'asc' },
    });

    return results.map((r) => new City(r as unknown as CityData));
  }

  static async findById(id: string): Promise<City | null> {
    const prisma = getPrismaClient();

    const result = await prisma.city.findUnique({
      where: { id },
      include: {
        country: true,
        coverage: true,
      },
    });

    if (!result) return null;
    return new City(result as unknown as CityData);
  }
}
