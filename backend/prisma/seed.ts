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

  // 3. Test Users (development or explicit opt-in for K8s lab DBs)
  const seedDevData =
    process.env.NODE_ENV === 'development' || process.env.SEED_TEST_USERS === 'true';

  if (seedDevData) {
    await seedTestUsers(prisma);

    // 4. Sample Trips (requires users)
    await seedSampleTrips(prisma);
  } else {
    console.log(
      '⏭️  Skipping test users and sample trips (set NODE_ENV=development or SEED_TEST_USERS=true)\n',
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
