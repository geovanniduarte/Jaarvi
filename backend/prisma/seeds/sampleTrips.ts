import { PrismaClient } from '@prisma/client';

interface TripSeed {
  name: string;
  ownerEmail: string;
  startDate: Date;
  endDate: Date;
  status: string;
  destinations: Array<{
    cityName: string;
    dayOrder: number;
    daysCount: number;
    notes?: string;
  }>;
}

export const sampleTripSeeds: TripSeed[] = [
  {
    name: 'France & Italy Adventure',
    ownerEmail: 'test@jaarvi.app',
    startDate: new Date('2026-03-15'),
    endDate: new Date('2026-03-28'),
    status: 'draft',
    destinations: [
      {
        cityName: 'Paris',
        dayOrder: 1,
        daysCount: 5,
        notes: 'Explore the City of Light',
      },
      {
        cityName: 'Rome',
        dayOrder: 2,
        daysCount: 4,
        notes: 'Ancient history and amazing food',
      },
      {
        cityName: 'Florence',
        dayOrder: 3,
        daysCount: 4,
        notes: 'Renaissance art and architecture',
      },
    ],
  },
  {
    name: 'Japan Discovery',
    ownerEmail: 'demo@jaarvi.app',
    startDate: new Date('2026-04-05'),
    endDate: new Date('2026-04-19'),
    status: 'active',
    destinations: [
      {
        cityName: 'Tokyo',
        dayOrder: 1,
        daysCount: 7,
        notes: 'Modern metropolis',
      },
      {
        cityName: 'Kyoto',
        dayOrder: 2,
        daysCount: 4,
        notes: 'Traditional temples and gardens',
      },
      {
        cityName: 'Osaka',
        dayOrder: 3,
        daysCount: 3,
        notes: 'Street food paradise',
      },
    ],
  },
];

export async function seedSampleTrips(prisma: PrismaClient): Promise<void> {
  console.log('✈️  Seeding sample trips...');

  for (const tripData of sampleTripSeeds) {
    // Find owner
    const owner = await prisma.user.findUnique({
      where: { email: tripData.ownerEmail },
    });

    if (!owner) {
      console.log(
        `  ⚠️  Skipping "${tripData.name}" - owner not found: ${tripData.ownerEmail}`,
      );
      continue;
    }

    // Check if trip already exists
    const existingTrip = await prisma.trip.findFirst({
      where: {
        ownerId: owner.id,
        name: tripData.name,
      },
    });

    if (existingTrip) {
      console.log(`  ⏭️  "${tripData.name}" already exists, skipping`);
      continue;
    }

    // Create trip
    const trip = await prisma.trip.create({
      data: {
        ownerId: owner.id,
        name: tripData.name,
        startDate: tripData.startDate,
        endDate: tripData.endDate,
        status: tripData.status,
      },
    });

    console.log(`  ✓ ${tripData.name}`);

    // Add destinations
    for (const destData of tripData.destinations) {
      const city = await prisma.city.findFirst({
        where: { name: destData.cityName },
      });

      if (city) {
        await prisma.tripDestination.create({
          data: {
            tripId: trip.id,
            cityId: city.id,
            dayOrder: destData.dayOrder,
            daysCount: destData.daysCount,
            notes: destData.notes,
          },
        });
        console.log(`    • ${destData.cityName} (${destData.daysCount} days)`);
      }
    }
  }

  console.log(`  Total: ${sampleTripSeeds.length} sample trips\n`);
}
