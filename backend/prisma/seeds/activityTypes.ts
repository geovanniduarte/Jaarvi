import { PrismaClient } from '@prisma/client';

export const activityTypeSeeds = [
  {
    key: 'sleep',
    displayName: 'Accommodation',
    isMandatoryDaily: true,
    requiresEvidence: true,
    allowNotNeeded: false,
  },
  {
    key: 'transfer',
    displayName: 'Transportation',
    isMandatoryDaily: false,
    requiresEvidence: false,
    allowNotNeeded: true,
  },
  {
    key: 'visit',
    displayName: 'Visit/Attraction',
    isMandatoryDaily: false,
    requiresEvidence: false,
    allowNotNeeded: true,
  },
  {
    key: 'meal',
    displayName: 'Meal',
    isMandatoryDaily: false,
    requiresEvidence: false,
    allowNotNeeded: true,
  },
  {
    key: 'free_time',
    displayName: 'Free Time',
    isMandatoryDaily: false,
    requiresEvidence: false,
    allowNotNeeded: true,
  },
];

export async function seedActivityTypes(prisma: PrismaClient): Promise<void> {
  console.log('📝 Seeding activity types...');

  for (const type of activityTypeSeeds) {
    await prisma.activityTypeConfig.upsert({
      where: { key: type.key },
      update: type,
      create: type,
    });
    console.log(`  ✓ ${type.displayName} (${type.key})`);
  }

  console.log(`  Total: ${activityTypeSeeds.length} activity types\n`);
}
