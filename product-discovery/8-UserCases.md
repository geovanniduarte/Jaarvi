# Jaarvi - Use Cases Catalog

## Overview

This document catalogs all use cases for the Jaarvi mobile travel assistant application, organized by implementation phase. Each use case describes a discrete interaction between a user (or system actor) and the system to achieve a specific goal.

**Each use case now includes references to wireframes** from `product-discovery/7-Wireframes.md` to guide frontend development.

**Legend:**
- **MVP** = Phase 1 (Months 1-3): Manual-first functionality
- **Phase 2** = AI-Assisted Features (Months 4-6)
- **Phase 3** = Advanced Features (Months 7+)
- **📱 Wireframe:** = Reference to corresponding screen design(s)

---

## 1. Account & Profile Management

### UC-1.1: Create User Account
**Phase:** Phase 1  
**Actor:** Traveler  
**Description:** User registers for a new Jaarvi account with email/password or social authentication.  
**Preconditions:** None  
**Postconditions:** User profile created with default preferences.  
**📱 Wireframe:** [Login/Register Screen](7-Wireframes.md#screen-15-loginregister)

### UC-1.2: Manage User Profile
**Phase:** Phase 1   
**Actor:** Traveler  
**Description:** User updates profile settings including language, units, default pace, and budget range preferences.  
**Preconditions:** User is authenticated.  
**Postconditions:** Profile preferences updated and persisted.  
**📱 Wireframe:** [Profile Settings Screen](7-Wireframes.md#screen-16-profile-settings)

### UC-1.3: Set Travel Preferences
**Phase:** Phase 1   
**Actor:** Traveler  
**Description:** User configures travel preferences including interests, travel style, and default constraints (budget, pace, time windows).  
**Preconditions:** User has an account.  
**Postconditions:** PreferenceSet stored and available for future trip planning.  
**📱 Wireframe:** [Create Trip Flow (Step 3: Preferences) - Shows preference configuration pattern](7-Wireframes.md#screen-14-create-trip-flow-step-3-preferences)

---

## 2. Trip Creation & Setup

### UC-2.1: Create New Trip
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User creates a new trip by providing trip name (optional), start/end dates.  
**Preconditions:** User is authenticated.  
**Postconditions:** Trip created in `draft` status with empty itinerary.  
**📱 Wireframe:** [Create Trip Flow (Step 1: Basic Info)](7-Wireframes.md#screen-12-create-trip-flow-step-1-basic-info)

### UC-2.2: Add Destinations to Trip
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User adds one or multiple destinations (cities) to a trip and assigns number of days per destination.  
**Preconditions:** Trip exists.  
**Postconditions:** TripDestination records created with date ranges or day index ranges.  
**📱 Wireframe:** [Create Trip Flow (Step 2: Add Destinations)](7-Wireframes.md#screen-13-create-trip-flow-step-2-add-destinations)

### UC-2.3: Set Trip-Specific Preferences
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User sets or overrides preferences for a specific trip (budget, pace, interests, constraints).  
**Preconditions:** Trip exists.  
**Postconditions:** TripPlanningContext updated with user-provided constraints.  
**📱 Wireframe:** [Create Trip Flow (Step 3: Preferences)](7-Wireframes.md#screen-14-create-trip-flow-step-3-preferences)

### UC-2.4: Validate Trip Configuration
**Phase:** MVP  
**Actor:** System  
**Description:** System validates trip dates, destination days, and required fields before allowing itinerary creation.  
**Preconditions:** User submits trip creation/edit.  
**Postconditions:** Valid trip saved or validation errors returned to user.  
**📱 Wireframe:** Screens 12-14 - Inline validation in Create Trip Flow

### UC-2.5: View Trip List
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User views all trips organized by status (draft/active/completed).  
**Preconditions:** User is authenticated.  
**Postconditions:** Trip list displayed with basic metadata.  
**📱 Wireframe:** [Trip List (Countries View)](7-Wireframes.md#screen-1-trip-list-countries-view)

### UC-2.6: Edit Trip Details
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User modifies trip name, dates, or destinations for draft or active trips.  
**Preconditions:** Trip exists.  
**Postconditions:** Trip details updated, itinerary recalculated if dates changed.  
**📱 Wireframe:** [Trip Detail (Cities List) - Edit actions via menu (⋮)](7-Wireframes.md#screen-2-trip-detail-cities-list)

### UC-2.7: Delete Trip
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User permanently deletes a trip and all associated data (itinerary, documents, activities).  
**Preconditions:** Trip exists, user confirms deletion.  
**Postconditions:** Trip and all related data removed.  
**📱 Wireframe:** [Trip List (🗑️ icon)](7-Wireframes.md#screen-1-trip-list-countries-view)

---

## 3. Itinerary Planning - Manual (MVP)

### UC-3.1: Initialize Empty Day Plans
**Phase:** MVP  
**Actor:** System  
**Description:** System automatically creates empty DayPlan records for each day in the trip based on destinations and date ranges.  
**Preconditions:** Trip destinations and dates are set.  
**Postconditions:** Empty day plans created with mandatory activity placeholders (e.g., sleep).  
**📱 Wireframe:** [City Itinerary (Days Tabs View) - Shows empty day structure](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view)

### UC-3.2: Add Activity to Day Plan
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User adds a new activity to a specific day by providing title, location (optional), time block, cost estimate (optional), activity type, and notes.  
**Preconditions:** Day plan exists.  
**Postconditions:** Activity created and added to day plan.  
**📱 Wireframe:** [Add/Edit Activity Modal](7-Wireframes.md#screen-5-addedit-activity-modal)

### UC-3.3: Edit Activity Details
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User modifies activity title, location, time block, cost estimate, or notes.  
**Preconditions:** Activity exists.  
**Postconditions:** Activity details updated.  
**📱 Wireframe:** [Add/Edit Activity Modal (Edit mode)](7-Wireframes.md#screen-5-addedit-activity-modal)

### UC-3.4: Delete Activity
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User removes an activity from a day plan.  
**Preconditions:** Activity exists, not a mandatory activity type or user replaces it.  
**Postconditions:** Activity deleted from day plan.  
**📱 Wireframe:** [Day Activities (Edit Mode) - Delete button [🗑️]](7-Wireframes.md#screen-4-day-activities-edit-mode---reordering)

### UC-3.5: Reorder Activities Within Day
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User changes the sequence of activities within a day using drag-and-drop.  
**Preconditions:** Day plan has multiple activities.  
**Postconditions:** Activity order updated.  
**📱 Wireframe:** [Day Activities (Edit Mode - Reordering) - Drag handles (☰)](7-Wireframes.md#screen-4-day-activities-edit-mode---reordering)

### UC-3.6: Move Activity Between Days
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User moves an activity from one day plan to another.  
**Preconditions:** Multiple day plans exist, activity exists.  
**Postconditions:** Activity reassigned to target day plan.  
**📱 Wireframe:** [Day Activities (Edit Mode)](7-Wireframes.md#screen-4-day-activities-edit-mode---reordering)

### UC-3.7: Add Transfer Activity
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User adds a transfer activity (airport→hotel, hotel→station, etc.) with departure/arrival locations and time.  
**Preconditions:** Day plan exists.  
**Postconditions:** Transfer activity created with transport context.  
**📱 Wireframe:** [Add/Edit Activity Modal - Activity Type: ✈️ Transport](7-Wireframes.md#screen-5-addedit-activity-modal)

### UC-3.8: Add Lodging Anchor
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User adds accommodation details (check-in/check-out) as a mandatory activity with booking reference.  
**Preconditions:** Day plan exists.  
**Postconditions:** Lodging activity created, mandatory activity requirement satisfied.  
**📱 Wireframe:** [Add/Edit Activity Modal - Activity Type: 🏨 Accommodation](7-Wireframes.md#screen-5-addedit-activity-modal)

### UC-3.9: View Day Plan Summary
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User views a summary of a specific day including all activities, total estimated cost, and time blocks.  
**Preconditions:** Day plan exists.  
**Postconditions:** Day summary displayed with feasibility indicators.  
**📱 Wireframe:** [City Itinerary (Days Tabs View) - Day summary at bottom (💰 Day Total: €136 • ⏱️ 8h 30min)](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view)

### UC-3.10: View Full Itinerary
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User views the complete multi-day itinerary with all day plans in sequence.  
**Preconditions:** Trip exists.  
**Postconditions:** Full itinerary displayed with navigation between days.  
**📱 Wireframe:** [City Itinerary (Days Tabs View) - Swipe between day tabs](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view)

### UC-3.11: Detect Time Conflicts
**Phase:** MVP  
**Actor:** System  
**Description:** System identifies overlapping activities or unrealistic time blocks within a day plan.  
**Preconditions:** Day plan has multiple activities with time blocks.  
**Postconditions:** Conflict warnings displayed to user.  
**📱 Wireframe:** [Day Activities (Edit Mode) - Validation warning (⚠️ Time conflict detected)](7-Wireframes.md#screen-4-day-activities-edit-mode---reordering)

### UC-3.12: Calculate Daily Budget
**Phase:** MVP  
**Actor:** System  
**Description:** System aggregates activity cost estimates per day and compares against user budget constraints.  
**Preconditions:** Activities have cost estimates, user has set budget.  
**Postconditions:** Budget status displayed (under/over/on-budget).  
**📱 Wireframe:** [Day summary bar](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view) + [Today View (💰 Spent: €63.50 / €180 today)](7-Wireframes.md#screen-10-today-view-active-trip-execution)

---

## 4. Itinerary Planning - AI-Assisted (Phase 2)

### UC-4.1: Generate Initial Itinerary with AI
**Phase:** Phase 2  
**Actor:** Traveler + AI Agent  
**Description:** User requests AI to generate a complete day-by-day itinerary based on trip dates, destinations, and preferences.  
**Preconditions:** Trip setup complete with preferences.  
**Postconditions:** AI-generated itinerary saved with generation metadata and rationale.  
**📱 Wireframe:** [AI Generation Screen](7-Wireframes.md#screen-17-ai-itinerary-generation) + [(displays generated result)](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view)

### UC-4.2: View AI Recommendation Rationale
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User views "why" explanations for AI-suggested activities (time, cost, distance, convenience, local dynamics).  
**Preconditions:** Activity was AI-generated.  
**Postconditions:** Rationale displayed with confidence indicators.  
**📱 Wireframe:** [Activity Detail with AI Rationale - Expandable "Why this?" section](7-Wireframes.md#screen-18-activity-detail-with-ai-rationale)

### UC-4.3: Request AI Activity Suggestions for Day
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User requests AI to suggest activities to fill gaps in a manually created day plan.  
**Preconditions:** Day plan exists with available time blocks.  
**Postconditions:** AI suggests 3-5 activities with rationale; user can accept/reject.  
**📱 Wireframe:** [AI Suggestions Modal](7-Wireframes.md#screen-19-ai-activity-suggestions-modal) + [Screen 3](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view)

### UC-4.4: Optimize Itinerary with AI
**Phase:** Phase 2  
**Actor:** Traveler + AI Agent  
**Description:** User requests AI to reorder/optimize existing itinerary to minimize travel time, respect opening hours, and balance budget.  
**Preconditions:** Itinerary exists with multiple activities.  
**Postconditions:** Optimized itinerary version created; user can compare and accept/reject.  
**📱 Wireframe:** [Optimization Comparison View - Side-by-side before/after](7-Wireframes.md#screen-20-itinerary-optimization-comparison)

### UC-4.5: Generate Plan B Alternatives
**Phase:** Phase 2  
**Actor:** Traveler + AI Agent  
**Description:** When an activity becomes unavailable (closed, weather, budget), user requests AI to suggest alternative activities with similar intent.  
**Preconditions:** Original activity exists and is marked unavailable.  
**Postconditions:** Alternative activities suggested; user can swap or keep original.  
**📱 Wireframe:** [Activity Execution - Problem Resolution (4️⃣ See Plan B alternatives)](7-Wireframes.md#screen-9-activity-execution---problem-resolution)

### UC-4.6: View AI Generation Audit Trail
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User views metadata about AI-generated recommendations including inputs used, model version, and confidence level.  
**Preconditions:** Activity/itinerary was AI-generated.  
**Postconditions:** Generation metadata displayed.  
**📱 Wireframe:** [AI Audit Trail Detail - Accessed from activity menu](7-Wireframes.md#screen-21-ai-audit-trail-detail)

---

## 5. Daily Planning (Start-of-Day)

### UC-5.1: Open Today View
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User opens "Today" view to see current day's activities and next action.  
**Preconditions:** User is on an active trip with today's date within trip range.  
**Postconditions:** Today's day plan displayed with current activity highlighted.  
**📱 Wireframe:** [Today View (Active Trip Execution)](7-Wireframes.md#screen-10-today-view-active-trip-execution)

### UC-5.2: Refresh Daily Plan
**Phase:** Phase 2  
**Actor:** Traveler + System  
**Description:** At start of day, system adapts planned activities to current constraints (time available, opening hours, travel times, weather).  
**Preconditions:** Day plan exists, current date matches day plan date.  
**Postconditions:** Day plan updated with practical adjustments; user approves or edits.  
**📱 Wireframe:** [Today View with refresh indicator](7-Wireframes.md#screen-10-today-view-active-trip-execution) + [Daily Plan Refresh Modal](7-Wireframes.md#screen-22-daily-plan-refresh-modal)

### UC-5.3: View Local Advice for Day
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User views destination-specific practical advice for today's activities (payment tips, negotiation norms, buy-in-advance vs on-site guidance).  
**Preconditions:** Destination has available playbook coverage.  
**Postconditions:** Local advice displayed contextually per activity type.  
**📱 Wireframe:** [Activity Detail (ℹ️ Local Tips section)](7-Wireframes.md#screen-6-activity-detail-execution-view)

### UC-5.4: Check Mandatory Activity Readiness
**Phase:** MVP  
**Actor:** Traveler + System  
**Description:** System checks if mandatory activities for today/tonight (e.g., accommodation) have evidence (documents) or notes indicating readiness.  
**Preconditions:** Day plan has mandatory activities.  
**Postconditions:** Pending mandatory activities highlighted as priority alerts.  
**📱 Wireframe:** [Today View with alert banner *(To be enhanced)*](7-Wireframes.md#screen-10-today-view-active-trip-execution) + [Mandatory Activity Alert](7-Wireframes.md#screen-23-mandatory-activity-alert)

---

## 6. Activity Execution Guidance (In-Trip)

### UC-6.1: Start Activity
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User marks current activity as "in progress" and system provides navigation and guidance.  
**Preconditions:** Activity exists in today's day plan.  
**Postconditions:** Activity status updated to `in_progress`, navigation/guidance displayed.  
**📱 Wireframe:** [Activity Detail (Execution View) → Screen 7](7-Wireframes.md#screen-6-activity-detail-execution-view) + [Activity Execution Pipeline](7-Wireframes.md#screen-7-activity-execution-pipeline-in-progress)

### UC-6.2: Navigate to Activity Location
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User opens navigation to activity location via external maps app (deep link).  
**Preconditions:** Activity has location data.  
**Postconditions:** External maps app opened with destination set.  
**📱 Wireframe:** [Activity Detail ([🗺️ Open in Maps] button)](7-Wireframes.md#screen-6-activity-detail-execution-view)

### UC-6.3: View Landmark-Based Instructions
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User views human-friendly navigation cues with landmarks (e.g., "turn right at X bakery") for walking routes.  
**Preconditions:** Activity has step-by-step guidance available.  
**Postconditions:** Landmark instructions displayed in sequence.  
**📱 Wireframe:** [Activity Execution - Navigation Mode (🧭 Landmark-Based Directions section)](7-Wireframes.md#screen-8-activity-execution---navigation-mode)

### UC-6.4: View Operational Step Guidance
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User views procedural instructions for complex tasks (ticket machines, transit passes, airport transfers).  
**Preconditions:** Activity type has playbook with operational steps.  
**Postconditions:** Step-by-step checklist displayed with checkpoints.  
**📱 Wireframe:** [Activity Execution Pipeline (Current Step expanded with instructions)](7-Wireframes.md#screen-7-activity-execution-pipeline-in-progress)

### UC-6.5: Mark Activity as Complete
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User marks activity as done; system advances to next activity.  
**Preconditions:** Activity is in progress or pending.  
**Postconditions:** Activity status = `done`, next activity becomes current.  
**📱 Wireframe:** [Activity Detail ([✅ Mark as Done] button)](7-Wireframes.md#screen-6-activity-detail-execution-view) + [Today View ([✅ Complete] button)](7-Wireframes.md#screen-10-today-view-active-trip-execution)

### UC-6.6: Skip Activity
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User skips an activity; system adjusts schedule and moves to next activity.  
**Preconditions:** Activity exists in today's plan.  
**Postconditions:** Activity status = `skipped`, schedule recalculated.  
**📱 Wireframe:** [Activity Detail ([⏭️ Skip] button)](7-Wireframes.md#screen-6-activity-detail-execution-view) + [Activity Execution Pipeline ([⏭️ Skip Step])](7-Wireframes.md#screen-7-activity-execution-pipeline-in-progress)

### UC-6.7: Receive Time Management Notifications
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User receives push notifications for time-critical events (leave time, closing hours, next transfer).  
**Preconditions:** Notifications enabled, activity has time constraints.  
**Postconditions:** Notification displayed and logged.  
**📱 Wireframe:** [Today View (🚶 Leave at 14:45)](7-Wireframes.md#screen-10-today-view-active-trip-execution) + [Push Notification Template](7-Wireframes.md#screen-24-push-notification-template)

### UC-6.8: View Activity Progress
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User views progress through today's activities (completed/remaining) with time estimates.  
**Preconditions:** Day plan exists.  
**Postconditions:** Progress indicator displayed with real-time updates.  
**📱 Wireframe:** [Today View (Progress bar: 📍 Day 1 of 5 in Paris ━━━━░░░░░░░░░░░░░░░░ 20%)](7-Wireframes.md#screen-10-today-view-active-trip-execution)

### UC-6.9: Report Activity Issue
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User reports a problem with activity execution (closure, delay, safety concern, budget issue).  
**Preconditions:** Activity exists.  
**Postconditions:** Incident logged, system offers alternatives or resolution playbook.  
**📱 Wireframe:** [Activity Execution - Problem Resolution ([🆘 Help] button opens modal)](7-Wireframes.md#screen-9-activity-execution---problem-resolution)

### UC-6.10: Access Problem Resolution Playbook
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User accesses action-oriented guidance for incidents (missed connections, lost items, safety concerns, last-minute changes).  
**Preconditions:** Problem type has available playbook.  
**Postconditions:** Playbook steps displayed with emergency contacts and fallback options.  
**📱 Wireframe:** [Activity Execution - Problem Resolution (Shows contextual solutions based on issue type)](7-Wireframes.md#screen-9-activity-execution---problem-resolution)

---

## 7. Travel Document Vault

### UC-7.1: Upload Document
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User uploads a document (PDF, image, etc.) from device storage or camera.  
**Preconditions:** User is authenticated, has active trip.  
**Postconditions:** Document stored with file reference and metadata.  
**📱 Wireframe:** [Document Vault ([+] button)](7-Wireframes.md#screen-11-document-vault-linked-to-trip) + [Document Upload Modal](7-Wireframes.md#screen-25-document-upload-modal)

### UC-7.2: Link Document to Trip/Day/Activity
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User associates a document with trip, specific day plan, or activity.  
**Preconditions:** Document exists, target trip/day/activity exists.  
**Postconditions:** Document linked with contextual retrieval enabled.  
**📱 Wireframe:** [Add/Edit Activity Modal (📎 Attach Documents section)](7-Wireframes.md#screen-5-addedit-activity-modal) + [Document Upload Modal](7-Wireframes.md#screen-25-document-upload-modal)

### UC-7.3: Tag Document with Metadata
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User adds metadata to document (type: ticket/reservation/permit, provider, date/time relevance, title).  
**Preconditions:** Document exists.  
**Postconditions:** Document metadata updated for better organization.  
**📱 Wireframe:** [Document Upload Modal - Metadata fields](7-Wireframes.md#screen-25-document-upload-modal)

### UC-7.4: View Documents by Trip
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User views all documents associated with a specific trip.  
**Preconditions:** Trip exists.  
**Postconditions:** Document list displayed organized by day/activity.  
**📱 Wireframe:** [Document Vault (Linked to Trip) - Grouped by destination](7-Wireframes.md#screen-11-document-vault-linked-to-trip)

### UC-7.5: Access Document in Context
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User quickly accesses relevant documents from "Today" view or activity detail screen.  
**Preconditions:** Document is linked to current day/activity.  
**Postconditions:** Document displayed in-app viewer.  
**📱 Wireframe:** [Activity Detail (📎 Documents section)](7-Wireframes.md#screen-6-activity-detail-execution-view) + [Activity Execution Pipeline (📎 Your Ticket)](7-Wireframes.md#screen-7-activity-execution-pipeline-in-progress)

### UC-7.6: Share Document
**Phase:** Phase 3  
**Actor:** Traveler  
**Description:** User shares a document with another person (email, messaging app).  
**Preconditions:** Document exists.  
**Postconditions:** Document shared via platform sharing mechanism.  
**📱 Wireframe:** [Document Vault with share action *(To be enhanced)*](7-Wireframes.md#screen-11-document-vault-linked-to-trip)

### UC-7.7: Delete Document
**Phase:** MVP  
**Actor:** Traveler  
**Description:** User permanently removes a document from vault.  
**Preconditions:** Document exists.  
**Postconditions:** Document and file reference deleted.  
**📱 Wireframe:** [Document Vault (Swipe action or long-press menu)](7-Wireframes.md#screen-11-document-vault-linked-to-trip)

### UC-7.8: Search Documents
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User searches documents by title, type, provider, or date.  
**Preconditions:** User has documents stored.  
**Postconditions:** Matching documents displayed.  
**📱 Wireframe:** [Document Vault (🔍 Search documents... field at top)](7-Wireframes.md#screen-11-document-vault-linked-to-trip)

---

## 8. Affiliate Recommendations & Monetization

### UC-8.1: View Contextual Recommendation
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User sees relevant product/service recommendations at decision points (tours, tickets, transport, essentials).  
**Preconditions:** Activity or day plan has compatible recommendation context.  
**Postconditions:** Recommendation displayed with rationale (why it fits).  
**📱 Wireframe:** [Affiliate Recommendation Card](7-Wireframes.md#screen-26-affiliate-recommendation-card) + [(Activity Detail)](7-Wireframes.md#screen-6-activity-detail-execution-view)

### UC-8.2: Open Affiliate Link
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User clicks recommendation and is redirected to partner site via in-app browser or external browser.  
**Preconditions:** Recommendation exists.  
**Postconditions:** Affiliate click logged, user navigates to partner site.  
**📱 Wireframe:** [Affiliate Recommendation Card with CTA button](7-Wireframes.md#screen-26-affiliate-recommendation-card)

### UC-8.3: Dismiss Recommendation
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** User dismisses a recommendation; system learns preference (optional).  
**Preconditions:** Recommendation is displayed.  
**Postconditions:** Recommendation hidden, preference noted (TBD).  
**📱 Wireframe:** [Affiliate Recommendation Card with dismiss (✕) button](7-Wireframes.md#screen-26-affiliate-recommendation-card)

### UC-8.4: Retry Failed Affiliate Link
**Phase:** Phase 2  
**Actor:** Traveler  
**Description:** When partner link fails, user can retry or access fallback link.  
**Preconditions:** Original link failed to load.  
**Postconditions:** Alternative link provided or error logged.  
**📱 Wireframe:** [with retry button](7-Wireframes.md#screen-26-affiliate-recommendation-card)

---

## 9. Advanced Features (Phase 3)

### UC-9.1: Invite Co-Traveler to Trip
**Phase:** Phase 3  
**Actor:** Traveler  
**Description:** User invites another Jaarvi user to collaborate on trip planning.  
**Preconditions:** Trip exists, invitee has Jaarvi account.  
**Postconditions:** Co-traveler added with appropriate permissions.  
**📱 Wireframe:** [Trip Collaboration Screen](7-Wireframes.md#screen-27-trip-collaboration) + [7](7-Wireframes.md#screen-2-trip-detail-cities-list)

### UC-9.2: Collaborate on Itinerary Editing
**Phase:** Phase 3  
**Actor:** Traveler + Co-Traveler  
**Description:** Multiple users edit itinerary simultaneously with conflict resolution.  
**Preconditions:** Trip has multiple collaborators.  
**Postconditions:** Changes synchronized, edit history tracked per user.  
**📱 Wireframe:** [Conflict Resolution Dialog](7-Wireframes.md#screen-28-conflict-resolution-dialog)

### UC-9.3: Enable Full Offline Mode
**Phase:** Phase 3  
**Actor:** Traveler  
**Description:** User downloads trip data for offline access; app functions without connectivity.  
**Preconditions:** Trip exists, user has connectivity to download.  
**Postconditions:** Trip data cached locally, offline mode enabled.  
**📱 Wireframe:** [Trip Detail with "Download for Offline" action](7-Wireframes.md#screen-2-trip-detail-cities-list) + [Offline Mode Indicator](7-Wireframes.md#screen-29-offline-mode-indicator)

### UC-9.4: Sync Offline Changes
**Phase:** Phase 3  
**Actor:** Traveler + System  
**Description:** When connectivity restored, system syncs changes made offline.  
**Preconditions:** User made changes in offline mode.  
**Postconditions:** Changes uploaded, conflicts resolved.  
**📱 Wireframe:** [Sync Progress Indicator](7-Wireframes.md#screen-29-offline-mode-indicator) + [Conflict Resolution Dialog (if conflicts exist)](7-Wireframes.md#screen-28-conflict-resolution-dialog)

### UC-9.5: View Embedded Maps (v2)
**Phase:** Phase 3  
**Actor:** Traveler  
**Description:** User views navigation and activity locations in embedded maps within Jaarvi app.  
**Preconditions:** Activity has location data.  
**Postconditions:** Map displayed with route, no external app needed.  
**📱 Wireframe:** [Embedded Map View](7-Wireframes.md#screen-30-embedded-map-view) + [Screen 8](7-Wireframes.md#screen-8-activity-execution---navigation-mode)

### UC-9.6: Receive Personalized Recommendations from History
**Phase:** Phase 3  
**Actor:** Traveler + AI Agent  
**Description:** System analyzes user's past trips and preferences to suggest personalized activities and itineraries.  
**Preconditions:** User has completed multiple trips.  
**Postconditions:** Personalized recommendations displayed with historical context.  
**📱 Wireframe:** [Personalized Recommendations Feed](7-Wireframes.md#screen-31-personalized-recommendations-feed)

### UC-9.7: Integrate Partner Booking API
**Phase:** Phase 3  
**Actor:** Traveler  
**Description:** User books accommodations, tours, or transport directly within Jaarvi via partner API integration.  
**Preconditions:** Partner integration active, user authenticated.  
**Postconditions:** Booking confirmed, confirmation document auto-added to vault.  
**📱 Wireframe:** [In-App Booking Flow - Accessed from affiliate recommendations or activity creation](7-Wireframes.md#screen-32-in-app-booking-flow)

---

## 10. System & Administrative Use Cases

### UC-10.1: Log AI Generation Metadata
**Phase:** Phase 2  
**Actor:** System  
**Description:** System persists detailed metadata for every AI-generated itinerary/activity (inputs, model version, confidence, rationale, sources).  
**Preconditions:** AI generation occurs.  
**Postconditions:** AIGenerationLog record created for audit and improvement.  
**📱 Wireframe:** [AI Audit Trail Detail](7-Wireframes.md#screen-21-ai-audit-trail-detail)

### UC-10.2: Calculate Itinerary Feasibility Signals
**Phase:** MVP  
**Actor:** System  
**Description:** System continuously validates itinerary for time conflicts, budget overruns, unrealistic travel times.  
**Preconditions:** Itinerary exists with activities.  
**Postconditions:** Feasibility warnings generated and displayed to user.  
**📱 Wireframe:** [Day Activities (Edit Mode) - Warning messages (⚠️)](7-Wireframes.md#screen-4-day-activities-edit-mode---reordering) + [Inline warnings](7-Wireframes.md#screen-3-city-itinerary-days-tabs-view)

### UC-10.3: Track Affiliate Click Attribution
**Phase:** Phase 2  
**Actor:** System  
**Description:** System logs affiliate click with context (trip/day/activity) for attribution and analytics.  
**Preconditions:** User clicks affiliate recommendation.  
**Postconditions:** AffiliateClick record persisted with timestamp and context.  
**📱 Wireframe:** [Affiliate Recommendation Card](7-Wireframes.md#screen-26-affiliate-recommendation-card)

### UC-10.4: Sync User Data Across Devices
**Phase:** MVP  
**Actor:** System  
**Description:** System synchronizes user profile, trips, itineraries, and documents across multiple devices.  
**Preconditions:** User authenticated on multiple devices.  
**Postconditions:** Data consistent across all user devices.  
**📱 Wireframe:** [Settings/Profile](7-Wireframes.md#screen-33-app-settings)

### UC-10.5: Backup and Restore Trip Data
**Phase:** Phase 2  
**Actor:** System + Traveler  
**Description:** System automatically backs up trip data; user can restore if needed.  
**Preconditions:** User has active trips.  
**Postconditions:** Trip data backed up to secure storage, restore mechanism available.  
**📱 Wireframe:** [Settings/Profile with backup status](7-Wireframes.md#screen-33-app-settings) + [Restore Trip Dialog](7-Wireframes.md#screen-34-restore-trip-dialog)

---

## Summary by Phase

### MVP (Phase 1) - 42 Use Cases
- Account & Profile: 3 use cases
- Trip Creation: 7 use cases
- Manual Itinerary: 12 use cases
- Daily Planning: 2 use cases (basic)
- Activity Execution: 6 use cases (basic)
- Document Vault: 5 use cases (basic)
- System: 2 use cases (basic)

### Phase 2 (AI-Assisted) - 19 Use Cases
- AI Itinerary Generation: 6 use cases
- Daily Planning: 2 use cases (AI-enhanced)
- Activity Execution: 4 use cases (guidance playbooks)
- Document Vault: 1 use case (search)
- Affiliate Recommendations: 4 use cases
- System: 3 use cases (AI audit, backup)

### Phase 3 (Advanced) - 7 Use Cases
- Collaboration: 2 use cases
- Offline Mode: 2 use cases
- Embedded Maps: 1 use case
- Personalization: 1 use case
- Partner Integrations: 1 use case

**Total: 68 Use Cases**

---

## Notes on Implementation Priority

1. **MVP Critical Path**: UC-2.1→UC-2.2→UC-3.1→UC-3.2→UC-5.1→UC-6.1→UC-6.5 (minimum viable trip creation and execution)
2. **Document Vault Early Win**: UC-7.1→UC-7.2→UC-7.5 should be prioritized for user trust and perceived value
3. **AI Entry Point**: UC-4.1 (full itinerary generation) is the flagship Phase 2 feature
4. **Monetization Foundation**: UC-8.1→UC-8.2 must be non-intrusive and value-aligned; test in Phase 2 beta

---

## Revision History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | 2026-01-26 | AI Analysis | Initial use case catalog based on general specs |
| 2.0 | 2026-01-29 | AI Analysis | Added wireframe references to all use cases + identified missing screens |
| 2.1 | 2026-01-29 | AI Analysis | Merged all wireframes into 7-Wireframes.md (34 total screens) |

---

## Wireframe Coverage Summary

### ✅ All Wireframes (34 screens in 7-Wireframes.md)

All wireframes are now consolidated in a single document: `7-Wireframes.md`

#### **Phase 1 (MVP) Core Screens (1-14)**

| Screen | Primary Use Cases | Status |
|--------|------------------|--------|
| **Screen 1**: Trip List | UC-2.5, UC-2.7 | Complete |
| **Screen 2**: Trip Detail | UC-2.6, UC-3.1 | Complete |
| **Screen 3**: City Itinerary | UC-3.9, UC-3.10, UC-3.12 | Complete |
| **Screen 4**: Day Activities (Edit) | UC-3.4, UC-3.5, UC-3.11 | Complete |
| **Screen 5**: Add/Edit Activity Modal | UC-3.2, UC-3.3, UC-3.7, UC-3.8, UC-7.2 | Complete |
| **Screen 6**: Activity Detail | UC-5.3, UC-6.1, UC-6.2, UC-6.5, UC-6.6, UC-7.5 | Complete |
| **Screen 7**: Activity Execution Pipeline | UC-6.1, UC-6.4, UC-7.5 | Complete |
| **Screen 8**: Navigation Mode | UC-6.3 | Complete |
| **Screen 9**: Problem Resolution | UC-4.5, UC-6.9, UC-6.10 | Complete |
| **Screen 10**: Today View | UC-5.1, UC-6.5, UC-6.8, UC-3.12 | Complete |
| **Screen 11**: Document Vault | UC-7.1, UC-7.4, UC-7.7, UC-7.8 | Complete |
| **Screen 12**: Create Trip (Step 1) | UC-2.1 | Complete |
| **Screen 13**: Create Trip (Step 2) | UC-2.2 | Complete |
| **Screen 14**: Create Trip (Step 3) | UC-2.3, UC-1.3 | Complete |

#### **Phase 1 (MVP) Additional Screens (15-16, 23-25, 33)**



#### **Phase 1 (MVP) Priority**

| Screen | Use Cases | Description | Priority |
|--------|-----------|-------------|----------|
| **Screen 15**: Login/Register | UC-1.1 | Email/password and social auth options | **HIGH** |
| **Screen 16**: Profile Settings | UC-1.2 | User profile management | **HIGH** |
| **Screen 23**: Mandatory Activity Alert | UC-5.4 | Alert for missing accommodation/transport | **MEDIUM** |
| **Screen 24**: Push Notification | UC-6.7 | Notification template design | **MEDIUM** |
| **Screen 25**: Document Upload Modal | UC-7.1, UC-7.2, UC-7.3 | Upload + metadata entry | **HIGH** |
| **Screen 33**: Settings/Profile | UC-10.4 | App settings and sync status | **MEDIUM** |

#### **Phase 2 (AI Features) Priority**

| Screen | Use Cases | Description | Priority |
|--------|-----------|-------------|----------|
| **Screen 17**: AI Generation | UC-4.1 | AI itinerary generation flow | **HIGH** |
| **Screen 18**: Activity with AI Rationale | UC-4.2 | Shows "Why this?" explanation | **HIGH** |
| **Screen 19**: AI Suggestions Modal | UC-4.3 | Activity suggestions with accept/reject | **HIGH** |
| **Screen 20**: Optimization Comparison | UC-4.4 | Before/after itinerary comparison | **MEDIUM** |
| **Screen 21**: AI Audit Trail | UC-4.6 | Generation metadata detail | **LOW** |
| **Screen 22**: Daily Plan Refresh | UC-5.2 | Start-of-day plan adjustment | **MEDIUM** |
| **Screen 26**: Affiliate Recommendation | UC-8.1, UC-8.2, UC-8.3, UC-8.4 | Contextual product/service recommendations | **HIGH** |
| **Screen 34**: Restore Trip Dialog | UC-10.5 | Trip backup/restore interface | **LOW** |

#### **Phase 3 (Advanced) Priority**

| Screen | Use Cases | Description | Priority |
|--------|-----------|-------------|----------|
| **Screen 27**: Trip Collaboration | UC-9.1 | Invite co-travelers interface | **MEDIUM** |
| **Screen 28**: Conflict Resolution | UC-9.2, UC-9.4 | Resolve editing/sync conflicts | **MEDIUM** |
| **Screen 29**: Offline Mode Indicator | UC-9.3, UC-9.4 | Download for offline + sync status | **MEDIUM** |
| **Screen 30**: Embedded Map View | UC-9.5 | In-app map navigation (v2) | **LOW** |
| **Screen 31**: Personalized Feed | UC-9.6 | ML-powered recommendations | **LOW** |
| **Screen 32**: In-App Booking Flow | UC-9.7 | Partner API booking integration | **LOW** |

---

## Implementation Guidance for Frontend Developers

### Phase 1 (MVP) - Screens to Build First

**Critical Path (Week 1-2):**
1. Screen 15 (Login/Register) → Screen 1 (Trip List) → Screen 12-14 (Create Trip Flow)
2. Screen 2 (Trip Detail) → Screen 3 (City Itinerary) → Screen 5 (Add/Edit Activity)
3. Screen 10 (Today View) → Screen 6 (Activity Detail)

**Secondary (Week 3-4):**
4. Screen 4 (Edit Mode) - Activity reordering
5. Screen 11 (Document Vault) + Screen 25 (Upload Modal)
6. Screen 16 (Profile Settings)

**Tertiary (Week 5-6):**
7. Screen 23 (Mandatory Activity Alerts)
8. Screen 24 (Notification Templates)
9. Screen 33 (Settings)

### Phase 2 (AI Features) - Build After MVP Launch

**AI Core (Month 4):**
1. Screen 17 (AI Generation) - Flagship feature
2. Screen 18 (AI Rationale) - Trust building
3. Screen 19 (AI Suggestions) - Hybrid manual+AI

**Execution Enhancements (Month 5):**
4. Screen 7 (Activity Execution Pipeline) - Already exists, enhance with playbooks
5. Screen 8 (Navigation Mode) - Already exists, add landmark guidance
6. Screen 9 (Problem Resolution) - Already exists, add AI alternatives

**Monetization (Month 6):**
7. Screen 26 (Affiliate Recommendations) - Revenue stream
8. Screen 22 (Daily Plan Refresh) - Engagement

### Phase 3 (Advanced) - Post-Launch Iterations

**Collaboration (Month 7-8):**
1. Screen 27 (Trip Collaboration)
2. Screen 28 (Conflict Resolution)

**Offline & Embedded Maps (Month 9-10):**
3. Screen 29 (Offline Mode)
4. Screen 30 (Embedded Maps)

**Personalization & Booking (Month 11-12):**
5. Screen 31 (Personalized Feed)
6. Screen 32 (In-App Booking)

---

## Cross-Reference: Use Case → Wireframe Quick Lookup

### Account & Profile
- UC-1.1 → Screen 15
- UC-1.2 → Screen 16
- UC-1.3 → Screen 14

### Trip Management
- UC-2.1 → Screen 12
- UC-2.2 → Screen 13
- UC-2.3 → Screen 14
- UC-2.4 → Screens 12-14 (validation)
- UC-2.5 → Screen 1
- UC-2.6 → Screen 2
- UC-2.7 → Screen 1

### Itinerary Planning (Manual)
- UC-3.1 → Screen 3
- UC-3.2 → Screen 5
- UC-3.3 → Screen 5
- UC-3.4 → Screen 4
- UC-3.5 → Screen 4
- UC-3.6 → Screen 4
- UC-3.7 → Screen 5
- UC-3.8 → Screen 5
- UC-3.9 → Screen 3
- UC-3.10 → Screen 3
- UC-3.11 → Screen 4
- UC-3.12 → Screens 3, 10

### Itinerary Planning (AI)
- UC-4.1 → Screens 17, 3
- UC-4.2 → Screen 18
- UC-4.3 → Screen 19
- UC-4.4 → Screen 20
- UC-4.5 → Screen 9
- UC-4.6 → Screen 21

### Daily Planning
- UC-5.1 → Screen 10
- UC-5.2 → Screens 10, 22
- UC-5.3 → Screen 6
- UC-5.4 → Screens 10, 23

### Activity Execution
- UC-6.1 → Screens 6, 7
- UC-6.2 → Screen 6
- UC-6.3 → Screen 8
- UC-6.4 → Screen 7
- UC-6.5 → Screens 6, 10
- UC-6.6 → Screens 6, 7
- UC-6.7 → Screens 10, 24
- UC-6.8 → Screen 10
- UC-6.9 → Screen 9
- UC-6.10 → Screen 9

### Document Vault
- UC-7.1 → Screens 11, 25
- UC-7.2 → Screens 5, 25
- UC-7.3 → Screen 25
- UC-7.4 → Screen 11
- UC-7.5 → Screens 6, 7
- UC-7.6 → Screen 11
- UC-7.7 → Screen 11
- UC-7.8 → Screen 11

### Affiliate & Monetization
- UC-8.1 → Screen 26
- UC-8.2 → Screen 26
- UC-8.3 → Screen 26
- UC-8.4 → Screen 26

### Advanced Features
- UC-9.1 → Screen 27
- UC-9.2 → Screens 3-5, 28
- UC-9.3 → Screens 2, 29
- UC-9.4 → Screens 28, 29
- UC-9.5 → Screen 30
- UC-9.6 → Screens 1, 3, 31
- UC-9.7 → Screen 32

### System & Admin
- UC-10.1 → Screen 21 (backend)
- UC-10.2 → Screens 3, 4 (inline)
- UC-10.3 → Screen 26 (backend)
- UC-10.4 → Screen 33 (indicator)
- UC-10.5 → Screens 33, 34

---

## Next Steps for Design Team

### ✅ Wireframe Status - All Complete!

All 34 wireframe screens are now specified in `7-Wireframes.md`:
- ✅ **Screens 1-14**: Core MVP screens (original)
- ✅ **Screens 15-16, 23-25, 33**: MVP additional screens
- ✅ **Screens 17-22, 26, 34**: Phase 2 AI & monetization screens
- ✅ **Screens 27-32**: Phase 3 advanced screens

### Remaining Design Tasks

1. ⚠️ **Enhance all screens** with:
   - Loading states
   - Error states
   - Empty states
   - Validation states

2. 🎨 **Design System Tasks**:
   - Document animation transitions between screens
   - Create component library (cards, buttons, modals, forms)
   - Define color palette and typography tokens
   - Establish spacing and layout grid

---