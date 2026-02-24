# Role

You are an expert frontend architect with extensive experience in KMP + CMP projects applying best practices.

# Ticket ID

$ARGUMENTS

# Goal

Obtain a step-by-step plan for a Jira ticket that is ready to start implementing.

# Process and rules

1. Adopt the role of `ai-specs/.agents/frontend-developer.md`
2. Analyze the Jira ticket mentioned in #ticket using the MCP. If the mention is a local file, then avoid using MCP
3. Propose a step-by-step plan for the frontend part, taking into account everything mentioned in the ticket and applying the project's best practices and rules you can find in `/ai-specs/specs`.
4. Apply the best practices of your role to ensure the developer can be fully autonomous and implement the ticket end-to-end using only your plan.
5. Do not write code yet; provide only the plan in the output format defined below.
6. If you are asked to start implementing at some point, make sure the first thing you do is to move to a branch named after the ticket id (if you are not yet there) and follow the process described in the command /develop-us.md

# Output format

Markdown document at the path `ai-specs/changes/TICKETS/[jira_id]_frontend.md` containing the complete implementation details.
Follow this template:

## Frontend Implementation Plan Ticket Template Structure

### 1. **Header**
- Title: `# Frontend Implementation Plan: [TICKET-ID] [Feature Name]`

### 2. **Overview**
- Brief description of the feature and frontend architecture principles (CMP composable-based architecture, KMP layered repository/use-case pattern, StateFlow-driven state management via Presenter/ScreenModel)

### 3. **Architecture Context**
- Composables/Screens involved (always in `shared-ui/src/commonMain/`)
- Presenters/ScreenModels involved and their `UiState`/`UiEvent` types
- Files referenced across `shared/` and `shared-ui/` modules
- Navigation considerations (Voyager screens/flows, if a new screen is introduced)
- State management approach (`UiState` data class + `UiEvent` sealed class + `StateFlow` from Presenter)

### 4. **Implementation Steps**
Detailed steps, typically:

#### **Step 0: Create Feature Branch**
- **Action**: Create and switch to a new feature branch following the development workflow. Check if it exists and if not, create it
- **Branch Naming**: Follow the project's branch naming convention (`ticket-<id>-<short-description>`, e.g., `ticket-12-today-mode`). Always use a dedicated branch; do not work on a general task branch.
- **Implementation Steps**:
  1. Ensure you're on the latest `main` or `master` branch (or appropriate base branch)
  2. Pull latest changes: `git pull origin [base-branch]`
  3. Create new branch: `git checkout -b ticket-[id]-[short-description]`
  4. Verify branch creation: `git branch`
- **Notes**: This must be the FIRST step before any code changes. Refer to `ai-specs/specs/frontend-standards.mdc` section "Development Workflow" for specific branch naming conventions and workflow rules.

#### **Step N: [Action Name]**
- **File**: Target file path (always under `shared/` or `shared-ui/src/commonMain/`)
- **Action**: What to implement
- **Class/Composable Signature**: Kotlin signature
- **Implementation Steps**: Numbered list
- **Dependencies**: Required imports — all Kotlin; no `android.*` in `commonMain`
- **Implementation Notes**: Technical details

Common steps:
- **Step 1**: Define/Update Repository Interface and Use Case (in `shared/src/commonMain/`)
- **Step 2**: Implement Presenter/ScreenModel with `UiState` and `UiEvent` (in `shared-ui/src/commonMain/`)
- **Step 3**: Create/Update Composable Screen using Linda Design System components (in `shared-ui/src/commonMain/`)
- **Step 4**: Wire Voyager Navigation (if a new screen is introduced or navigation flow changes)
- **Step 5**: Write `commonTest` tests for Presenter state transitions and use case logic


#### **Step N+1: Update Technical Documentation**
- **Action**: Review and update technical documentation according to changes made
- **Implementation Steps**:
  1. **Review Changes**: Analyze all code changes made during implementation
  2. **Identify Documentation Files**: Determine which documentation files need updates based on:
     - API endpoint changes → Update `ai-specs/specs/api-spec.yml`
     - UI/UX patterns or composable patterns → Update `ai-specs/specs/frontend-standards.mdc`
     - Navigation flow changes → Update navigation documentation
     - New Gradle dependencies or module configuration changes → Update `ai-specs/specs/frontend-standards.mdc`
     - New Linda Design System components added → Update design system documentation
     - Test patterns changes → Update testing documentation
  3. **Update Documentation**: For each affected file:
     - Update content in English (as per `documentation-standards.mdc`)
     - Maintain consistency with existing documentation structure
     - Ensure proper formatting
  4. **Verify Documentation**:
     - Confirm all changes are accurately reflected
     - Check that documentation follows established structure
  5. **Report Updates**: Document which files were updated and what changes were made
- **References**:
  - Follow process described in `ai-specs/specs/documentation-standards.mdc`
  - All documentation must be written in English
- **Notes**: This step is MANDATORY before considering the implementation complete. Do not skip documentation updates.

### 5. **Implementation Order**
- Numbered list of steps in sequence (must start with Step 0: Create Feature Branch and end with the documentation update step)

### 6. **Testing Checklist**
- Post-implementation verification checklist
- `commonTest` coverage for Presenter state transitions (loading → success / error) and use case logic
- Compose UI test coverage for critical screen rendering on Android (`androidx.compose.ui:ui-test-junit4`)
- XCUITest smoke coverage for critical flows on iOS (if applicable)
- Error state handling verification via `UiState.errorMessage` or equivalent

### 7. **Error Handling Patterns**
- Error state captured in `UiState` (e.g., `errorMessage: String?` or a dedicated sealed error type)
- User-friendly error messages rendered by Linda components within the composable
- Errors propagated from use case/repository via `Result<T>` or sealed result classes
- Presenter catches exceptions in its coroutine scope and maps them to the error `UiState`

### 8. **UI/UX Considerations** (if applicable)
- Linda Design System component usage (`LindaCard`, `LindaBadge`, `LindaButton`, `LindaHeader`, `LindaProgressBar`, etc.)
- Mobile-first layout: `fillMaxWidth`, `fillMaxSize`, padding and spacing via `LindaTheme.spacing`
- Accessibility: meaningful `contentDescription` on all interactive elements; stable semantic identifiers for UI tests
- Loading states: expose `isLoading: Boolean` in `UiState`; render a loading indicator from Linda or Compose Material

### 9. **Dependencies**
- Kotlin/CMP libraries involved (Compose Multiplatform, Voyager, Ktor, Coroutines, SQLDelight, etc.)
- Linda Design System components used (`com.jaarvi.ui.linda.components.*`)
- New Gradle entries added to `shared/build.gradle.kts` or `shared-ui/build.gradle.kts` (if any)
- Third-party packages (if any) — must be KMP-compatible; no Android-only libraries in `commonMain`


### 10. **Notes**
- Important reminders and constraints
- Business rules
- Language requirements (English only for code, comments, logs, and documentation)
- Platform-boundary rule: no `android.*` imports in `commonMain`; use capability interfaces (`MapCapability`, `NotificationCapability`, etc.) for all platform edges
- All build files must use Kotlin Gradle DSL (`.gradle.kts`)

### 11. **Next Steps After Implementation**
- Post-implementation tasks (documentation is already covered in Step N+1, but may include integration with backend, deployment, or follow-up tickets)

### 12. **Implementation Verification**
- Final verification checklist:
  - Code Quality (no `android.*` in `commonMain`, Kotlin idioms, Linda Design System used consistently)
  - Functionality (all `UiState` transitions work correctly: loading, success, error)
  - Testing (`commonTest` for Presenter + use cases; Compose UI test for critical Android flows)
  - Integration (Voyager navigation wired correctly; Presenter injected via Koin)
  - Documentation updates completed
