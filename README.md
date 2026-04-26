**🛡️ Ndejje University Safety App:**
A Real-time Campus Security & Incident Reporting System
It bridges the gap between students and university administration by providing a secure, role-based platform for reporting emergencies 
and broadcasting verified safety alerts.

🚀 **Key Features**
1. **Smart Incident Reporting**
     Categorized Reports:
     Supports various categories (Theft, Medical, Fire, Harassment).
     Anonymous Mode: Students can report incidents without revealing their identity to protect their privacy.
     Evidence Attachment: Support for image uploads to provide visual context to security teams.
2.**Advanced Moderation PipelineSensitive Data Protection:**
    Reports marked as "Sexual Harassment" are automatically flagged as Pending.Admin Review:
    Sensitive reports remain hidden from the public feed until a verified Administrator reviews and approves them.
    Real-time Alerts: Once approved, alerts are instantly pushed to all campus users.
3. **Role-Based DashboardsStudent View:**
     Access to a public alert feed and a personal "My Reports" history.Admin View:
     Access to high-level analytics, incident management, and a verification queue.
   
 🏗️ **Architecture & Tools**
   The app is built using modern Android development standards to ensure scalability and performance:
   Jetpack Compose: For a modern, reactive user interface.
   MVVM Architecture: Ensures a clean separation between UI logic and data.
   Room Database: Local SQLite persistence for offline access to safety resources.
   Kotlin Coroutines/Flow: For smooth, asynchronous database operations and real-time UI updates.

 🧪 **Testing & Validation**
 The system has been rigorously tested across the following scenarios:
**Scenario**	         **Objective**	                                                               **Outcome**
Authentication	   Ensure secure login and session persistence.                                      Passed
Privacy Filter	   Verify that "Pending" reports are invisible to other students.	                   Passed
Admin Approval	   Confirm database updates trigger immediate UI refreshes for all users.            Passed
My Reports	       Ensure users can track the status of their own pending reports.	                 Passed
RBAC	             Validate that Admin tools are inaccessible to regular student accounts.	         Passed

🛠️ **Installation & SetupClone the Repository:** 
git clone https://github.com/your-username/NdejjeSafetyApp.gitOpen
in Android Studio:Ensure you are using version Ladybug (2024.2.1) or newer.
Gradle Sync: Allow the IDE to download dependencies (Room, Compose, Navigation).
Run: Deploy to an emulator or device (API 24+).
