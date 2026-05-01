# **Ndejje Safety App** 🛡️
### [**📺 Watch the App Demo on YouTube**](https://your-youtube-link-here.com)

The **Ndejje Safety App** is a specialized mobile emergency response system developed for Ndejje 
University. It bridges the gap between students and campus security by providing a real-time, role-
based platform for incident reporting, alert verification, and safety analytics.

---

## **👥 Team Roster**
| Name                     | Student ID           | Defined Role                                    |
|:-----------------------  |:------------------  |:------------------------------------------------ |
| **AIJUKA ARNOLD**        | **25/2/306/W/2040** | **Lead Developer**                               |
| **MUSIIGE ALFRED JUNIOR**| **24/2/306/WJ/149** | **UI/UX Designer/DOC & RESEARCH LEAD**           |
| **KIZITO KEZEKIAH**      | **25/2/306/W/3273** | **Git & Quality Manager / Testing & QA Engineer**|


---

## **🚀 Feature Set**
* **Theme-Aware Interface:** Fully optimized for **Dark Mode** and **Light Mode** using Material 3 
    dynamic color mapping.
* **Role-Based Access Control (RBAC):** * **Students:** Report incidents (Anonymous toggle), view 
    public alerts, and track personal report status.
* **Admins:** Command Center view , incident verification logic, and system analytics.
* **Incident Management:** Categorized reporting (Physical Assault, Theft, Medical, etc.) with 
    campus-specific selection.
* **Multimedia Integration:** Support for image attachments using the Coil library for visual evidence.
* **Emergency Quick-Dial:** campus security hotline integration for instant SOS calls.

---

## **🛠️ Technical Stack**
* **UI:** Jetpack Compose (Material 3)
* **Local DB:** Room Persistence Library (SQLite)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Concurrency:** Kotlin Coroutines & StateFlow
* **Image Handling:** Coil Image Loader
* **Navigation:** Compose Navigation Component

---

## **✅ QA Summary**
The following test cases were conducted by the QA Engineer to ensure production readiness.

| Test Case ID | Feature Tested       | Expected Result                                                  | Status   |
|:-------------|:---------------------|:-----------------------------------------------------------------|:---------|
| **TC-01**    | **Theme Contrast**   | All text/inputs remain visible when switching to Dark Mode.      | **PASS** |
| **TC-02**    | **Admin Dashboard**  | Branding flips to Navy/Charcoal for Admins to signify authority. | **PASS** |
| **TC-03**    | **Anonymity Logic**  | Reporter ID is hidden from the public feed when toggled.         | **PASS** |
| **TC-04**    | **Input Integrity**  | Prevents empty "Title" submissions via ViewModel validation.     | **PASS** |
| **TC-05**    | **Responsive Flow**  | Horizontal scrolling enabled in Landscape via `verticalScroll`.  | **PASS** |
| **TC-06**    | **Auth Persistence** | User session remains active during orientation changes.          | **PASS** |

---

## **📂 Installation & Setup**
1. Clone the repository: `git clone https://github.com/YourUsername/NdejjeSafetyApp.git`
2. Open in **Android Studio (Ladybug 2024.2.1 or higher)**.
3. Sync Gradle and ensure SDK 34 is installed.
4. Run on a physical device for full hardware support (Camera/Dialer).

---

