import { PrismaClient } from '@prisma/client';
import argon2 from 'argon2';
import { DEV_USER_IDS } from './devUserIds';

export const testUserSeeds = [
  {
    id: DEV_USER_IDS.test,
    email: 'test@jaarvi.app',
    displayName: 'Test User',
    password: 'TestPassword123',
  },
  {
    id: DEV_USER_IDS.demo,
    email: 'demo@jaarvi.app',
    displayName: 'Demo User',
    password: 'DemoPassword123',
  },
  {
    id: DEV_USER_IDS.admin,
    email: 'admin@jaarvi.app',
    displayName: 'Admin User',
    password: 'AdminPassword123',
  },
];

export async function seedTestUsers(prisma: PrismaClient): Promise<void> {
  console.log('👥 Seeding test users...');

  for (const userData of testUserSeeds) {
    const passwordHash = await argon2.hash(userData.password, {
      type: argon2.argon2id,
      memoryCost: 2 ** 16,
      timeCost: 3,
      parallelism: 1,
    });

    const user = await prisma.user.upsert({
      where: { email: userData.email },
      update: {},
      create: {
        id: userData.id,
        email: userData.email,
        displayName: userData.displayName,
        emailVerifiedAt: new Date(),
        status: 'active',
      },
    });

    await prisma.userCredential.upsert({
      where: { userId: user.id },
      update: {
        passwordHash,
        passwordAlgo: 'argon2id',
        passwordUpdatedAt: new Date(),
      },
      create: {
        userId: user.id,
        passwordHash,
        passwordAlgo: 'argon2id',
        passwordUpdatedAt: new Date(),
      },
    });

    console.log(`  ✓ ${userData.email}`);
  }

  console.log(`  Total: ${testUserSeeds.length} test users\n`);
}
