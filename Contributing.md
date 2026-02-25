# Contributing to DukaanDesk

First off, thank you for considering contributing to DukaanDesk! 

We are building a tool that directly impacts the daily lives of local business owners—the Kirana shopkeepers, salon owners, and repair technicians who form the backbone of our neighborhoods. By contributing, you are helping build an offline-first, highly accessible digital cash register and ops hub designed specifically for their needs.

This document outlines the process for contributing to the project to ensure a smooth, collaborative, and high-quality environment.

---

## 1. The Golden Rules of DukaanDesk

Before writing any code, please internalize our core project mandates:
* **Offline-First is Non-Negotiable:** If a feature requires a continuous internet connection to function, it will not be merged. All core loops (sales, inventory updates, status changes) must work locally and sync asynchronously.
* **Respect the "Dirty Hands" UI:** Our users operate in high-traffic, messy environments. Adhere strictly to the Material Design 3 token system provided. Touch targets must be a minimum of 48dp.
* **Follow the Design Spec:** The UX and UI for the MVP have been thoroughly researched and defined. Please do not introduce new UI paradigms or colors without discussing them in an issue first.

---

## 2. Finding Something to Work On

* **Issues:** Check the [GitHub Issues](#) tab. Look for issues labeled `good first issue` if you are new to the codebase.
* **Engineering Requirements Document (ERD):** Review our ERD in the `/docs` folder to understand the overarching system architecture and NFRs (Non-Functional Requirements).
* **Claiming an Issue:** Please comment on the issue you want to work on so we can assign it to you and avoid duplicate work.

---

## 3. Development Workflow

We follow a standard Feature Branch workflow. 

1.  **Fork the repository** and clone it locally.
2.  **Create a branch** from `main` using the following naming convention:
    * `feature/issue-number-short-description` (e.g., `feature/12-voice-search-input`)
    * `bugfix/issue-number-short-description` (e.g., `bugfix/34-offline-sync-crash`)
    * `chore/short-description` (for documentation or configuration updates)
3.  **Commit frequently** with clear, descriptive commit messages.

---

## 4. Pull Request (PR) Guidelines

When you are ready to submit your code, open a Pull Request against the `main` branch. 

**Your PR must include the following:**
* **Linked Issue:** Mention the issue number it resolves (e.g., "Fixes #12").
* **Summary of Changes:** A brief bulleted list of what was added, changed, or removed.
* **Visual Proof (For UI/UX Changes):** If your PR alters the interface, **you must include before/after screenshots or a screen recording.** We need to verify it matches the Figma specifications and design tokens.
* **Offline Testing Verification:** Explicitly state how you tested this feature in an offline state. 

*Note: PRs failing to include visual proof for UI changes or offline testing verification will be blocked until provided.*

---

## 5. Code Review Process

All submissions require review from at least one core maintainer before merging. 

**What Reviewers Look For:**
1.  **Architecture Alignment:** Does this break the offline-first sync model?
2.  **Performance:** App launch must remain under 2.0s. Local database writes must be instantaneous (< 0.5s). 
3.  **Accessibility & Localization:** Are UI strings externalized? Are color tokens used correctly instead of hardcoded hex values?
4.  **Code Quality:** Is the code clean, well-commented, and following the project's linting rules?

Be open to feedback. Code reviews are discussions, not criticisms. We are all here to make the product more robust for our users.

---

## 6. Getting Help

If you are stuck on an architectural decision or need clarification on a design token, don't guess! 
* Ping us in the related GitHub Issue.
* Join our community channel at [Slack].

Let's build something that makes a difference.
