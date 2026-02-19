-- CreateTable
CREATE TABLE "users" (
    "id" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "displayName" TEXT NOT NULL,
    "emailVerifiedAt" TIMESTAMP(3),
    "status" TEXT NOT NULL DEFAULT 'active',
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "user_credentials" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "passwordHash" TEXT NOT NULL,
    "passwordAlgo" TEXT NOT NULL DEFAULT 'argon2id',
    "passwordUpdatedAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "user_credentials_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "sessions" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "deviceName" TEXT,
    "userAgent" TEXT,
    "ipAddress" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "lastSeenAt" TIMESTAMP(3),
    "revokedAt" TIMESTAMP(3),
    "expiresAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "sessions_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "refresh_tokens" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "sessionId" TEXT NOT NULL,
    "tokenHash" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "rotatedAt" TIMESTAMP(3),
    "revokedAt" TIMESTAMP(3),
    "expiresAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "refresh_tokens_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "password_reset_tokens" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "tokenHash" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "usedAt" TIMESTAMP(3),
    "expiresAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "password_reset_tokens_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "email_verification_tokens" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "tokenHash" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "usedAt" TIMESTAMP(3),
    "expiresAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "email_verification_tokens_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "oauth_accounts" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "provider" TEXT NOT NULL,
    "providerAccountId" TEXT NOT NULL,
    "accessTokenEncrypted" TEXT,
    "refreshTokenEncrypted" TEXT,
    "tokenExpiresAt" TIMESTAMP(3),
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "oauth_accounts_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "preference_sets" (
    "id" TEXT NOT NULL,
    "userId" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "preferencesJson" JSONB NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "preference_sets_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "countries" (
    "id" TEXT NOT NULL,
    "isoCode" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "countries_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "cities" (
    "id" TEXT NOT NULL,
    "countryId" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "timezone" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "cities_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "city_coverages" (
    "id" TEXT NOT NULL,
    "cityId" TEXT NOT NULL,
    "level" TEXT NOT NULL,
    "notes" TEXT,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "city_coverages_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "trips" (
    "id" TEXT NOT NULL,
    "ownerId" TEXT NOT NULL,
    "name" TEXT,
    "startDate" TIMESTAMP(3) NOT NULL,
    "endDate" TIMESTAMP(3) NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'draft',
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "trips_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "trip_destinations" (
    "id" TEXT NOT NULL,
    "tripId" TEXT NOT NULL,
    "cityId" TEXT NOT NULL,
    "dayOrder" INTEGER NOT NULL,
    "daysCount" INTEGER NOT NULL,
    "notes" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "trip_destinations_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "trip_planning_contexts" (
    "id" TEXT NOT NULL,
    "tripId" TEXT NOT NULL,
    "version" INTEGER NOT NULL,
    "inputsSnapshot" JSONB NOT NULL,
    "coverageSnapshot" JSONB NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "trip_planning_contexts_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "itinerary_versions" (
    "id" TEXT NOT NULL,
    "tripId" TEXT NOT NULL,
    "version" INTEGER NOT NULL,
    "source" TEXT NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'draft',
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "itinerary_versions_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "itinerary_generations" (
    "id" TEXT NOT NULL,
    "itineraryVersionId" TEXT NOT NULL,
    "agentName" TEXT NOT NULL,
    "agentVersion" TEXT NOT NULL,
    "promptInputsSnapshot" JSONB NOT NULL,
    "contextInputsUsed" JSONB,
    "citations" JSONB,
    "rationale" JSONB,
    "confidence" JSONB,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "itinerary_generations_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "day_plans" (
    "id" TEXT NOT NULL,
    "itineraryVersionId" TEXT NOT NULL,
    "cityId" TEXT NOT NULL,
    "dayDate" TIMESTAMP(3) NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'pending',
    "refreshedAt" TIMESTAMP(3),

    CONSTRAINT "day_plans_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "daily_contexts" (
    "id" TEXT NOT NULL,
    "dayPlanId" TEXT NOT NULL,
    "constraintsSnapshot" JSONB NOT NULL,
    "weatherSnapshot" JSONB,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "daily_contexts_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "activity_type_configs" (
    "key" TEXT NOT NULL,
    "displayName" TEXT NOT NULL,
    "isMandatoryDaily" BOOLEAN NOT NULL DEFAULT false,
    "requiresEvidence" BOOLEAN NOT NULL DEFAULT false,
    "allowNotNeeded" BOOLEAN NOT NULL DEFAULT true,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "activity_type_configs_pkey" PRIMARY KEY ("key")
);

-- CreateTable
CREATE TABLE "activities" (
    "id" TEXT NOT NULL,
    "dayPlanId" TEXT NOT NULL,
    "activityTypeKey" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "title" TEXT NOT NULL,
    "locationName" TEXT,
    "costEstimate" DECIMAL(10,2),
    "startTime" TIMESTAMP(3),
    "endTime" TIMESTAMP(3),
    "sortOrder" INTEGER NOT NULL,
    "isMandatory" BOOLEAN NOT NULL DEFAULT false,
    "readinessStatus" TEXT NOT NULL DEFAULT 'pending',
    "readinessNote" TEXT,
    "preparedAt" TIMESTAMP(3),

    CONSTRAINT "activities_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "activity_progress" (
    "id" TEXT NOT NULL,
    "activityId" TEXT NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'notStarted',
    "startedAt" TIMESTAMP(3),
    "completedAt" TIMESTAMP(3),

    CONSTRAINT "activity_progress_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "step_guides" (
    "id" TEXT NOT NULL,
    "activityId" TEXT NOT NULL,
    "source" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "step_guides_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "step_guide_steps" (
    "id" TEXT NOT NULL,
    "stepGuideId" TEXT NOT NULL,
    "stepOrder" INTEGER NOT NULL,
    "title" TEXT NOT NULL,
    "description" TEXT,

    CONSTRAINT "step_guide_steps_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "reminders" (
    "id" TEXT NOT NULL,
    "activityId" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "scheduledAt" TIMESTAMP(3) NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'scheduled',

    CONSTRAINT "reminders_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "incidents" (
    "id" TEXT NOT NULL,
    "activityId" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "notes" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "incidents_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "documents" (
    "id" TEXT NOT NULL,
    "tripId" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "title" TEXT NOT NULL,
    "storageProvider" TEXT NOT NULL,
    "storageKey" TEXT NOT NULL,
    "checksum" TEXT,
    "metadataJson" JSONB,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "documents_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "document_links" (
    "id" TEXT NOT NULL,
    "documentId" TEXT NOT NULL,
    "tripId" TEXT NOT NULL,
    "dayPlanId" TEXT,
    "activityId" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "document_links_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "playbook_packs" (
    "id" TEXT NOT NULL,
    "name" TEXT NOT NULL,
    "version" INTEGER NOT NULL,
    "metadataJson" JSONB,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "playbook_packs_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "playbook_entries" (
    "id" TEXT NOT NULL,
    "playbookPackId" TEXT NOT NULL,
    "key" TEXT NOT NULL,
    "contentJson" JSONB NOT NULL,

    CONSTRAINT "playbook_entries_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "local_advice_packs" (
    "id" TEXT NOT NULL,
    "cityId" TEXT NOT NULL,
    "playbookPackId" TEXT NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'draft',
    "publishedAt" TIMESTAMP(3),

    CONSTRAINT "local_advice_packs_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "recommendations" (
    "id" TEXT NOT NULL,
    "tripId" TEXT NOT NULL,
    "dayPlanId" TEXT,
    "activityId" TEXT,
    "category" TEXT NOT NULL,
    "title" TEXT NOT NULL,
    "destinationUrl" TEXT NOT NULL,
    "displayReason" JSONB,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "recommendations_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "affiliate_clicks" (
    "id" TEXT NOT NULL,
    "recommendationId" TEXT NOT NULL,
    "clickedAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "userAgent" TEXT,
    "referrer" TEXT,

    CONSTRAINT "affiliate_clicks_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "users_email_key" ON "users"("email");

-- CreateIndex
CREATE UNIQUE INDEX "user_credentials_userId_key" ON "user_credentials"("userId");

-- CreateIndex
CREATE INDEX "sessions_userId_expiresAt_idx" ON "sessions"("userId", "expiresAt" DESC);

-- CreateIndex
CREATE UNIQUE INDEX "refresh_tokens_tokenHash_key" ON "refresh_tokens"("tokenHash");

-- CreateIndex
CREATE INDEX "refresh_tokens_sessionId_expiresAt_idx" ON "refresh_tokens"("sessionId", "expiresAt" DESC);

-- CreateIndex
CREATE UNIQUE INDEX "password_reset_tokens_tokenHash_key" ON "password_reset_tokens"("tokenHash");

-- CreateIndex
CREATE INDEX "password_reset_tokens_userId_createdAt_idx" ON "password_reset_tokens"("userId", "createdAt" DESC);

-- CreateIndex
CREATE UNIQUE INDEX "email_verification_tokens_tokenHash_key" ON "email_verification_tokens"("tokenHash");

-- CreateIndex
CREATE INDEX "email_verification_tokens_userId_createdAt_idx" ON "email_verification_tokens"("userId", "createdAt" DESC);

-- CreateIndex
CREATE INDEX "oauth_accounts_userId_provider_idx" ON "oauth_accounts"("userId", "provider");

-- CreateIndex
CREATE UNIQUE INDEX "oauth_accounts_provider_providerAccountId_key" ON "oauth_accounts"("provider", "providerAccountId");

-- CreateIndex
CREATE INDEX "preference_sets_userId_createdAt_idx" ON "preference_sets"("userId", "createdAt" DESC);

-- CreateIndex
CREATE UNIQUE INDEX "countries_isoCode_key" ON "countries"("isoCode");

-- CreateIndex
CREATE UNIQUE INDEX "cities_countryId_name_key" ON "cities"("countryId", "name");

-- CreateIndex
CREATE UNIQUE INDEX "city_coverages_cityId_key" ON "city_coverages"("cityId");

-- CreateIndex
CREATE INDEX "trips_ownerId_status_idx" ON "trips"("ownerId", "status");

-- CreateIndex
CREATE INDEX "trips_ownerId_startDate_idx" ON "trips"("ownerId", "startDate");

-- CreateIndex
CREATE UNIQUE INDEX "trip_destinations_tripId_dayOrder_key" ON "trip_destinations"("tripId", "dayOrder");

-- CreateIndex
CREATE INDEX "trip_planning_contexts_tripId_version_idx" ON "trip_planning_contexts"("tripId", "version" DESC);

-- CreateIndex
CREATE UNIQUE INDEX "itinerary_versions_tripId_version_key" ON "itinerary_versions"("tripId", "version");

-- CreateIndex
CREATE UNIQUE INDEX "itinerary_generations_itineraryVersionId_key" ON "itinerary_generations"("itineraryVersionId");

-- CreateIndex
CREATE INDEX "day_plans_cityId_dayDate_idx" ON "day_plans"("cityId", "dayDate");

-- CreateIndex
CREATE UNIQUE INDEX "day_plans_itineraryVersionId_dayDate_key" ON "day_plans"("itineraryVersionId", "dayDate");

-- CreateIndex
CREATE INDEX "daily_contexts_dayPlanId_createdAt_idx" ON "daily_contexts"("dayPlanId", "createdAt" DESC);

-- CreateIndex
CREATE INDEX "activities_dayPlanId_startTime_idx" ON "activities"("dayPlanId", "startTime");

-- CreateIndex
CREATE INDEX "activities_dayPlanId_isMandatory_readinessStatus_idx" ON "activities"("dayPlanId", "isMandatory", "readinessStatus");

-- CreateIndex
CREATE UNIQUE INDEX "activities_dayPlanId_sortOrder_key" ON "activities"("dayPlanId", "sortOrder");

-- CreateIndex
CREATE UNIQUE INDEX "activity_progress_activityId_key" ON "activity_progress"("activityId");

-- CreateIndex
CREATE UNIQUE INDEX "step_guides_activityId_key" ON "step_guides"("activityId");

-- CreateIndex
CREATE UNIQUE INDEX "step_guide_steps_stepGuideId_stepOrder_key" ON "step_guide_steps"("stepGuideId", "stepOrder");

-- CreateIndex
CREATE INDEX "reminders_activityId_scheduledAt_idx" ON "reminders"("activityId", "scheduledAt");

-- CreateIndex
CREATE INDEX "reminders_scheduledAt_status_idx" ON "reminders"("scheduledAt", "status");

-- CreateIndex
CREATE INDEX "documents_tripId_createdAt_idx" ON "documents"("tripId", "createdAt" DESC);

-- CreateIndex
CREATE INDEX "document_links_tripId_dayPlanId_activityId_idx" ON "document_links"("tripId", "dayPlanId", "activityId");

-- CreateIndex
CREATE UNIQUE INDEX "playbook_entries_playbookPackId_key_key" ON "playbook_entries"("playbookPackId", "key");

-- CreateIndex
CREATE INDEX "local_advice_packs_cityId_status_idx" ON "local_advice_packs"("cityId", "status");

-- CreateIndex
CREATE INDEX "local_advice_packs_cityId_publishedAt_idx" ON "local_advice_packs"("cityId", "publishedAt" DESC);

-- CreateIndex
CREATE INDEX "recommendations_tripId_createdAt_idx" ON "recommendations"("tripId", "createdAt" DESC);

-- CreateIndex
CREATE INDEX "affiliate_clicks_recommendationId_clickedAt_idx" ON "affiliate_clicks"("recommendationId", "clickedAt" DESC);

-- AddForeignKey
ALTER TABLE "user_credentials" ADD CONSTRAINT "user_credentials_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "sessions" ADD CONSTRAINT "sessions_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "refresh_tokens" ADD CONSTRAINT "refresh_tokens_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "refresh_tokens" ADD CONSTRAINT "refresh_tokens_sessionId_fkey" FOREIGN KEY ("sessionId") REFERENCES "sessions"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "password_reset_tokens" ADD CONSTRAINT "password_reset_tokens_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "email_verification_tokens" ADD CONSTRAINT "email_verification_tokens_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "oauth_accounts" ADD CONSTRAINT "oauth_accounts_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "preference_sets" ADD CONSTRAINT "preference_sets_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "cities" ADD CONSTRAINT "cities_countryId_fkey" FOREIGN KEY ("countryId") REFERENCES "countries"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "city_coverages" ADD CONSTRAINT "city_coverages_cityId_fkey" FOREIGN KEY ("cityId") REFERENCES "cities"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "trips" ADD CONSTRAINT "trips_ownerId_fkey" FOREIGN KEY ("ownerId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "trip_destinations" ADD CONSTRAINT "trip_destinations_tripId_fkey" FOREIGN KEY ("tripId") REFERENCES "trips"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "trip_destinations" ADD CONSTRAINT "trip_destinations_cityId_fkey" FOREIGN KEY ("cityId") REFERENCES "cities"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "trip_planning_contexts" ADD CONSTRAINT "trip_planning_contexts_tripId_fkey" FOREIGN KEY ("tripId") REFERENCES "trips"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itinerary_versions" ADD CONSTRAINT "itinerary_versions_tripId_fkey" FOREIGN KEY ("tripId") REFERENCES "trips"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "itinerary_generations" ADD CONSTRAINT "itinerary_generations_itineraryVersionId_fkey" FOREIGN KEY ("itineraryVersionId") REFERENCES "itinerary_versions"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "day_plans" ADD CONSTRAINT "day_plans_itineraryVersionId_fkey" FOREIGN KEY ("itineraryVersionId") REFERENCES "itinerary_versions"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "day_plans" ADD CONSTRAINT "day_plans_cityId_fkey" FOREIGN KEY ("cityId") REFERENCES "cities"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "daily_contexts" ADD CONSTRAINT "daily_contexts_dayPlanId_fkey" FOREIGN KEY ("dayPlanId") REFERENCES "day_plans"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "activities" ADD CONSTRAINT "activities_dayPlanId_fkey" FOREIGN KEY ("dayPlanId") REFERENCES "day_plans"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "activities" ADD CONSTRAINT "activities_activityTypeKey_fkey" FOREIGN KEY ("activityTypeKey") REFERENCES "activity_type_configs"("key") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "activity_progress" ADD CONSTRAINT "activity_progress_activityId_fkey" FOREIGN KEY ("activityId") REFERENCES "activities"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "step_guides" ADD CONSTRAINT "step_guides_activityId_fkey" FOREIGN KEY ("activityId") REFERENCES "activities"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "step_guide_steps" ADD CONSTRAINT "step_guide_steps_stepGuideId_fkey" FOREIGN KEY ("stepGuideId") REFERENCES "step_guides"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "reminders" ADD CONSTRAINT "reminders_activityId_fkey" FOREIGN KEY ("activityId") REFERENCES "activities"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "incidents" ADD CONSTRAINT "incidents_activityId_fkey" FOREIGN KEY ("activityId") REFERENCES "activities"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "documents" ADD CONSTRAINT "documents_tripId_fkey" FOREIGN KEY ("tripId") REFERENCES "trips"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "document_links" ADD CONSTRAINT "document_links_documentId_fkey" FOREIGN KEY ("documentId") REFERENCES "documents"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "document_links" ADD CONSTRAINT "document_links_tripId_fkey" FOREIGN KEY ("tripId") REFERENCES "trips"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "document_links" ADD CONSTRAINT "document_links_dayPlanId_fkey" FOREIGN KEY ("dayPlanId") REFERENCES "day_plans"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "document_links" ADD CONSTRAINT "document_links_activityId_fkey" FOREIGN KEY ("activityId") REFERENCES "activities"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "playbook_entries" ADD CONSTRAINT "playbook_entries_playbookPackId_fkey" FOREIGN KEY ("playbookPackId") REFERENCES "playbook_packs"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "local_advice_packs" ADD CONSTRAINT "local_advice_packs_cityId_fkey" FOREIGN KEY ("cityId") REFERENCES "cities"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "local_advice_packs" ADD CONSTRAINT "local_advice_packs_playbookPackId_fkey" FOREIGN KEY ("playbookPackId") REFERENCES "playbook_packs"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "recommendations" ADD CONSTRAINT "recommendations_tripId_fkey" FOREIGN KEY ("tripId") REFERENCES "trips"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "recommendations" ADD CONSTRAINT "recommendations_dayPlanId_fkey" FOREIGN KEY ("dayPlanId") REFERENCES "day_plans"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "recommendations" ADD CONSTRAINT "recommendations_activityId_fkey" FOREIGN KEY ("activityId") REFERENCES "activities"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "affiliate_clicks" ADD CONSTRAINT "affiliate_clicks_recommendationId_fkey" FOREIGN KEY ("recommendationId") REFERENCES "recommendations"("id") ON DELETE CASCADE ON UPDATE CASCADE;
