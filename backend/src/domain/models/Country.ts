import { getPrismaClient } from '../../infrastructure/prismaClient';

export interface CountryData {
  id: string;
  isoCode: string;
  name: string;
  createdAt: Date;
}

export class Country {
  id: string;
  isoCode: string;
  name: string;
  createdAt: Date;

  constructor(data: CountryData) {
    this.id = data.id;
    this.isoCode = data.isoCode;
    this.name = data.name;
    this.createdAt = data.createdAt;
  }

  static async findAll(): Promise<Country[]> {
    const prisma = getPrismaClient();

    const results = await prisma.country.findMany({
      orderBy: { name: 'asc' },
    });

    return results.map((r) => new Country(r as CountryData));
  }
}
