/**
 * Stable UUIDs for dev/test users so re-seeds and the auth JWT stub stay aligned.
 * Must match rows created by {@link seedTestUsers}.
 */
export const DEV_USER_IDS = {
  test: '2afb5526-d8fc-41ff-a313-f90bbe3c91bd',
  demo: '660e8400-e29b-41d4-a716-446655440010',
  admin: '660e8400-e29b-41d4-a716-446655440011',
} as const;
