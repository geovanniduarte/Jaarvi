# User Story: Time Management Notifications

## Story
**As a** traveler  
**I want to** receive timely reminders for my activities  
**So that** I don't miss important events, closures, or transfers

## Business Value
- Reduces missed activities and time stress
- Proactive trip execution support
- Improves user trust and reliability

## Acceptance Criteria
- [ ] User can enable/disable notifications in settings
- [ ] System sends notification for "leave time" (30 min before activity)
- [ ] System sends notification for closing hours (1 hour before closure)
- [ ] System sends notification for time-critical transfers (2 hours before departure)
- [ ] Notifications include activity title and time remaining
- [ ] Tapping notification opens relevant activity in app
- [ ] System schedules notifications at trip start (background job)
- [ ] System cancels notifications when activity is completed/skipped
- [ ] Notifications respect device "Do Not Disturb" settings
- [ ] User sees notification history in app (last 24 hours)

## Technical Notes
- Use platform notification services (FCM for Android, APNs for iOS)
- Schedule local notifications for time-critical events
- Store notification schedule in `NotificationSchedule` entity
- Calculate leave time based on travel distance (MVP: fixed 30 min)
- Handle timezone changes for multi-city trips
- Respect user notification preferences

## Estimated Effort
**5 Story Points** (Medium)

## Priority
**Medium** - Enhances execution experience

## Dependencies
- mvp-08-execute-daily-activities

## Mapped Use Cases
- [UC-6.7: Receive Time Management Notifications](../../product-discovery/8-UserCases.md#uc-67-receive-time-management-notifications)

## Definition of Done
- [ ] Unit tests for notification scheduling
- [ ] Integration tests for notification triggers
- [ ] Notifications working on iOS and Android
- [ ] Notification settings UI implemented
- [ ] Deep linking from notifications tested
- [ ] Timezone handling tested
- [ ] Notification history UI implemented
- [ ] Performance: Notifications delivered on time (±2 min)
