import { PrismaClient } from '@prisma/client';
import { seedActivityTypes } from './seeds/activityTypes';
import { seedDestinations } from './seeds/destinations';
import { seedTestUsers } from './seeds/users';
import { seedSampleTrips } from './seeds/sampleTrips';

const prisma = new PrismaClient();

async function main() {
  console.log('🌱 Starting database seeding...\n');

  // 1. Activity Types (Required - always run)
  await seedActivityTypes(prisma);

  // 2. Destinations (Required - always run)
  await seedDestinations(prisma);

  // 3. Test Users (Development only)
  if (process.env.NODE_ENV === 'development') {
    await seedTestUsers(prisma);

    // 4. Sample Trips (Development only, requires users)
    await seedSampleTrips(prisma);
  } else {
    console.log(
      '⏭️  Skipping test users and sample trips (not in development mode)\n',
    );
  }

  console.log('✅ Database seeding completed successfully!\n');
}

main()
  .catch((e) => {
    console.error('❌ Error during seeding:', e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
