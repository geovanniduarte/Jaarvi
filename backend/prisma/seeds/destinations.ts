import { PrismaClient } from '@prisma/client';

export const destinationSeeds = [
  {
    country: { isoCode: 'FR', name: 'France' },
    cities: [
      {
        name: 'Paris',
        timezone: 'Europe/Paris',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
      {
        name: 'Lyon',
        timezone: 'Europe/Paris',
        coverage: { level: 'medium', notes: 'Basic guidance only' },
      },
    ],
  },
  {
    country: { isoCode: 'IT', name: 'Italy' },
    cities: [
      {
        name: 'Rome',
        timezone: 'Europe/Rome',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
      {
        name: 'Florence',
        timezone: 'Europe/Rome',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
      {
        name: 'Venice',
        timezone: 'Europe/Rome',
        coverage: { level: 'medium', notes: 'Expanding coverage' },
      },
    ],
  },
  {
    country: { isoCode: 'ES', name: 'Spain' },
    cities: [
      {
        name: 'Barcelona',
        timezone: 'Europe/Madrid',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
      {
        name: 'Madrid',
        timezone: 'Europe/Madrid',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
    ],
  },
  {
    country: { isoCode: 'JP', name: 'Japan' },
    cities: [
      {
        name: 'Tokyo',
        timezone: 'Asia/Tokyo',
        coverage: { level: 'medium', notes: 'Expanding coverage' },
      },
      {
        name: 'Kyoto',
        timezone: 'Asia/Tokyo',
        coverage: { level: 'medium', notes: 'Expanding coverage' },
      },
      {
        name: 'Osaka',
        timezone: 'Asia/Tokyo',
        coverage: { level: 'medium', notes: 'Expanding coverage' },
      },
    ],
  },
  {
    country: { isoCode: 'GB', name: 'United Kingdom' },
    cities: [
      {
        name: 'London',
        timezone: 'Europe/London',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
    ],
  },
  {
    country: { isoCode: 'US', name: 'United States' },
    cities: [
      {
        name: 'New York',
        timezone: 'America/New_York',
        coverage: { level: 'high', notes: 'Full playbooks available' },
      },
      {
        name: 'San Francisco',
        timezone: 'America/Los_Angeles',
        coverage: { level: 'medium', notes: 'Basic guidance' },
      },
    ],
  },
];

export async function seedDestinations(prisma: PrismaClient): Promise<void> {
  console.log('🗺️  Seeding destinations...');

  let countryCount = 0;
  let cityCount = 0;

  for (const dest of destinationSeeds) {
    // Upsert country
    const country = await prisma.country.upsert({
      where: { isoCode: dest.country.isoCode },
      update: { name: dest.country.name },
      create: dest.country,
    });
    countryCount++;
    console.log(`  ✓ ${country.name} (${country.isoCode})`);

    // Upsert cities
    for (const cityData of dest.cities) {
      const city = await prisma.city.upsert({
        where: {
          countryId_name: {
            countryId: country.id,
            name: cityData.name,
          },
        },
        update: { timezone: cityData.timezone },
        create: {
          countryId: country.id,
          name: cityData.name,
          timezone: cityData.timezone,
        },
      });

      // Upsert coverage
      await prisma.cityCoverage.upsert({
        where: { cityId: city.id },
        update: {
          level: cityData.coverage.level,
          notes: cityData.coverage.notes,
        },
        create: {
          cityId: city.id,
          level: cityData.coverage.level,
          notes: cityData.coverage.notes,
        },
      });

      cityCount++;
      console.log(`    • ${cityData.name} (${cityData.coverage.level} coverage)`);
    }
  }

  console.log(`  Total: ${countryCount} countries, ${cityCount} cities\n`);
}
